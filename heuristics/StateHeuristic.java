package emergence_HR.heuristics;

import ontology.Types;
import tools.Vector2d;
import core.game.StateObservation;

/**
 * That is the main type of heuristics. all that are created and used should
 * inherit from this class.
 * 
 */
public abstract class StateHeuristic {

	Vector2d avatarPosition;

	
	
	public StateHeuristic() {

	}

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
			return Double.MAX_VALUE;
		} else if (w == Types.WINNER.PLAYER_LOSES) {
			return Double.MIN_VALUE;

			// if we are not winning or loosing
		} else {
			avatarPosition = stateObs.getAvatarPosition();
			return getRank(stateObs);
		}
	}
	
	/**
	 * Calculates the Manhattan distance to one object!
	 * @param from the source
	 * @param to destination
	 * @return Manhattan distance
	 */
	public double distance(Vector2d from, Vector2d to) {
		return Math.abs(from.x - to.x) + (from.y - to.y);
	}

}
