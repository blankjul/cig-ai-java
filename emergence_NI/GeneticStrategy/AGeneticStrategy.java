package emergence_NI.GeneticStrategy;

import java.util.Random;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_NI.GeneticStrategy.Chromosom.AChromosomRating;
import emergence_NI.GeneticStrategy.Chromosom.Chromosom;
import emergence_NI.GeneticStrategy.Chromosom.Population;
import emergence_NI.helper.ActionMap2;
import emergence_NI.helper.ActionTimer;

public abstract class AGeneticStrategy {
	
	public StateObservation stateObs;
	
	public GeneticSettings settings;
	
	//index = nummer des chromosoms in der population
	public double[] score;
	
	//vtl in array umwandeln chromosombestehn aus aktionen und dem score
	//public Pair<int[], Double> chromosom;
	
	//int[] = chromosomes, sorted by Double = score
	public Population population;
	
	public Chromosom bestChromosom;
	
	public ActionTimer timer;
	
	// generator for random numbers
	public static Random r = new Random();
	
	//to get fast access to the available actions
	public static ActionMap2 map;
	
	public int generation;
	
	
	public AGeneticStrategy(StateObservation stateObs, GeneticSettings settings, ElapsedCpuTimer elapsedTimer){
		this.stateObs = stateObs;
		this.settings = settings;
		this.population = new Population(settings.populationSize);
		this.timer = new ActionTimer(elapsedTimer);
		this.timer.timeRemainingLimit = 2;
		this.generation = 0;
	}
	
	/**
	 * this method is used to compute the  whole EA
	 * @return
	 */
	public abstract Types.ACTIONS compute();
	
	//the following methods are single parts of the EA algorithm
	
	/**
	 * specifies which candidates get mutated
	 */
	public abstract void selection();
	
	/**
	 * a n-parentoperation
	 */
	public abstract void crossover();
	
	/**
	 * a one-parent operation
	 */
	public abstract void mutate();
	
	/**
	 * generat a new population from the selected/mutated children
	 */
	public abstract void genNewPopulation();
	
	/**
	 * evaluate the new population and sort the population
	 */
	public abstract void evaluation();
	
	
}
