package emergence.safety;

import ontology.Types.ACTIONS;
import ontology.Types.WINNER;
import core.game.StateObservation;
import emergence.Factory;

/**
 * A Safety which checks a action by executing the action several times.
 * 
 * @author spakken
 *
 */
public class SafetyAdvance extends ASafety {

	/** number of iterations */
	private int numOfIterations;

	/**
	 * Create a new SafetyAdvance with a number of iterations.
	 * 
	 * @param n
	 */
	public SafetyAdvance(int n) {
		this.numOfIterations = n;
	}

	/**
	 * Returns true if a action is safe. The action is executed a specified
	 * number of times, if the player loses in one of the executions it returns
	 * false, true otherwise.
	 */
	@Override
	public boolean isSafe(StateObservation stateObs, ACTIONS a) {
		for (int i = 0; i < numOfIterations; i++) {
			StateObservation tmp = stateObs.copy();
			Factory.getSimulator().advance(tmp, a);
			if (tmp.getGameWinner() == WINNER.PLAYER_LOSES)
				return false;
		}
		return true;
	}

	/**
	 * Generate a String object which is used in csv files.
	 */
	@Override
	public String toCSVString() {
		return "SafetyAdvance," + Integer.toString(numOfIterations);
	}

}
