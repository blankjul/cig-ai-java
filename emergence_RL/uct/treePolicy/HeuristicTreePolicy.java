package emergence_RL.uct.treePolicy;

import emergence_RL.heuristic.AHeuristic;
import emergence_RL.heuristic.TargetHeuristic;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public class HeuristicTreePolicy extends ATreePolicy {

	// epsilon for the utc formula
	public double epsilon = 1e-6;

	public AHeuristic heuristic = new TargetHeuristic();
	
	

	@Override
	public Node bestChild(UCTSettings s, Node n, double c) {
		Node bestChild = null;
		double bestValue = Double.NEGATIVE_INFINITY;

		for (Node child : n.getChildren()) {

			double exploitation = child.Q / (child.visited + epsilon);
			
			double exploration = Math.sqrt(Math.log(n.visited + 1)
					/ (child.visited));
			
			double heuristicValue = heuristic.evaluateState(child.stateObs);
			
			
			double tieBreaker = s.r.nextDouble() * epsilon;

			double uctValue = exploitation + c * exploration + heuristicValue
					+ tieBreaker;

			// check if it has the best value
			if (uctValue > bestValue) {
				bestChild = child;
				bestValue = uctValue;
			}
		}

		return bestChild;
	}

}
