package emergence.strategy.mcts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ontology.Types;
import ontology.Types.ACTIONS;
import ontology.Types.WINNER;
import core.game.StateObservation;
import emergence.nodes.Tree;
import emergence.strategy.AStrategy;
import emergence.util.Helper;
import emergence.util.pair.Pair;
import emergence.util.pair.SortedPair;

public class MCTStrategy extends AStrategy {

	// maximal depth of the tree -> 10 per default!
	public int maxDepth = 8;

	// the value for the exploration term
	public double c = Math.sqrt(2);

	// this is the discounting factor. it's one so disabled default
	public double gamma = 0.9;
	
	// game tree that should be expanded by this class
	public Tree<MCTSNode> tree;

	// weights that could be used for a formula!
	public double[] weights = new double[] { 1, 1, 1, 1 };

	// very small value
	public static double epsilon = 0.0000000001d;

	public StateObservation stateObsOfRoot;
	
	public int currentTreeDepth = 0;
	

	public MCTStrategy(StateObservation stateObsOfRoot) {
		super(stateObsOfRoot);
		this.stateObsOfRoot = stateObsOfRoot;
	}

	public void expand() {
		Pair<MCTSNode, StateObservation> pair = treePolicy(new Pair<MCTSNode, StateObservation>(
				tree.root, stateObsOfRoot));
		double reward = defaultPolicy(pair);
		backPropagation(pair.getFirst(), reward);

	}

	private void backPropagation(MCTSNode n, double reward) {
		while (n != null) {
			// now we visited the MCTSNode
			// use a discount factor for the as a weight!
			++n.visited;
			n.Q += reward;
			reward *= gamma;
			n = n.father;
		}
	}

	private double defaultPolicy(Pair<MCTSNode, StateObservation> pair) {

		StateObservation currentStateObs = pair.getSecond().copy();
		MCTSNode n = pair.getFirst();
		double startingScore = currentStateObs.getGameScore();

		ArrayList<Types.ACTIONS> actions = currentStateObs
				.getAvailableActions();

		int level = n.level;
		while (!currentStateObs.isGameOver() && level <= maxDepth) {
			Types.ACTIONS a = Helper.getRandomEntry(actions, Storage.random);
			currentStateObs.advance(a);
			++level;
		}

		if (currentStateObs.isGameOver()) {
			Types.WINNER winner = currentStateObs.getGameWinner();
			if (winner == Types.WINNER.PLAYER_WINS)
				return 100;
			else if (winner == Types.WINNER.PLAYER_LOSES)
				return -1;
		}

		double delta = currentStateObs.getGameScore() - startingScore;
		return delta;
	}

	private Pair<MCTSNode, StateObservation> treePolicy(
			Pair<MCTSNode, StateObservation> pair) {

		// get the MCTSNode and the state observation
		MCTSNode n = pair.getFirst();
		StateObservation stateObs = pair.getSecond();

		while (!stateObs.isGameOver() && n.level <= maxDepth) {
			
			if (!n.isFullyExpanded(stateObs)) {

				// get an random child that is not expanded yet
				ArrayList<ACTIONS> actions = new ArrayList<ACTIONS>();
				for (ACTIONS a : stateObs.getAvailableActions()) {
					if (!n.children.containsKey(a))
						actions.add(a);
				}
				ACTIONS nextAction = Helper.getRandomEntry(actions,
						Storage.random);

				// get the state and the child MCTSNode
				StateObservation stateObsChild = stateObs.copy();
				stateObsChild.advance(nextAction);
				MCTSNode child = new MCTSNode(n, nextAction);
				if (stateObsChild.getGameWinner() == WINNER.PLAYER_LOSES) child.isLooser = true;

				return new Pair<MCTSNode, StateObservation>(child, stateObsChild);

			} else {
				Pair<MCTSNode, StateObservation> result = bestChild(
						new Pair<MCTSNode, StateObservation>(n, stateObs), c);
				n = result.getFirst();
				stateObs = result.getSecond();
			}
		}
		return new Pair<MCTSNode, StateObservation>(n, stateObs);
	}

	private Pair<MCTSNode, StateObservation> bestChild(
			Pair<MCTSNode, StateObservation> pair, double c) {

		MCTSNode n = pair.getFirst();
		StateObservation stateObsFather = pair.getSecond();

		// always the best child is saved here
		MCTSNode bestChild = null;
		double bestValue = Double.NEGATIVE_INFINITY;
		StateObservation bestStateObs = null;

		for (MCTSNode child : MCTSNode.getChildren(n)) {

			// get the current state observation from the child
			StateObservation stateObsChild = stateObsFather.copy();
			stateObsChild.advance(child.lastAction);
			if (stateObsChild.getGameWinner() == WINNER.PLAYER_LOSES) child.isLooser = true;

			double exploitation = child.Q
					/ (child.visited + epsilon * Storage.random.nextDouble());
			double exploration = c
					* Math.sqrt(Math.log(n.visited + 1) / (child.visited));
			double tiebreak = Storage.random.nextDouble() * epsilon;

			// heuristic by using the target
			double heuristicValue = 0;
			if (heuristic != null) {
				heuristicValue = heuristic.evaluateState(stateObsChild);
			}

			// history of field
			String h = MCTSNode.hash(stateObsChild, child.lastAction);
			Integer visitsOfField = FieldTracker.fieldVisits.get(h);
			double historyValue = 1;
			if (visitsOfField != null && FieldTracker.maxVisitedField > 0) {
				historyValue = Math.sqrt((1 - visitsOfField
						/ (double) FieldTracker.maxVisitedField));
			}

			child.uct = weights[0] * exploitation + weights[1] * exploration
					+ weights[2] * heuristicValue + weights[3] * historyValue
					+ tiebreak;

			if (child.uct >= bestValue) {
				bestChild = child;
				bestValue = child.uct;
				bestStateObs = stateObsChild;
			}
		}
		return new Pair<MCTSNode, StateObservation>(bestChild, bestStateObs);
	}

	@Override
	public MCTSNode act() {
		// select the best child!
		List<SortedPair<MCTSNode, Double>> MCTSNodes = new ArrayList<SortedPair<MCTSNode, Double>>();
		for (MCTSNode child : MCTSNode.getChildren(tree.root)) {
			if (child.isLooser == true) continue;
			double tiebreak = Storage.random.nextDouble() * epsilon;
			MCTSNodes.add(new SortedPair<MCTSNode, Double>(child, child.visited
					+ tiebreak));
		}
		Collections.sort(MCTSNodes);
		if (MCTSNodes.size() > 0)
			return MCTSNodes.get(0).getFirst();
		else
			return null;
	}

	/**
	 * Set the given MCTSNode as the new tree
	 * this means to set level = level -1
	 * @param MCTSNode
	 */
	public void rollingHorizon(MCTSNode MCTSNode) {
		
		// rolling horizon
		MCTSNode.father = null;
		tree.root = MCTSNode;
		
		// if it's root just return
		if (MCTSNode.level == 0) return;
		// update the levels
		Queue<MCTSNode> queue = new LinkedList<MCTSNode>();
		queue.add(MCTSNode);
		while (!queue.isEmpty()) {
			MCTSNode n = queue.poll();
			n.level -= 1;
			queue.addAll(MCTSNode.getChildren(n));
		}
	}

	@Override
	public String toString() {
		String s = "";
		for (MCTSNode child : MCTSNode.getChildren(tree.root)) {
			s += child.toString() + '\n';
		}
		s += "---------------------";
		return s;
	}

}
