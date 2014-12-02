package emergence_NI.GeneticStrategy.Chromosom;

import core.game.StateObservation;
import emergence_NI.heuristic.AHeuristic;

public abstract class AChromosomRating {
	
	public AChromosomRating(){
	}
	
	public abstract void rateChromosom(StateObservation stateObs, Chromosom chr);
}
