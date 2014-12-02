package emergence_NI.GeneticStrategy.Chromosom;

import java.util.ArrayList;

import ontology.Types;
import core.game.StateObservation;
import emergence_NI.helper.ActionMap2;
import emergence_NI.heuristic.AHeuristic;
import emergence_NI.helper.Helper;
import emergence_NI.tree.Node;

public class NormalRating extends AChromosomRating{

	public NormalRating() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void rateChromosom(StateObservation stateObs, Chromosom chr) {
		StateObservation currentStateObs = stateObs.copy();
		
		double endscore;
		double startingScore = currentStateObs.getGameScore();

		for(int i = 0; i < chr.actions.length; i++){
			Types.ACTIONS action = ActionMap2.getAction(chr.actions[i]);
			currentStateObs.advance(action);
		}
		

		if (currentStateObs.isGameOver()) {
			Types.WINNER winner = currentStateObs.getGameWinner();
			if (winner == Types.WINNER.PLAYER_WINS)
				endscore = 100;
			else if (winner == Types.WINNER.PLAYER_LOSES)
				endscore = -1;
		}

		endscore = currentStateObs.getGameScore() - startingScore;
		
		//set endscore
		chr.score = endscore;
		chr.rated = true;
	}
	
}
