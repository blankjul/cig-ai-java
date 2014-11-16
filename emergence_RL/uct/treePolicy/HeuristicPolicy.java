package emergence_RL.uct.treePolicy;

import java.util.ArrayList;

import emergence_RL.helper.Helper;
import emergence_RL.heuristic.AHeuristic;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public class HeuristicPolicy extends ATreePolicy {

	private AHeuristic heuristic;
	
	public HeuristicPolicy (AHeuristic heuristic) {
		this.heuristic = heuristic;
	}
	
	// epsilon for the utc formula
	public double epsilon = 1e-6;

	public double heuristicWeight = 2;

	public Node bestChild(UCTSettings s, Node n, double c) {

		double bestHeuristic = Double.NEGATIVE_INFINITY;
		ArrayList<Node> bestNodes = new ArrayList<Node>();

		for (Node child : n.getChildren()) {

			double heuristicValue = heuristic.evaluateState(child.stateObs);

			if (heuristicValue == bestHeuristic) {
				bestNodes.add(child);
			} else if (bestHeuristic > heuristicValue) {
				bestNodes.clear();
				bestNodes.add(child);
				bestHeuristic = heuristicValue;
			}
		}
		if (bestNodes.isEmpty())
			return n;
		
		Node result = Helper.getRandomEntry(bestNodes, s.r);
		return result;

	}
}
