package emergence.heuristics;

import ontology.Types.WINNER;
import core.game.StateObservation;

/**
 * A simple Heuristic to compute the reward of a new state by just consider the
 * win, loss and the score.
 * 
 * @author spakken
 *
 */
public class DeltaScoreHeuristic extends AHeuristic {

	/** the start score to compare */
	double startScore;

	/**
	 * Construct a DeltaScoreHeuristic by setting the start score.
	 * 
	 * @param startScore
	 */
	public DeltaScoreHeuristic(double startScore) {
		this.startScore = startScore;
	}

	/**
	 * Evaluate the state of the given state observation. Returns 10 if the
	 * player wins, -10 if the winner loses and the the difference between the
	 * score otherwise.
	 * 
	 * @param stateObs
	 * @return
	 */
	@Override
	public double evaluateState(StateObservation stateObs) {
		if (stateObs.getGameWinner() == WINNER.PLAYER_WINS) {
			return 10;
		} else if (stateObs.getGameWinner() == WINNER.PLAYER_LOSES) {
			return -10;
		} else {
			return stateObs.getGameScore() - startScore;
		}

	}

	/**
	 * Generate a String object which is used in csv files.
	 */
	public String toCSVString() {
		return "DeltaScoreHeuristic";
	}

}
