package emergence_NI.GeneticStrategy.Chromosom;

import emergence_NI.heuristic.AHeuristic;

public class HeuristicRating extends AChromosomRating{

	public HeuristicRating(AHeuristic heuristic) {
		super(heuristic);
	}

	@Override
	public void rateChromosom(Chromosom chr) {

		chr.score = 0.0;
	}
	
}
