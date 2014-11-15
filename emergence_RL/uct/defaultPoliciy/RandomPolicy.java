package emergence_RL.uct.defaultPoliciy;

import ontology.Types;
import core.game.StateObservation;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public class RandomPolicy implements IDefaultPolicy {

	private static double[] lastBounds = new double[] { 0, 1 };
	private static double[] curBounds = new double[] { 0, 1 };

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

		double delta = n.stateObs.getGameScore();

		if (n.stateObs.isGameOver()
				&& n.stateObs.getGameWinner() == Types.WINNER.PLAYER_LOSES)
			return -100000000;

		if (n.stateObs.isGameOver()
				&& n.stateObs.getGameWinner() == Types.WINNER.PLAYER_WINS)
			return +100000000;

		if (delta < curBounds[0])
			curBounds[0] = delta;
		if (delta > curBounds[1])
			curBounds[1] = delta;

		double normDelta = normalise(delta, lastBounds[0], lastBounds[1]);
		return normDelta;
	}

	// Normalizes a value between its MIN and MAX.
	private double normalise(double a_value, double a_min, double a_max) {
		return (a_value - a_min) / (a_max - a_min);
	}

}
