package emergence_RL.strategies.UCT.treePolicy;

import emergence_RL.strategies.UCT.UCTSearch;
import emergence_RL.tree.Node;

public class HeuristicTreePolicy extends ATreePolicy {

	@Override
	public Node bestChild(UCTSearch search, Node n, double c) {

		Node bestChild = null;
		double bestValue = Double.NEGATIVE_INFINITY;

		for (Node child : n.getChildren()) {

			child.exploitation = child.Q / (child.visited + UCTSearch.epsilon);

			child.exploration = Math.sqrt(Math.log(n.visited + 1)
					/ (child.visited));

			// heuristic by using the target
			child.heuristicValue = 0;
			if (search.heuristic != null) {
				search.heuristic.evaluateState(child.stateObs);
				for (int i = 0; i < search.heuristic.distances.size(); i++) {
					child.heuristicValue += search.heuristic.weights[i]
							* search.heuristic.distances.get(i);
				}
			}

			// history of field
			String h = child.hash();
			Integer visitsOfField = UCTSearch.fieldVisits.get(h);
			child.historyValue = 1;
			if (visitsOfField != null && UCTSearch.maxVisitedField > 0) {
				child.historyValue = c
						* Math.sqrt((1 - visitsOfField
								/ (double) UCTSearch.maxVisitedField));
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
