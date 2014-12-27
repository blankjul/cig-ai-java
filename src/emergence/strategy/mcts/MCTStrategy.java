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

public class MCTStrategy extends AStrategy {

	
	// maximal depth of the tree
	public int maxDepth = 10;

	// this is the discounting factor. it's one so disabled default
	public double gamma = 0.1;

	// game tree that should be expanded by this class
	public MCTSNode root;

	public int currentTreeDepth = 0;

	public AHeuristic heuristic = null;
	
	private MCTSNode bestNode;
	

	
	public MCTStrategy(MCTSNode root) {
		super();
		this.root = root;
		bestNode = root;
	}

	
	@Override
	public boolean expand(StateObservation stateObs, ActionTimer timer) {
		
		// get all actions that are available
		Set<ACTIONS> allActions = new HashSet<ACTIONS>(stateObs.getAvailableActions());
		

		while (timer.isTimeLeft()) {

			// tree policy by iteration through the tree
			MCTSNode n = treePolicy(root, allActions);
			
			// adapt the current tree depth
			if (n.getLevel() > currentTreeDepth) currentTreeDepth = n.getLevel();
			
			// check if there is enough time
			if (!timer.isTimeLeft()) return true;
			
			// default policy by looking for a random path
			double reward = defaultPolicy(n, stateObs.copy());
			
			// backpropagate the reward
			backPropagation(n, reward);

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

	private double defaultPolicy(MCTSNode n, StateObservation stateObs) {

		DeltaScoreHeuristic deltaHeuristic = new DeltaScoreHeuristic(stateObs.getGameScore());
		
		// simulate this node and the given state obs
		n.simulate(stateObs);
		if (heuristic != null) n.heuristicValue = heuristic.evaluateState(stateObs);

		// add random simulation
		int level = n.getLevel();
		while (!stateObs.isGameOver() && level <= maxDepth) {
			Types.ACTIONS a = Helper.getRandomEntry(stateObs.getAvailableActions());
			Factory.getSimulator().advance(stateObs, a);
			++level;
		}

		// calculate the heuristic value
		double delta = deltaHeuristic.evaluateState(stateObs);
		return delta;
	}

	
	private MCTSNode treePolicy(MCTSNode n, Set<ACTIONS> allActions) {
		// get the MCTSNode and the state observation
		while (n.getLevel() <= maxDepth) {
			if (!n.isFullyExpanded(allActions)) {
				MCTSNode child = (MCTSNode) n.getRandomUnexpandedChildren(allActions);
				return child;
			} else {
				MCTSNode result = n.bestChild(n);
				n = result;
			}
		}
		return n;
	}
	



	@Override
	public ACTIONS act() {
		// get all the children and compare the visited integer
		List<MCTSNode> nodeList = new ArrayList<>();
		for (GenericNode<Object>  childGeneric : root.getAllChildren()) {
			MCTSNode child = (MCTSNode) childGeneric;
			nodeList.add(child);
		}
		MostVisitedNodeComparator comp = new MostVisitedNodeComparator();
		Collections.sort(nodeList, comp);
		
		// get all the best nodes. Could be that more than one have the max values
		if (nodeList.size() == 0) return ACTIONS.ACTION_NIL;
		else {
			// find the first point where they are not equal
			int i = 0;
			for (; i < nodeList.size() - 1; i++) {
				if (comp.compare(nodeList.get(i), nodeList.get(i+1)) != 0) break;
			}
			nodeList = nodeList.subList(0, i + 1);
			// now the nodeList contains all nodes with the maximal equal result
			if (heuristic == null) {
				bestNode = Helper.getRandomEntry(nodeList);
			} else {
				List<MCTSNode> heuristicList = new ArrayList<>(nodeList);
				Collections.sort(heuristicList, new BestHeuristicNodeComparator());
				bestNode = heuristicList.get(0);
			}
			return bestNode.getFirstAction();
		}
		
	}

	
	
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
		root.print(1);
		if (heuristic != null) System.out.println(heuristic);
		s += "---------------------";
		return s;
	}

	public void setHeuristic(AHeuristic heuristic) {
		this.heuristic = heuristic;
	}

	public AHeuristic getHeuristic() {
		return heuristic;
	}
	
	public String toCSVString(){
		String csv = "";
		csv += Integer.toString(this.maxDepth) + ",";
		csv += Double.toString(this.gamma) + ",";
		csv += (this.heuristic == null ? "null" : this.heuristic.toCSVString());
		
		return csv;
	}

}
