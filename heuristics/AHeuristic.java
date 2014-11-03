package emergence_HR.heuristics;

import ontology.Types;
import tools.Vector2d;
import core.game.StateObservation;

/**
 * Main type of heuristic is the StateHeuristic. Every idea that is heuristic
 * based should inherit from this class. The method evaluateState returns if we
 * did win or lose, the most positive or negative value because this should be
 * equal for all heuristics.
 */
public abstract class AHeuristic {

	// the position of the avatar
	protected Vector2d avatarPosition;


	/**
	 * Method that must be overridden by children classes.
	 * 
	 * @return heuristic value if we do not win!
	 */
	abstract protected double getRank(StateObservation stateObs);

	/**
	 * This method returns the value if the heuristic. for every heuristic if
	 * the game is not finished it looks for the abstract method getRank()
	 * 
	 * @param stateObs
	 * @return value of the heuristic
	 */
	public double evaluateState(StateObservation stateObs) {

		Types.WINNER w = stateObs.getGameWinner();
		if (w == Types.WINNER.PLAYER_WINS) {
			return Double.POSITIVE_INFINITY;
		} else if (w == Types.WINNER.PLAYER_LOSES) {
			return Double.NEGATIVE_INFINITY;

			// if we are not winning or loosing
		} else {
			avatarPosition = stateObs.getAvatarPosition();
			return getRank(stateObs);
		}
	}

	/**
	 * Calculates the Manhattan distance to one object!
	 * 
	 * @param from
	 *            the source
	 * @param to
	 *            destination
	 * @return Manhattan distance
	 */
	public double distance(Vector2d from, Vector2d to) {
		return Math.abs(from.x - to.x) + (from.y - to.y);
	}

}
