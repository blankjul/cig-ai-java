package emergence_RL.uct.defaultPoliciy;

import ontology.Types;
import core.game.StateObservation;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public class RandomPolicy extends ADefaultPolicy {


	
	
	@Override
	public double expand(UCTSettings s, Node n) {

		lastBounds[0] = curBounds[0];
		lastBounds[1] = curBounds[1];

		StateObservation stateObs = n.stateObs.copy();
		int level = n.level;
		while (!n.stateObs.isGameOver() && level <= s.maxDepth) {
			Types.ACTIONS a = n.getRandomAction(s.r);
			stateObs.advance(a);
			++level;
		}

		double normDelta =  getNormalizedReward(stateObs);
		return normDelta;
	}



}
