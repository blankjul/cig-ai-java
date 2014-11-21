package emergence_RL.strategies.uct.defaultPolicy;

import java.util.ArrayList;

import ontology.Types;
import core.game.StateObservation;
import emergence_RL.helper.Helper;
import emergence_RL.strategies.uct.UCTSettings;
import emergence_RL.tree.Node;

public class RandomPolicy extends ADefaultPolicy {


	@Override
	public double expand(UCTSettings s, Node n) {

		StateObservation currentStateObs = n.stateObs.copy();
		Types.ACTIONS currentAction = null;


		ArrayList<Types.ACTIONS> allActions = currentStateObs
				.getAvailableActions();

		int level = n.level;
		double delta = 0;

		while (!currentStateObs.isGameOver() && delta == 0
				&& level <= s.maxDepth) {
			
			currentAction = Helper.getRandomEntry(allActions, s.r);
			currentStateObs.advance(currentAction);
			delta = currentStateObs.getGameScore() - n.stateObs.getGameScore();
			++level;
		}
		
		
		if (currentStateObs.isGameOver()) {
			Types.WINNER winner = currentStateObs.getGameWinner();
			if (winner == Types.WINNER.PLAYER_WINS)
				return 1000;
			else if (winner == Types.WINNER.PLAYER_LOSES)
				return -1000;
		}

		if (delta > 0)
			delta = 1;
		else if (delta < 0)
			delta = -1;

		return delta;

	}

}
