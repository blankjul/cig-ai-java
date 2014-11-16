package emergence_RL.uct.defaultPoliciy;

import java.util.ArrayList;

import ontology.Types;
import core.game.StateObservation;
import emergence_RL.helper.Helper;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public class RandomPolicy extends ADefaultPolicy {

	protected static double[] lastBounds = new double[] { 0, 1 };
	protected static double[] curBounds = new double[] { 0, 1 };

	@Override
	public double expand(UCTSettings s, Node n) {

		StateObservation currentStateObs = n.stateObs.copy();
		Types.ACTIONS currentAction = null;

		Types.ACTIONS lastAction = null;

		ArrayList<Types.ACTIONS> allActions = currentStateObs
				.getAvailableActions();

		int level = n.level;
		double delta = 0;

		while (!currentStateObs.isGameOver() && delta == 0
				&& level <= s.maxDepth) {

			ArrayList<Types.ACTIONS> nextActions = new ArrayList<Types.ACTIONS>();
			for (Types.ACTIONS a : allActions) {
				if (lastAction != null) {
					if (lastAction == Types.ACTIONS.ACTION_RIGHT
							&& a == Types.ACTIONS.ACTION_LEFT)
						continue;
					else if (lastAction == Types.ACTIONS.ACTION_LEFT
							&& a == Types.ACTIONS.ACTION_RIGHT)
						continue;
					else if (lastAction == Types.ACTIONS.ACTION_UP
							&& a == Types.ACTIONS.ACTION_DOWN)
						continue;
					else if (lastAction == Types.ACTIONS.ACTION_DOWN
							&& a == Types.ACTIONS.ACTION_UP)
						continue;
				}
				nextActions.add(a);
			}

			currentAction = Helper.getRandomEntry(nextActions, s.r);
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
