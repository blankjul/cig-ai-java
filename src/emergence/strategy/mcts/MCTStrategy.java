package emergence.strategy.mcts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import ontology.Types;
import ontology.Types.ACTIONS;
import core.game.StateObservation;
import emergence.Factory;
import emergence.heuristics.AHeuristic;
import emergence.heuristics.DeltaScoreHeuristic;
import emergence.nodes.GenericNode;
import emergence.strategy.AStrategy;
import emergence.util.ActionTimer;
import emergence.util.Helper;

/**
 * A MCTS strategy which builds the tree and searches in this tree using
 * default-and tree policy and some other methods.
 * 
 * @author spakken
 *
 */
public class MCTStrategy extends AStrategy {

	/** maximal depth of the tree */
	public int maxDepth = 15;

	/** this is the discounting factor. it's one so disabled default */
	public double gamma = 1.0;

	/** game tree that should be expanded by this class */
	public MCTSNode root;

	/** actual depth of the tree */
	public int currentTreeDepth = 0;

	/** the heuristic which is used */
	public AHeuristic heuristic = null;

	/** actual best MCTS node */
	private MCTSNode bestNode;

	/**
	 * Constructs the root of an MCTS tree and sets the root as the actual best
	 * node.
	 * 
	 * @param root
	 */
	public MCTStrategy(MCTSNode root) {
		super();
		this.root = root;
		bestNode = root;
	}

	/**
	 * Expand method to compute the whole MCTS algorithm. If time is left, the
	 * tree policy is executed to iterate trough the tree, the default policy to
	 * simulate actions from the new node and the backpropagation to compute the
	 * reward values.
	 */
	public boolean expand(StateObservation stateObs, ActionTimer timer) {

		// get all actions that are available
		Set<ACTIONS> allActions = new HashSet<ACTIONS>(
				stateObs.getAvailableActions());

		while (timer.isTimeLeft()) {

			// tree policy by iteration through the tree
			MCTSNode n = treePolicy(root, allActions);

			// adapt the current tree depth
			if (n.getLevel() > currentTreeDepth)
				currentTreeDepth = n.getLevel();

			// check if there is enough time
			if (!timer.isTimeLeft())
				return true;

			// default policy by looking for a random path
			double reward = defaultPolicy(n, stateObs.copy());

			// backpropagate the reward
			backPropagation(n, reward);

			timer.addIteration();
		}

		return true;
	}

	/**
	 * Backpropagation from the simulated node n to the root. Change the reward
	 * of all nodes which be visited according to the given reward.
	 * 
	 * @param n
	 *            at this node starts the backpropagation
	 * @param reward
	 *            reward of the simulated node
	 */
	private void backPropagation(MCTSNode n, double reward) {
		// while the actual node n is not the root
		while (n != null) {
			n.addVisited();
			n.Q += reward;
			reward *= gamma;
			n = n.getFather();
		}
	}

	/**
	 * The default policy simulates (advances) the given node and computes a
	 * reward value.
	 * 
	 * @param n
	 * @param stateObs
	 * @return
	 */
	private double defaultPolicy(MCTSNode n, StateObservation stateObs) {

		DeltaScoreHeuristic deltaHeuristic = new DeltaScoreHeuristic(
				stateObs.getGameScore());

		// simulate this node and the given state obs
		n.simulate(stateObs);
		if (heuristic != null)
			n.heuristicValue = heuristic.evaluateState(stateObs);

		// add random simulation
		int level = n.getLevel();
		while (!stateObs.isGameOver() && level <= maxDepth) {
			Types.ACTIONS a = Helper.getRandomEntry(stateObs
					.getAvailableActions());
			Factory.getSimulator().advance(stateObs, a);
			++level;
		}

		// calculate the heuristic value
		double delta = deltaHeuristic.evaluateState(stateObs);
		return delta;
	}

