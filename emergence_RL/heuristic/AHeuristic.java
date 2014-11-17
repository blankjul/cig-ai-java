package emergence_RL.heuristic;

import tools.Vector2d;
import core.game.StateObservation;

/**
 * Main type of heuristic is the StateHeuristic. Every idea that is heuristic
 * based should inherit from this class. The method evaluateState returns if we
 * did win or lose, the most positive or negative value because this should be
 * equal for all heuristics.
 */
public abstract class AHeuristic {


	/**
	 * This method returns the value if the heuristic. for every heuristic if
	 * the game is not finished it looks for the abstract method getRank()
	 * 
	 * @param stateObs
	 * @return value of the heuristic
	 */
	abstract public double evaluateState(StateObservation stateObs);

	/**
	 * Calculates the Manhattan distance to one object!
	 * 
	 * @param from
	 *            the source
	 * @param to
	 *            destination
	 * @return Manhattan distance
	 */
	public static double distance(Vector2d from, Vector2d to) {
		return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
	}

}
