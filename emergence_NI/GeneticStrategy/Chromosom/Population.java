package emergence_NI.GeneticStrategy.Chromosom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * a Population is just a list of Chromosomes
 * it has to be ordered by using the .sort() 
 * method before doing something
 * @author spakken
 *
 */
public class Population {
	
	public List<Chromosom> chromoms;
	
	//added score of all chromosoms, set by GenStrategy.evaluation
	public double score;
	
	/**
	 * create a Population without knowing the populationSize
	 */
	public Population(){
		this.chromoms = new ArrayList<Chromosom>();
	}
	
	/**
	 * create a Population with the population size
	 * @param startSize
	 */
	public Population(int startSize){
		this.chromoms = new ArrayList<Chromosom>(startSize);
	}
	
	/**
	 * create a Population from an ArrayList of Chromosoms
	 * @param pop
	 */
	public Population(ArrayList<Chromosom> chroms){
		this.chromoms = new ArrayList<Chromosom>();
		this.chromoms.addAll(chroms);
	}
	
	/**
	 * sort the List acording to the score of the Chromosom
	 */
	public void sort(){
		Collections.sort((List<Chromosom>)this.chromoms);
	}
}
