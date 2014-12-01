package emergence_NI.GeneticStrategy.GeneticOperators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import emergence_NI.GeneticStrategy.Chromosom.Chromosom;
import emergence_NI.GeneticStrategy.Chromosom.Population;

public class NBestSelection extends ASelection{
	
	public void select(Population pop, int n, int chromosomLength, Random r){
		//out of range
		if(n > chromosomLength){
			n = chromosomLength;
		}
		//take the first/best n Chromosom and delete all others
		pop.chromoms.subList(0, n).clear();
	}
	
	public ArrayList<Chromosom> selectCopy(ArrayList<Chromosom> pop, int n, int chromosomLength, Random r){
		//out of range
		if(n > chromosomLength){
			n = chromosomLength;
		}
		
		//take the first/best n Chromosom and generate the population
		ArrayList<Chromosom> selected = new ArrayList<Chromosom>();
		selected.addAll(pop.subList(0, n));
		
		return (ArrayList<Chromosom>) selected.clone();
	}
}
