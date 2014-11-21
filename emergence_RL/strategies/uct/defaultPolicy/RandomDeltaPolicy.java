package emergence_RL.strategies.uct.defaultPolicy;

import java.util.ArrayList;

import ontology.Types;
import core.game.StateObservation;
import emergence_RL.helper.Helper;
import emergence_RL.strategies.uct.UCTSettings;
import emergence_RL.tree.Node;

public class RandomDeltaPolicy extends ADefaultPolicy {


	@Override
	public double expand(UCTSettings s, Node n) {

		StateObservation currentStateObs = n.stateObs.copy();

		ArrayList<Types.ACTIONS> actions = currentStateObs
				.getAvailableActions();

		int level = n.level;
		while (!currentStateObs.isGameOver() && level <= s.maxDepth) {
			Types.ACTIONS a = Helper.getRandomEntry(actions, s.r);
			currentStateObs.advance(a);
			++level;
		}
		
		if (currentStateObs.isGameOver()) {
			Types.WINNER winner = currentStateObs.getGameWinner();
			if (winner == Types.WINNER.PLAYER_WINS)
				return +100;
			else if (winner == Types.WINNER.PLAYER_LOSES)
				return -1;
		} 
		
		double delta = currentStateObs.getGameScore() - n.stateObs.getGameScore();
		return delta;

	}
	
	

}
