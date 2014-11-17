package emergence_RL.uct.defaultPoliciy;

import java.util.ArrayList;

import ontology.Types;
import core.game.StateObservation;
import emergence_RL.helper.Helper;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

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
				return Double.POSITIVE_INFINITY;
			else if (winner == Types.WINNER.PLAYER_LOSES)
				return 0;
		} 

		double delta = currentStateObs.getGameScore() - n.stateObs.getGameScore();
		return delta;

	}
	
	
	public static ArrayList<Types.ACTIONS> getForbiddenMoves(ArrayList<Types.ACTIONS> allActions, Types.ACTIONS lastAction) {
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
		return nextActions;
	}

}
