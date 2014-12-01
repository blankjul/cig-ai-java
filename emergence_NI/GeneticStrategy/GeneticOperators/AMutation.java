package emergence_NI.GeneticStrategy.GeneticOperators;

import java.util.Random;

import emergence_NI.GeneticStrategy.Chromosom.Chromosom;
import emergence_NI.GeneticStrategy.Chromosom.Population;

public abstract class AMutation {

	public abstract void mutate(Chromosom chr, Random r);
	
	public abstract void mutateCopy(Population pop, Chromosom chr, Random r);
}
