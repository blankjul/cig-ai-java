package emergence_NI.GeneticStrategy.GeneticOperators;

import java.util.Random;

import emergence_NI.GeneticStrategy.Chromosom.Chromosom;
import emergence_NI.GeneticStrategy.Chromosom.Population;

public abstract class ACrossover {
	
	public abstract void crossover(Chromosom first,Chromosom second, Random r);
	
	public abstract void crossoverCopy(Population pop, Chromosom first,Chromosom second, Random r);
}