	/**
	 * The tree policy iterates trough the tree, starting at the node n, until
	 * one node is not fully expanded. When a node is not fully expanded, it
	 * returns a random unexpected children of this node.
	 * 
	 * @param n
	 * @param allActions
	 * @return
	 */
	private MCTSNode treePolicy(MCTSNode n, Set<ACTIONS> allActions) {
		// get the MCTSNode and the state observation
		while (n.getLevel() <= maxDepth) {
			if (!n.isFullyExpanded(allActions)) {
				MCTSNode child = (MCTSNode) n
						.getRandomUnexpandedChildren(allActions);
				return child;
			} else {
				MCTSNode result = n.bestChild(n);
				n = result;
			}
		}
		return n;
	}

	/**
	 * After the expand method was executed, this method is called to chose the
	 * action which will be executed.
	 */
	@Override
	public ACTIONS act() {
		// get all the children and compare the visited integer
		List<MCTSNode> nodeList = new ArrayList<>();
		for (GenericNode<Object> childGeneric : root.getAllChildren()) {
			MCTSNode child = (MCTSNode) childGeneric;
			nodeList.add(child);
		}
		MostVisitedNodeComparator comp = new MostVisitedNodeComparator();
		Collections.sort(nodeList, comp);

		// get all the best nodes. Could be that more than one have the max
		// values
		if (nodeList.size() == 0)
			return ACTIONS.ACTION_NIL;
		else {
			// find the first point where they are not equal
			int i = 0;
			for (; i < nodeList.size() - 1; i++) {
				if (comp.compare(nodeList.get(i), nodeList.get(i + 1)) != 0)
					break;
			}
			nodeList = nodeList.subList(0, i + 1);
			// now the nodeList contains all nodes with the maximal equal result
			if (heuristic == null) {
				bestNode = Helper.getRandomEntry(nodeList);
			} else {
				List<MCTSNode> heuristicList = new ArrayList<>(nodeList);
				Collections.sort(heuristicList,
						new BestHeuristicNodeComparator());
				bestNode = heuristicList.get(0);
			}
			return bestNode.getFirstAction();
		}

	}

	/**
	 * Rolling horizon sets the actual best node as the root.
	 * 
	 * @param lastAction
	 */
	public void rollingHorizon(ACTIONS lastAction) {

		MCTSNode bestNode = (MCTSNode) root.getChild(lastAction);

		// rolling horizon
		bestNode.setFather(null);
		root = bestNode;
		currentTreeDepth = 0;

		// update the levels
		Queue<MCTSNode> queue = new LinkedList<MCTSNode>();
		queue.add(bestNode);
		while (!queue.isEmpty()) {
			MCTSNode n = queue.poll();
			n.removeFirstAction();
			if (n.getLevel() > currentTreeDepth)
				currentTreeDepth = n.getLevel();
			for (GenericNode<Object> childGeneric : n.getAllChildren()) {
				queue.add((MCTSNode) childGeneric);
			}
		}
	}

	/**
	 * Convert parameters to a String object.
	 */
	@Override
	public String toString() {
		act();
		String s = String.format("Best:%s | \nDepth:%s \n",
				bestNode.toString(), currentTreeDepth);
		s += "---------------------\n";
		root.print(1);
		if (heuristic != null)
			System.out.println(heuristic);
		s += "---------------------";
		return s;
	}

	/**
	 * Set the heuristic.
	 * 
	 * @param heuristic
	 */
	public void setHeuristic(AHeuristic heuristic) {
		this.heuristic = heuristic;
	}

	/**
	 * Get the heuristic.
	 * 
	 * @return
	 */
	public AHeuristic getHeuristic() {
		return heuristic;
	}

	/**
	 * Convert parameters into a String which is used for the csv files.
	 * 
	 * @return
	 */
	public String toCSVString() {
		String csv = "";
		csv += Integer.toString(this.maxDepth) + ",";
		csv += Double.toString(this.gamma);

		return csv;
	}

}
