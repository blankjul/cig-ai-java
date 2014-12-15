package emergence.strategy.evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import core.game.StateObservation;
import emergence.util.ActionTimer;
import emergence.util.Helper;

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
	private ArrayList<EvolutionaryNode> population = new ArrayList<>();

	// always the last generation!
	private ArrayList<EvolutionaryNode> lastGeneration = null;

	// number of generations that were applied.
	private int numGeneration = 0;

	// counter for the pool simulation
	private int counter = 0;

	public Evolution(int pathLength, int populationSize, int numFittest, StateObservation stateObs) {
		super();
		this.pathLength = pathLength;
		this.populationSize = populationSize;
		this.numFittest = numFittest;

		// initialize the pool
		for (int i = 0; i < populationSize; i++) {
			population.add(EvolutionaryNode.random(stateObs, pathLength));
		}
	}

	public void expand(StateObservation stateObs, ActionTimer timer) {

		while (timer.isTimeLeft()) {

			// simulate one element
			if (counter < population.size()) {
				EvolutionaryNode evoNode = population.get(counter);
				// simulate always on a copy!
				evoNode.simulate(stateObs.copy());
				++counter;

			} else {
				nextGen();
				counter = 0;
			}
			timer.addIteration();
		}
	}

	public void slidingWindow(StateObservation stateObs) {
		numGeneration = 0;
		lastGeneration = null;

		ArrayList<EvolutionaryNode> poolNew = new ArrayList<EvolutionaryNode>();

		for (int i = 0; i < population.size(); i++) {
			EvolutionaryNode evoNode = population.get(i);
			EvolutionaryNode nodeToAdd = new EvolutionaryNode(stateObs, evoNode.getPath());
			nodeToAdd.removeFirstAction();
			nodeToAdd.setLength(evoNode.getLevel() + 1, stateObs);
			nodeToAdd.setScore(Double.NEGATIVE_INFINITY);
			poolNew.add(nodeToAdd);
		}
		population = poolNew;
	}

	public void nextGen() {
		++numGeneration;
		lastGeneration = population;
		counter = 0;

		ArrayList<EvolutionaryNode> nextPool = new ArrayList<>();

		// save the fittest
		Collections.sort(population);

		for (int i = 0; i < numFittest && i < population.size(); i++) {
			EvolutionaryNode evo = population.get(i);
			nextPool.add(evo);
		}

		// create the next generation
		while (nextPool.size() < populationSize) {

			Random r = new Random();
			EvolutionaryNode selected = Helper.getRandomEntry(nextPool);

			// result that will be returned
			EvolutionaryNode result = null;

			// mutate
			if (r.nextDouble() < MUTATE_PROBABILITY) {
				result = selected.mutate();
				// crossover
			} else {

				// select a second one that is not the first!
				ArrayList<EvolutionaryNode> tmp = new ArrayList<>();
				for (EvolutionaryNode candidate : nextPool) {
					if (candidate != selected)
						tmp.add(candidate);
				}
				EvolutionaryNode second = Helper.getRandomEntry(tmp);

				result = selected.crossover(second);
			}
			nextPool.add(result);
		}

		population = nextPool;
	}

	public void print() {
		print(3);
	}

	public void print(int top) {
		ArrayList<EvolutionaryNode> pool = getPopulation();
		Collections.sort(population);
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

	public void setPathLength(StateObservation stateObs, int pathLength) {
		this.pathLength = pathLength;
		for (int i = 0; i < population.size(); i++) {
			EvolutionaryNode path = population.get(i);
			path.setLength(pathLength, stateObs);
		}
	}

	public int getNumGeneration() {
		return numGeneration;
	}

	public int getPopulationSize() {
		return population.size();
	}

	public void setPopulationSize(StateObservation stateObs, int populationSize) {
		this.populationSize = populationSize;
		Collections.sort(population);

		while (population.size() < populationSize) {
			EvolutionaryNode random = EvolutionaryNode.random(stateObs, populationSize);
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

	public ArrayList<EvolutionaryNode> getPopulation() {
		if (lastGeneration != null) {
			return lastGeneration;
		} else
			return population;
	}

}
