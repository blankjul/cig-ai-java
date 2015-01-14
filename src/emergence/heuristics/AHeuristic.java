package emergence.heuristics;

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
	 * Generates a String of the parameters which can be used in an csv file.
	 * @return
	 */
	abstract public String toCSVString();

}
