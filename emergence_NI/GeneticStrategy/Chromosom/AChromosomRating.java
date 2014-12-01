package emergence_NI.GeneticStrategy.Chromosom;

import emergence_NI.heuristic.AHeuristic;

public abstract class AChromosomRating {
	AHeuristic heuristic;
	
	public AChromosomRating(AHeuristic heuristic){
		this.heuristic = heuristic;
	}
	
	public abstract void rateChromosom(Chromosom chr);
}
