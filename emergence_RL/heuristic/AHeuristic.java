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


	public static AHeuristic getNPCHeuristic() {
		AHeuristic heuristic = new EquationStateHeuristic(
				"camelRace/eggomania", new double[] { 83.56525340105779,
						-94.18003693788877, 63.8497743799621,
						52.91407845744166, -89.16201858673986,
						50.898113590684744, -59.55816967825811,
						41.28391268668591, -70.88223625353956,
						-17.469607503662886 });
		return heuristic;
	}

	
	public static AHeuristic getPortalHeuristic() {
		AHeuristic heuristic = new EquationStateHeuristic(
				"aliens/butterflies/missilecommand", new double[] {
						-40.62327505720693, -58.69258914953923,
						-49.606898527438204, -81.32390879388393,
						71.43675019986114, -61.35483540585036,
						-61.11143207906202, -87.16329141011143,
						-62.109108312834024, 69.37755076132808 });
		return heuristic;
	}

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
