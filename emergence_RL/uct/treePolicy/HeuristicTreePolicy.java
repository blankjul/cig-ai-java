package emergence_RL.uct.treePolicy;

import emergence_RL.heuristic.TargetHeuristic;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSearch;
import emergence_RL.uct.UCTSettings;

public class HeuristicTreePolicy extends ATreePolicy {

	
	@Override
	public Node bestChild(UCTSettings s, Node n, double c) {
		Node bestChild = null;
		double bestValue = Double.NEGATIVE_INFINITY;

		for (Node child : n.getChildren()) {

			child.exploitation = child.Q
					/ (child.visited + UCTSettings.epsilon);

			child.exploration = Math.sqrt(Math.log(n.visited + 1)
					/ (child.visited));

			// heuristic by using the target
			child.heuristicValue = new TargetHeuristic()
					.evaluateState(child.stateObs);
			child.targetHeuristicIndex = TargetHeuristic.lastUsed;

			// history of field
			String h = child.hash();
			Integer visitsOfField = UCTSearch.fieldVisits.get(h);
			child.historyValue = 1;
			double gameTick = child.stateObs.getGameTick();
			if (visitsOfField != null) {
				child.historyValue = 1 - visitsOfField / gameTick;
			}
			
			double uctValue = child.exploitation + c * child.exploration + child.heuristicValue
					+ child.historyValue;
			
			child.uct = uctValue;

			// check if it has the best value
			if (uctValue >= bestValue) {
				bestChild = child;
				bestValue = uctValue;
			}
		}

		return bestChild;
	}

}
