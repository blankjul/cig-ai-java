package emergence.strategy.mcts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import ontology.Types;
import ontology.Types.ACTIONS;
import ontology.Types.WINNER;
import core.game.StateObservation;
import emergence.heuristics.AHeuristic;
import emergence.nodes.GenericNode;
import emergence.strategy.AStrategy;
import emergence.util.ActionTimer;
import emergence.util.Helper;
import emergence.util.pair.Pair;
import emergence.util.pair.SortedPair;

public class MCTStrategy extends AStrategy {

	@Override
	public boolean expand(StateObservation stateObs, ActionTimer timer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ACTIONS act() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	
	
	// maximal depth of the tree
	public int maxDepth = 8;

	// the value for the exploration term
	public double c = Math.sqrt(2);

	// this is the discounting factor. it's one so disabled default
	public double gamma = 0.9;

	// game tree that should be expanded by this class
	public MCTSNode root;

	// weights that could be used for a formula!
	public double[] weights = new double[] { 1, 1, 1, 1 };

	// very small value
	public static double epsilon = 0.0000000001d;

	public int currentTreeDepth = 0;

	private Random r = new Random();

	private MCTSNode bestNode;

	AHeuristic heuristic;

	public MCTStrategy() {
	}

	@Override
	public boolean expand(StateObservation stateObs, ActionTimer timer) {

		while (timer.isTimeLeft()) {

			Pair<MCTSNode, StateObservation> pair = treePolicy(new Pair<MCTSNode, StateObservation>(root, stateObs));

			double reward = defaultPolicy(pair);
			backPropagation(pair._1(), reward);

			timer.addIteration();
		}

		return true;
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

		StateObservation currentStateObs = pair._2().copy();
		MCTSNode n = pair._1();
		double startingScore = currentStateObs.getGameScore();

		ArrayList<Types.ACTIONS> actions = currentStateObs.getAvailableActions();

		int level = n.getLevel();
		while (!currentStateObs.isGameOver() && level <= maxDepth) {
			Types.ACTIONS a = Helper.getRandomEntry(actions);
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

	private Pair<MCTSNode, StateObservation> treePolicy(Pair<MCTSNode, StateObservation> pair) {

		// get the MCTSNode and the state observation
		MCTSNode n = pair._1();
		StateObservation stateObs = pair._2();

		while (!stateObs.isGameOver() && n.getLevel() <= maxDepth) {

			if (!n.isFullyExpanded(stateObs)) {

				// get one random child
				ACTIONS rndAction = Helper.getRandomEntry(stateObs.getAvailableActions());
				
				
				GenericNode<Object> genericChild = n.getChild(rndAction);
				MCTSNode child = new MCTSNode(n, rndAction);
				child.setPath(genericChild.getPath());
				
				
				// get an random child that is not expanded yet
				ArrayList<ACTIONS> actions = new ArrayList<ACTIONS>();
				for (ACTIONS a : stateObs.getAvailableActions()) {
					if (!n.children.containsKey(a))
						actions.add(a);
				}
				ACTIONS nextAction = Helper.getRandomEntry(actions);

				// get the state and the child MCTSNode
				StateObservation stateObsChild = stateObs.copy();
				stateObsChild.advance(nextAction);
				
				MCTSNode child = new MCTSNode(n, nextAction);
				if (stateObsChild.getGameWinner() == WINNER.PLAYER_LOSES)
					child.isLooser = true;

				return new Pair<MCTSNode, StateObservation>(child, stateObsChild);

			} else {
				Pair<MCTSNode, StateObservation> result = bestChild(new Pair<MCTSNode, StateObservation>(n, stateObs),
						c);
				n = result._1();
				stateObs = result._2();
			}
		}
		return new Pair<MCTSNode, StateObservation>(n, stateObs);
	}

	private Pair<MCTSNode, StateObservation> bestChild(Pair<MCTSNode, StateObservation> pair, double c) {

		MCTSNode n = pair._1();
		StateObservation stateObsFather = pair._2();

		// always the best child is saved here
		MCTSNode bestChild = null;
		double bestValue = Double.NEGATIVE_INFINITY;
		StateObservation bestStateObs = null;

		for (MCTSNode child : MCTSNode.getChildren(n)) {

			// get the current state observation from the child
			StateObservation stateObsChild = stateObsFather.copy();
			stateObsChild.advance(child.getLastAction());
			if (stateObsChild.getGameWinner() == WINNER.PLAYER_LOSES)
				child.isLooser = true;

			double exploitation = child.Q / (child.visited + epsilon * r.nextDouble());
			double exploration = c * Math.sqrt(Math.log(n.visited + 1) / (child.visited));
			double tiebreak = r.nextDouble() * epsilon;

			// heuristic by using the target
			double heuristicValue = 0;
			if (heuristic != null) {
				heuristicValue = heuristic.evaluateState(stateObsChild);
			}

			// history of field
			String h = MCTSNode.hash(stateObsChild, child.getLastAction());
			Integer visitsOfField = FieldTracker.fieldVisits.get(h);
			double historyValue = 1;
			if (visitsOfField != null && FieldTracker.maxVisitedField > 0) {
				historyValue = Math.sqrt((1 - visitsOfField / (double) FieldTracker.maxVisitedField));
			}

			child.uct = weights[0] * exploitation + weights[1] * exploration + weights[2] * heuristicValue + weights[3]
					* historyValue + tiebreak;

			if (child.uct >= bestValue) {
				bestChild = child;
				bestValue = child.uct;
				bestStateObs = stateObsChild;
			}
		}
		return new Pair<MCTSNode, StateObservation>(bestChild, bestStateObs);
	}

	@Override
	public ACTIONS act() {
		// select the best child!
		List<SortedPair<MCTSNode, Double>> nodes = new ArrayList<SortedPair<MCTSNode, Double>>();
		for (MCTSNode child : MCTSNode.getChildren(root)) {
			if (child.isLooser == true)
				continue;
			double tiebreak = r.nextDouble() * epsilon;
			nodes.add(new SortedPair<MCTSNode, Double>(child, child.visited + tiebreak));
		}
		Collections.sort(nodes);
		if (nodes.size() > 0) {
			bestNode = nodes.get(0)._1();
			return bestNode.getFirstAction();
		} else
			return ACTIONS.ACTION_NIL;
	}

	public void rollingHorizon() {

		if (bestNode == null) return;
		
		// rolling horizon
		bestNode.father = null;
		root = bestNode;

		// update the levels
		Queue<MCTSNode> queue = new LinkedList<MCTSNode>();
		queue.add(bestNode);
		while (!queue.isEmpty()) {
			MCTSNode n = queue.poll();
			n.removeFirstAction();
			queue.addAll(MCTSNode.getChildren(n));
		}
	}

	@Override
	public String toString() {
		String s = "";
		for (MCTSNode child : MCTSNode.getChildren(root)) {
			s += child.toString() + '\n';
		}
		s += "---------------------";
		return s;
	}
	
	*/

}
