package emergence.heuristics;

import ontology.Types.WINNER;
import core.game.StateObservation;

public class DeltaScoreHeuristic extends AHeuristic {

	double startScore;
	
	public DeltaScoreHeuristic(double startScore) {
		this.startScore = startScore;
	}
	
	
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
	
	

}
