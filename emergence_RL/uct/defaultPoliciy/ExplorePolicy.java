package emergence_RL.uct.defaultPoliciy;

import java.util.ArrayList;
import java.util.HashSet;

import ontology.Types;
import core.game.StateObservation;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public class ExplorePolicy extends RandomPolicy {


	@Override
	public double expand(UCTSettings s, Node n) {

		lastBounds[0] = curBounds[0];
		lastBounds[1] = curBounds[1];

		StateObservation stateObs = n.stateObs.copy();
		HashSet<String> map = new HashSet<String>();

		
		int level = n.level;
		while (!n.stateObs.isGameOver() && level <= s.maxDepth) {

			ArrayList<Types.ACTIONS> actions = stateObs.getAvailableActions();
			Types.ACTIONS a = Types.ACTIONS.ACTION_NIL;
			
			while (actions.size() > 0) {
				StateObservation tmp = stateObs.copy();
				
				// pick a random action
				int index = s.r.nextInt(actions.size());
				a = actions.get(index);
				actions.remove(index);
				tmp.advance(a);
				
				boolean visited = map.contains(hash(tmp,a));
				if (!visited) {
					stateObs = tmp;
					break;
				}
				
			}
			map.add(hash(stateObs, a));
			++level;
		}

		double normDelta =  getNormalizedReward(stateObs);
		return normDelta;

	}
	

	protected double getNormalizedReward(StateObservation stateObs) {
		double delta = stateObs.getGameScore();

		if (stateObs.isGameOver()) {

			if (stateObs.getGameWinner() == Types.WINNER.PLAYER_WINS)
				return Double.POSITIVE_INFINITY;
			else if (stateObs.getGameWinner() == Types.WINNER.PLAYER_LOSES)
				return -1;
		}

		double normDelta = normalise(delta, lastBounds[0], lastBounds[1]);
		return normDelta;
	}



}
