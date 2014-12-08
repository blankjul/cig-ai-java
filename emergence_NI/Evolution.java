package emergence_NI;

import java.util.ArrayList;
import java.util.Random;

import core.game.StateObservation;
import emergence_NI.helper.Helper;

public class Evolution {

	// probability of a mutation
	final private double MUTATE_PROBABILITY = 0.7;

	// number of actions that are simulated
	private int pathLength;

	// how many entries should the population has
	private int populationSize;

	// number of the fittest to save for the next generation
	private int numFittest;

	// the current population
	private ArrayList<Evolutionary<Path>> population = new ArrayList<>();
	
	// always the last generation!
	private ArrayList<Evolutionary<Path>> lastGeneration = null;

	// number of generations that were applied.
	private int numGeneration = 0;

	// counter for the pool simulation
	private int counter = 0;

	// comparator for the ranking
	private PathComparator comp = new PathComparator();


	public Evolution(int pathLength, int populationSize, int numFittest,
			StateObservation stateObs) {
		super();
		this.pathLength = pathLength;
		this.populationSize = populationSize;
		this.numFittest = numFittest;

		// initialize the pool
		Path generator = new Path(pathLength, stateObs.getAvailableActions());
		for (int i = 0; i < populationSize; i++) {
			Path p = generator.random();
			population.add(p);
		}
	}



	public void expand(StateObservation stateObs) {

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
		lastGeneration = null;
		
		ArrayList<Evolutionary<Path>> poolNew = new ArrayList<Evolutionary<Path>>();
		
		for (int i = 0; i < population.size(); i++) {
			Path path = (Path) population.get(i);
			Path pathToAdd = new Path(path.getPathLength(), path.getActions());
			pathToAdd.removeFirstAction();
			pathToAdd.setPathLength(path.getPathLength());
			pathToAdd.resetScore();
			poolNew.add(pathToAdd);
		}
		
		population = poolNew;
		
		
	}
	

	public void nextGen() {
		++numGeneration;
		lastGeneration = population;
		counter = 0;

		ArrayList<Evolutionary<Path>> nextPool = new ArrayList<>();

		// save the fittest
		PathComparator.sort(population, comp);
		for (int i = 0; i < numFittest && i < population.size(); i++) {
			Evolutionary<Path> evo = population.get(i);
			nextPool.add(evo);
		}

		// create the next generation
		while (nextPool.size() < populationSize) {

			Random r = new Random();
			Evolutionary<Path> selected = Helper.getRandomEntry(nextPool,
					Agent.r);

			// result that will be returned
			Evolutionary<Path> result = null;

			// mutate
			if (r.nextDouble() < MUTATE_PROBABILITY) {
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
		ArrayList<Evolutionary<Path>> pool = getPopulation();
		PathComparator.sort(pool, comp);
		System.out.println("------------------");
		System.out.println("GENERATION: " + numGeneration);
		System.out.println("------------------");
		for (int i = 0; i < pool.size() && i < top; i++) {
			if (i == 0)
				System.out.print("--> ");
			System.out.println(pool.get(i));
		}
	}

	public int getPathLength() {
		return pathLength;
	}

	public void setPathLength(int pathLength) {
		this.pathLength = pathLength;
		for (int i = 0; i < population.size(); i++) {
			Path path = (Path) population.get(i);
			path.setPathLength(pathLength);
		}
	}

	public int getNumGeneration() {
		return numGeneration;
	}

	public int getPopulationSize() {
		return population.size();
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
		PathComparator.sort(population, comp);
		
		while (population.size() < populationSize) {
			Path random = population.get(0).random();
			population.add(random);
		}
		while (population.size() > populationSize) {
			population.remove(population.size() - 1);
		}

	}

	public int getNumFittest() {
		return numFittest;
	}

	public void setNumFittest(int numFittest) {
		this.numFittest = numFittest;
	}

	public PathComparator getComparator() {
		return comp;
	}

	public ArrayList<Evolutionary<Path>> getPopulation() {
		if (lastGeneration != null) {
			return lastGeneration;
		}
		else return population;
	}

}
