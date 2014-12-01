package emergence_NI.GeneticStrategy.GeneticOperators;

import java.util.ArrayList;
import java.util.Random;

import emergence_NI.GeneticStrategy.Chromosom.Chromosom;
import emergence_NI.GeneticStrategy.Chromosom.Population;

public abstract class ASelection {
	
	public abstract void select(Population pop, int n, int chromosomLength, Random r);

	public abstract ArrayList<Chromosom> selectCopy(ArrayList<Chromosom> pop, int n, int chromosomLength, Random r);
	
}
