package emergence_RL.uct.treePolicy;

import emergence_RL.heuristic.AHeuristic;
import emergence_RL.heuristic.EquationStateHeuristic;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public class HeuristicPolicy extends ATreePolicy {

	public Node bestChild(UCTSettings s, Node n, double c) {

		// AHeuristic heuristic = new SimpleStateHeuristic();
		AHeuristic heuristic = new EquationStateHeuristic(
				"camelRace/eggomania", new double[] { 71.51606955238063,
						-0.10874248901326666, 1.46935755801519,
						58.91949024357237, -46.09021025115321,
						-57.43379973569722, 57.57362881912201,
						-73.6456264953129, -31.50978515374345,
						-52.41586298782184 });
		

		double bestHeuristic = Double.NEGATIVE_INFINITY;
		Node bestChild = null;

		for (Node child : n.getChildren()) {
			
			// check for best heuristic and this heuristic
			double score = heuristic.evaluateState(child.stateObs);

			if (score > bestHeuristic) {
				bestHeuristic = score;
				bestChild = child;
			}
		}
		return bestChild;

	}
}
