package emergence_RL.uct.defaultPoliciy;

import ontology.Types;
import tools.Vector2d;
import core.game.StateObservation;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public abstract class ADefaultPolicy {

	abstract public double expand(UCTSettings s, Node n);

	protected static double[] lastBounds = new double[] { 0, 1 };
	protected static double[] curBounds = new double[] { 0, 1 };

	
	
	// Normalizes a value between its MIN and MAX.
	protected double normalise(double a_value, double a_min, double a_max) {
		return (a_value - a_min) / (a_max - a_min);
	}

	protected String hash(StateObservation stateObs, Types.ACTIONS lastAction) {
		Vector2d pos = stateObs.getAvatarPosition();
		String used = (lastAction == Types.ACTIONS.ACTION_USE) ? "y" : "n";
		return String.format("[%s,%s,%s]", pos.x, pos.y, used);
	}
	
	
	
	protected double getNormalizedReward(StateObservation stateObs) {
		double delta = stateObs.getGameScore();

		if (stateObs.isGameOver()) {

			if (stateObs.getGameWinner() == Types.WINNER.PLAYER_WINS)
				return Double.POSITIVE_INFINITY;
			else if (stateObs.getGameWinner() == Types.WINNER.PLAYER_LOSES)
				return Double.NEGATIVE_INFINITY;
		}
		
		if (delta < curBounds[0])
			curBounds[0] = delta;
		if (delta > curBounds[1])
			curBounds[1] = delta;

		double normDelta = normalise(delta, lastBounds[0], lastBounds[1]);
		return normDelta;
	}

}
