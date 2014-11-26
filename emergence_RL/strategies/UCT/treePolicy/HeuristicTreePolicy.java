package emergence_RL.strategies.UCT.treePolicy;

import java.util.ArrayList;

import ontology.Types;
import emergence_RL.FieldTracker;
import emergence_RL.strategies.UCTSearch;
import emergence_RL.tree.Node;

public class HeuristicTreePolicy extends ATreePolicy {

	

	
	@Override
	public Node bestChild(UCTSearch search, Node n, double c) {

		Node bestChild = null;
		double bestValue = Double.NEGATIVE_INFINITY;

		ArrayList<Types.ACTIONS> unexpandedActions = new ArrayList<Types.ACTIONS>();
		ArrayList<Types.ACTIONS> actionList = n.stateObs.getAvailableActions();

		for (int i = 0; i < actionList.size(); i++) {

			// action that has to be performed
			Types.ACTIONS a = actionList.get(i);
			
			if (i >= n.children.length) continue;
			
			if (n.children[i] == null) {
				unexpandedActions.add(a);
				continue;
			}
			
			Node child = n.children[i];

			child.exploitation = child.Q / (child.visited + UCTSearch.epsilon);

			child.exploration = Math.sqrt(Math.log(n.visited + 1)
					/ (child.visited));

			// heuristic by using the target
			child.heuristicValue = 0;
			if (search.heuristic != null) {
				child.heuristicValue = search.heuristic.evaluateState(child.stateObs);
			}

			// history of field
			String h = child.hash();
			Integer visitsOfField = FieldTracker.fieldVisits.get(h);
			child.historyValue = 1;
			if (visitsOfField != null && FieldTracker.maxVisitedField > 0) {
				child.historyValue = c
						* Math.sqrt((1 - visitsOfField
								/ (double) FieldTracker.maxVisitedField));
			}

			double[] weights = search.weights;
			child.uct = weights[0] * child.exploitation + weights[1]
					* child.exploration + weights[2] * child.heuristicValue
					+ weights[3] * child.historyValue;

			
			// check if it has the best value
			if (child.uct >= bestValue) {
				bestChild = child;
				bestValue = child.uct;
			}
		}


		return bestChild;
	}

}
