package emergence_RL.uct.defaultPoliciy;

import java.util.ArrayList;

import ontology.Types;
import emergence_RL.helper.ActionMap;
import emergence_RL.heuristic.AHeuristic;
import emergence_RL.heuristic.EquationStateHeuristic;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public class GreedyPolicy extends ADefaultPolicy {

	
	@Override
	public double expand(UCTSettings s, Node n) {

		lastBounds[0] = curBounds[0];
		lastBounds[1] = curBounds[1];
		
		//AHeuristic heuristic = new SimpleStateHeuristic();
		AHeuristic heuristic = new EquationStateHeuristic("camelRace/eggomania", new double[] {71.51606955238063,-0.10874248901326666,1.46935755801519,58.91949024357237,-46.09021025115321,-57.43379973569722,57.57362881912201,-73.6456264953129,-31.50978515374345,-52.41586298782184});

		ArrayList<Types.ACTIONS> actionList = n.stateObs.getAvailableActions();
		ActionMap map = ActionMap.create(actionList);
		
		while (n != null && !n.stateObs.isGameOver() && n.level <= s.maxDepth) {
			
			double bestHeuristic = Double.NEGATIVE_INFINITY;
			Node bestChild = null;
			
			// for each possible action
			for (int i = 0; i < actionList.size(); i++) {

				// action that has to be performed
				Types.ACTIONS a = actionList.get(i);
				Node child = n.getChild(a);
				
				// delete reference again! this is a dirty hack :/
				int index = map.getInt(a);
				n.children[index] = null;

				// check for best heuristic and this heuristic
				double score = heuristic.evaluateState(child.stateObs);
				
				if (score > bestHeuristic) {
					bestHeuristic = score;
					bestChild = child;
				}
			}
			n = bestChild;
			
		}
		
		if ( n == null) return 0;

		double normDelta = getNormalizedReward(n.stateObs);
		
		return normDelta;
	}



}
