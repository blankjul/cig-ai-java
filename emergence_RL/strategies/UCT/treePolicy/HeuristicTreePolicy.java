package emergence_RL.strategies.UCT.treePolicy;

import java.util.ArrayList;

import ontology.Types;
import emergence_RL.Agent;
import emergence_RL.strategies.UCT.UCTSearch;
import emergence_RL.tree.Node;

public class HeuristicTreePolicy extends ATreePolicy {

	
	public Node expand(UCTSearch search, Node n) {
		while (!n.stateObs.isGameOver() && n.level <= search.maxDepth) {
			if (!n.isFullyExpanded()) {
				
				Node child = n.getRandomChild(UCTSearch.r, true);
				if (child.level == 1 && search.pessimisticIterations > 0)
					pessimisticExploring(child, search.pessimisticIterations);
				
				return child;
			} else {
				n = bestChild(search, n, search.c);
			}
		}
		return n;
	}
	
	/*
	
	private HashSet<Node> firstLevelPessimistic = new HashSet<Node>();
	
	
	@Override
	public Node expand(UCTSearch search, Node n) {
		while (!n.stateObs.isGameOver() && n.level <= search.maxDepth) {
			
			if (n.level == 1 && search.pessimisticIterations > 0 && !firstLevelPessimistic.contains(n)) {
				firstLevelPessimistic.add(n);
				pessimisticExploring(n, search.pessimisticIterations);
			}
			
			n = bestChild(search, n, search.c);
		}
		return n;
	}
	
*/
	
	@Override
	public Node bestChild(UCTSearch search, Node n, double c) {

		Node bestChild = null;
		double bestValue = Double.NEGATIVE_INFINITY;

		ArrayList<Types.ACTIONS> unexpandedActions = new ArrayList<Types.ACTIONS>();
		ArrayList<Types.ACTIONS> actionList = n.stateObs.getAvailableActions();

		for (int i = 0; i < actionList.size(); i++) {

			// action that has to be performed
			Types.ACTIONS a = actionList.get(i);
			int index = Agent.map.getInt(a);

			if (n.children[index] == null) {
				unexpandedActions.add(a);
				continue;
			}
			
			Node child = n.children[index];

			child.exploitation = child.Q / (child.visited + UCTSearch.epsilon);

			child.exploration = Math.sqrt(Math.log(n.visited + 1)
					/ (child.visited));

			// heuristic by using the target
			child.heuristicValue = 0;
			if (search.heuristic != null) {
				search.heuristic.evaluateState(child.stateObs);
				for (int j = 0; j < search.heuristic.distances.size(); j++) {
					child.heuristicValue += search.heuristic.weights[j]
							* search.heuristic.distances.get(j);
				}
			}

			// history of field
			String h = child.hash();
			Integer visitsOfField = Agent.fieldVisits.get(h);
			child.historyValue = 1;
			if (visitsOfField != null && Agent.maxVisitedField > 0) {
				child.historyValue = c
						* Math.sqrt((1 - visitsOfField
								/ (double) Agent.maxVisitedField));
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

		/*
		if (bestValue < search.urgentUCTValue && !unexpandedActions.isEmpty()) {
			Types.ACTIONS a = Helper.getRandomEntry(unexpandedActions, UCTSearch.r);
			bestChild = n.getChild(a);
		}
		
		if (bestChild == null) {
			n.getRandomChild(UCTSearch.r, false);
		}

*/
		return bestChild;
	}

}
