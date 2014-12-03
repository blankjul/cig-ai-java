package emergence_NI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import core.game.StateObservation;
import emergence_NI.helper.Helper;

public class Evolution {


	// number of actions that are simulated
	public int pathLength = 6;
	
	// number of the fittest to save for the next generation
	public int numFittest = 3;
	
	// how many entries should the population has
	public int populationSize = 6;
	
	// probability of a mutation
	public double mutateProbability = 0.7;
	
	// the current population
	protected ArrayList<Evolutionary<Path>> population = new ArrayList<>();
	
	// number of generations that were applied.
	private int numGeneration = 0;
	
	// counter for the pool simulation
	private int counter = 0;
	
	// comparator for the ranking
	public PathComparator comp = new PathComparator();
	
	
	
	public Evolution() {
	}

	
	/**
	 * @return current best element of population
	 */
	public Path best() {
		if (population.isEmpty()) return null;
		Collections.sort(population, comp);
		return (Path) population.get(0);
	}



	public void expand(StateObservation stateObs) {
		
		// initialize the pool
		if (population.isEmpty()) {
			Path generator = new Path(pathLength, stateObs.getAvailableActions());
			for (int i = 0; i < populationSize; i++) {
				Path p = generator.random();
				population.add(p);
			}
		}

		// simulate one element
		if (counter < population.size()) {
			Path p = (Path) population.get(counter);
			// simulate always on a copy!
			p.simulate(stateObs.copy());
			++counter;
		} else {
			nextGen();
			counter = 0;
		}

	}
	
	public void slidingWindow(StateObservation stateObs) {
		numGeneration = 0;
		for (int i = 0; i < population.size(); i++) {
			Path evo = (Path) population.get(i);
			evo.list.remove(0);
			evo.list.add(Helper.getRandomEntry(stateObs.getAvailableActions(), Agent.r));
			evo.reset();
		}
	}
	

	
	public void nextGen() {
		++numGeneration;
		counter = 0;
		
		ArrayList<Evolutionary<Path>> nextPool = new ArrayList<>();
		
		// save the fittest
		Collections.sort(population, comp);
		for (int i = 0; i < numFittest && i < population.size(); i++) {
			Evolutionary<Path> evo = population.get(i);
			nextPool.add(evo);
		}

		
		// create the next generation
		while (nextPool.size() < populationSize) {

			Random r = new Random();
			Evolutionary<Path> selected = Helper.getRandomEntry(nextPool, Agent.r);

			// result that will be returned
			Evolutionary<Path> result = null;

			// mutate
			if (r.nextDouble() < mutateProbability) {
				result = selected.mutate();
				// crossover
			} else {

				// select a second one that is not the first!
				ArrayList<Evolutionary<Path>> tmp = new ArrayList<>();
				for (Evolutionary<Path> candidate : nextPool) {
					if (candidate != selected)
						tmp.add(candidate);
				}
				Evolutionary<Path> second = Helper.getRandomEntry(tmp, Agent.r);
				
			
				
				result = selected.crossover((Path) second);
			}
			nextPool.add(result);
		}

		population = nextPool;
	}



	public void print() {
		print(3);
	}
	
	public void print(int top) {
		Collections.sort(population, comp);
		System.out.println("------------------");
		System.out.println("GENERATION: " + numGeneration);
		System.out.println("------------------");
		for (int i = 0; i < population.size() && i < top; i++) {
			if (i == 0) System.out.print("--> ");
			System.out.println(population.get(i));
		}
	}

	
	

}
