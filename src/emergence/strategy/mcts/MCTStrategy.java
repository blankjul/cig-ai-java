package emergence.strategy.mcts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

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
import emergence.util.pair.Pair;
import emergence.util.pair.SortedPair;

public class MCTStrategy extends AStrategy {

	
	// maximal depth of the tree
	public int maxDepth = 10;

	// this is the discounting factor. it's one so disabled default
	public double gamma = 0.9;

	// game tree that should be expanded by this class
	public MCTSNode root;

	public int currentTreeDepth = 0;

	private Random r = new Random();

	private MCTSNode bestNode;
	
	private AHeuristic heuristic = null;


	@Override
	public boolean expand(StateObservation stateObs, ActionTimer timer) {

		while (timer.isTimeLeft()) {

			// tree policy by iteration through the tree
			Pair<MCTSNode, StateObservation> pair = treePolicy(new Pair<MCTSNode, StateObservation>(root, stateObs.copy()));
			
			if (!timer.isTimeLeft()) return true;
			
			// adapt the current tree depth
			if (pair._1().getLevel() > currentTreeDepth) currentTreeDepth = pair._1().getLevel();
			
			if (!timer.isTimeLeft()) return true;
			
			// default policy by looking for a random path
			double reward = defaultPolicy(pair);
			
			// backpropagate the reward
			backPropagation(pair._1(), reward);

			timer.addIteration();
		}

		return true;
	}

	private void backPropagation(MCTSNode n, double reward) {
		while (n != null) {
			n.addVisited();
			n.Q += reward;
			reward *= gamma;
			n = n.getFather();
		}
	}

	private double defaultPolicy(Pair<MCTSNode, StateObservation> pair) {

		MCTSNode n = pair._1();
		StateObservation stateObs = pair._2();
		DeltaScoreHeuristic heuristic = new DeltaScoreHeuristic(stateObs.getGameScore());

		int level = n.getLevel();
		while (!stateObs.isGameOver() && level <= maxDepth) {
			Types.ACTIONS a = Helper.getRandomEntry(stateObs.getAvailableActions());
			stateObs.advance(a);
			++level;
		}

		double delta = heuristic.evaluateState(stateObs);
		return delta;
	}

	private Pair<MCTSNode, StateObservation> treePolicy(Pair<MCTSNode, StateObservation> pair) {

		// get the MCTSNode and the state observation
		MCTSNode n = pair._1();
		StateObservation stateObs = pair._2();

		while (!stateObs.isGameOver() && n.getLevel() <= maxDepth) {

			if (!n.isFullyExpanded(stateObs)) {

				MCTSNode child = (MCTSNode) n.getRandomUnexpandedChildren(stateObs);
				Factory.getSimulator().advance(stateObs, child.getLastAction());
				child.checkLoosing(stateObs);
				
				return new Pair<MCTSNode, StateObservation>(child, stateObs);

			} else {
				Pair<MCTSNode, StateObservation> result = n.bestChild(new Pair<MCTSNode, StateObservation>(n, stateObs), heuristic);
				n = result._1();
				stateObs = result._2();
			}
		}
		return new Pair<MCTSNode, StateObservation>(n, stateObs);
	}
	



	@Override
	public ACTIONS act() {
		
		// select the best child!
		List<SortedPair<MCTSNode, Double>> nodes = new ArrayList<SortedPair<MCTSNode, Double>>();
		
		for (GenericNode<Object>  childGeneric : root.getAllChildren()) {
			
			MCTSNode child = (MCTSNode) childGeneric;
			
			if (child.isLoosingAtSimulation() == true)
				continue;
			
			double tiebreak = r.nextDouble() * MCTSNode.epsilon;
			
			nodes.add(new SortedPair<MCTSNode, Double>(child, child.getVisited() + tiebreak));
		}
		Collections.sort(nodes);
		if (nodes.size() > 0) {
			bestNode = nodes.get(0)._1();
			//System.out.println(bestNode);
			return bestNode.getFirstAction();
		} else
			return ACTIONS.ACTION_NIL;
		
	}

	
	
	public void rollingHorizon() {

		if (bestNode == null) return;
		
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
			if (n.getLevel() > currentTreeDepth) currentTreeDepth = n.getLevel();
			for(GenericNode<Object> childGeneric : n.getAllChildren()) {
				queue.add((MCTSNode) childGeneric);
			}
			
		}
	}

	@Override
	public String toString() {
		act();
		String s = String.format("Best:%s | \nDepth:%s \n", bestNode.toString(), currentTreeDepth);
		s += "---------------------\n";
		for (GenericNode<Object> child : root.getAllChildren()) {
			s += child.toString() + '\n';
		}
		s += "---------------------";
		return s;
	}

	public void setHeuristic(AHeuristic heuristic) {
		this.heuristic = heuristic;
	}

	public AHeuristic getHeuristic() {
		return heuristic;
	}
	

}
