package emergence_NI.GeneticStrategy.Chromosom;

import core.game.StateObservation;
import emergence_NI.heuristic.AHeuristic;

public class HeuristicRating extends AChromosomRating{

	public HeuristicRating(AHeuristic heuristic) {
		super();
	}

	@Override
	public void rateChromosom(StateObservation stateObs, Chromosom chr) {

		for(Integer action : chr.actions)
		chr.score = 0.0;
	}
	
}
