package emergence_RL.strategies;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import ontology.Types;
import ontology.Types.WINNER;
import emergence_RL.heuristic.AHeuristic;
import emergence_RL.heuristic.TargetHeuristic;
import emergence_RL.tree.Node;
import emergence_RL.tree.NodeComparator;
import emergence_RL.tree.Tree;

/**
 * this class expands the Tree using a modified a-star algorithm. There is no
 * check if we found our Goal, the actual Action is set by every loop in the
 * expand method using the rootaction from the best Node of the closedList. and
 * the given heuristic
 * 
 * @author spakken
 *
 */
public class AStarStrategy extends AEvolutionaryStrategy {

	// (a < b) = 1 if a.score > b.score
	Comparator<Node> comparator = new NodeComparator();

	// to check if a Node is on the openlist
	Map<String, Node> openSet = new HashMap<String, Node>();

	// to check if a Node is on the closedlist
	Map<String, Node> closedSet = new HashMap<String, Node>();

	// to get the best Node from the openlist
	PriorityQueue<Node> openList = new PriorityQueue<Node>(10, comparator);

	// the forbidden Actions
	ArrayList<Types.ACTIONS> forbidden_actions = new ArrayList<Types.ACTIONS>();

	// best current node in the tree. this is important for extracting the path.
	public Node bestNode = null;

	// best score that has reached with this heuristic
	public double bestScore = Double.NEGATIVE_INFINITY;

	// heuristic that is used for expanding the tree
	public AHeuristic heuristic;

	// check if there are immovable positions in this game
	boolean immovalbeExist = false;

	public AStarStrategy() {
	};

	public AStarStrategy(Tree tree, AHeuristic heuristic) {
		super(tree);
		this.heuristic = heuristic;
		tree.root.score = heuristic.evaluateState(tree.root.stateObs);
		init(tree);

	}

	public void init(Tree tree) {
		openSet.clear();
		closedSet.clear();
		openList.clear();
		bestNode = null;
		bestScore = Double.NEGATIVE_INFINITY;
		immovalbeExist = false;

		// add own node to the open list
		for (Node child : this.getChildren(tree.root)) {
			openSet.put(child.hash(), child);
			openList.add(child);
		}
	}



	private void next(Node n) {

		// generate all children
		List<Node> children = getChildren(n);

		// for every child that can be computed:
		for (Node child : children) {

			// if the node (Position) is already on the closed List, do nothing
			if (closedSet.containsKey(child.hash())) {
				continue;
			}

			Node node = openSet.get(child.hash());

			if (node == null) {
				// add node to the openList
				openSet.put(child.hash(), child);
				openList.add(child);

			} else if (node.level >= child.level) {
				// change path to child in openSet
				openSet.put(child.hash(), child);
				openList.add(child);
			}
		}
	}

	/**
	 * Overwritten method from Class ATree. only create the children
	 * 
	 * @param node
	 *            node that should be expanded
	 * @return list of all possible children states
	 */
	public List<Node> getChildren(Node node) {

		List<Node> children = node.getChildren();

		for (Node child : children) {

			child.score = heuristic.evaluateState(child.stateObs);
			double delta = child.stateObs.getGameScore()
					- node.stateObs.getGameScore();

			if (child.stateObs.getGameWinner() == WINNER.PLAYER_WINS) {
				child.score = 100;
			} else if (delta != 0) {
				child.score = delta;
			}

		}
		return children;
	}

	@Override
	public boolean expand() {

		if (openList.isEmpty())
			return false;

		Node n = openList.poll();
		openSet.remove(n.hash());

		// add the actual position to the closed list
		closedSet.put(n.hash(), n);
		
		
		if (n.score > bestScore) {
			bestScore = n.score;
			bestNode = n;
		}
		
		
		next(n);

		// System.out.println(bestNode);
		return true;
	}

	@Override
	public AEvolutionaryStrategy random() {
		AStarStrategy strategy = new AStarStrategy();
		strategy.heuristic = TargetHeuristic.createRandom();
		return strategy;
	}

	@Override
	public AEvolutionaryStrategy mutate() {
		return this.random();
	}

	@Override
	public AEvolutionaryStrategy crossover(AEvolutionaryStrategy strategy) {
		return this.random();
	}

	@Override
	public double getScore() {
		if (bestNode != null
				&& bestNode.stateObs.getGameWinner() == WINNER.PLAYER_WINS)
			return 1;
		else
			return -1;
	}

	@Override
	public String toString() {
		String s = "AStar ";
		s += " | ";
		s += (bestNode != null) ? bestNode.toString() : "null";
		s += " | ";
		s += heuristic.toString();
		return s;
	}


	public Types.ACTIONS act() {
		if (bestNode == null)
			return Types.ACTIONS.ACTION_NIL;
		else
			return bestNode.rootAction;
	}

}
