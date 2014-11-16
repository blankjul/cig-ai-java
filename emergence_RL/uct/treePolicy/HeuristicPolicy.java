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

		double bestUTC = Double.NEGATIVE_INFINITY;
		ArrayList<Node> bestNodes = new ArrayList<Node>();

		Node result = null;

		for (Node child : n.getChildren()) {

			double heuristicValue = heuristic.evaluateState(child.stateObs)
					- heuristic.evaluateState(n.stateObs);

			child.uct = child.Q
					/ (child.visited + epsilon)
					+ c
					* Math.sqrt(Math.log(n.visited + 1)
							/ (child.visited + epsilon)) + s.r.nextDouble()
					* epsilon + heuristicWeight * heuristicValue;

			if (child.uct == bestUTC) {
				bestNodes.add(child);
			} else if (child.uct > bestUTC) {
				bestNodes.clear();
				bestNodes.add(child);
				bestUTC = child.uct;
			}
		}
		if (bestNodes.isEmpty())
			return n;
		result = Helper.getRandomEntry(bestNodes, s.r);
		return result;

	}
}
