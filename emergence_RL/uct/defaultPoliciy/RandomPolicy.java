package emergence_RL.uct.defaultPoliciy;

import ontology.Types;
import ontology.Types.WINNER;
import core.game.StateObservation;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public class RandomPolicy implements IDefaultPolicy {

	@Override
	public double expand(UCTSettings s, Node n) {
		Node root = n;
		
		StateObservation stateObs = n.stateObs.copy();
		int level = n.level;
		while (!n.stateObs.isGameOver() && level <= s.maxDepth) {
			Types.ACTIONS a = n.getRandomAction(s.r);
			stateObs.advance(a);
			++level;
		}
		
		// this is just an normalization step!
		// normally the result should between [0,1]
		double delta = calcScore(s, root, n);
		return delta;
	}
	
	private double calcScore(UCTSettings s, Node root, Node n) {
		if (n.stateObs.getGameWinner() == WINNER.PLAYER_WINS) {
			return 1.0;
		} else if (n.stateObs.getGameWinner() == WINNER.PLAYER_LOSES)  {
			return 0.0;
		// try to get a good value between zero and one!
		} else {
			double min = root.stateObs.getGameScore() - s.maxDepth;
			double max = root.stateObs.getGameScore() + s.maxDepth;
			double score = n.stateObs.getGameScore();
			// normalize to only positive values
			if (min < 0) {
				max += Math.abs(min);
				score += Math.abs(min);
				min = 0;
			}
			double result = score / max;
			if (result > 1) result = 1;
			else if (result < 0 ) result = 0;
			return result;
			
		}
		
	}

}
