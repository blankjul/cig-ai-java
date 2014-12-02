package emergence_NI.GeneticStrategy;

import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_NI.GeneticStrategy.Chromosom.AChromosomRating;
import emergence_NI.GeneticStrategy.Chromosom.Chromosom;
import emergence_NI.GeneticStrategy.Chromosom.Population;
import emergence_NI.GeneticStrategy.GeneticOperators.ACrossover;
import emergence_NI.GeneticStrategy.GeneticOperators.AMutation;
import emergence_NI.GeneticStrategy.GeneticOperators.ASelection;
import emergence_NI.helper.ActionMap2;
import emergence_NI.helper.Helper;

public class MemoryGenStrategy extends AGeneticStrategy {

	public Population selectedPop;

	public int numberOfSelectedChildren = 5;

	public int numberOfCrossovers = 2;

	public int numberOfMutations = 4;

	public ASelection selection;

	public ACrossover crossover;

	public AMutation mutation;

	public AChromosomRating chromosomRating;

	public static Population lastPopulation = new Population();

	public static boolean lastPopValid = false;

	public MemoryGenStrategy(StateObservation stateObs,
			GeneticSettings settings, ElapsedCpuTimer elapsedTimer) {
		super(stateObs, settings, elapsedTimer);
		this.selectedPop = new Population(this.settings.populationSize);

		this.selection = this.settings.selection.get(0);
		this.crossover = this.settings.crossover.get(0);
		this.mutation = this.settings.mutation.get(0);
		this.chromosomRating = this.settings.chrRating.get(0);
	}

	public void createRandomPopulation() {
		int i = this.settings.populationSize;
		while (i > 0) {
			// generate array to store the Actions
			int[] tempActions = new int[this.settings.chromosomLength];
			// fill one chromosom
			for (int e = 0; e < this.settings.chromosomLength; e++) {
				tempActions[e] = this.r.nextInt(ActionMap2.numberOfActions);
			}
			// create Chromosom
			Chromosom temp = new Chromosom(0, tempActions);
			
			// add chromosom to the population
			this.population.chromoms.add(temp);

			i--;
		}
		
		//rank the if engough time is left
		for(Chromosom chr : population.chromoms){
			if(timer.getRemaining() < 4.0){ System.out.println("break!!!!!!!!!");break;}
			this.chromosomRating.rateChromosom(this.stateObs, chr);
		}
		
		// sort population
		population.sort();

		// store best Chromosom
		bestChromosom = population.chromoms.get(0);
	}

	public void setLastPopAsActual() {
		// correct the lastPop
		for (Chromosom chr : lastPopulation.chromoms) {
			int[] temp = new int[this.settings.chromosomLength];
			temp[this.settings.chromosomLength - 1] = this.r
					.nextInt(ActionMap2.numberOfActions);
			System.arraycopy(chr.actions, 1, temp, 0,
					this.settings.chromosomLength - 1);
			chr.actions = temp;
		}

		// set the last pop as new pop
		this.population.chromoms.addAll(lastPopulation.chromoms);

		// rate the new pop very expensive
		for (Chromosom chr : population.chromoms) {
			if(this.timer.getRemaining() < 4.0){ System.out.println("break, lastpop!!!!");break;}
			this.chromosomRating.rateChromosom(this.stateObs, chr);
		}

		// sort population
		population.sort();

		// store best Chromosom
		bestChromosom = population.chromoms.get(0);
	}


	/**
	 * used as a start point of the algorithm
	 */
	@Override
	public ACTIONS compute() {
		System.out.println("compute start: " + timer.getRemaining());
		// check if a last pop was created
		if (lastPopValid && lastPopulation != null) {
			setLastPopAsActual();
		} else {
			createRandomPopulation();
		}
		
		System.out.println("compute before loop: " + timer.getRemaining());
		// the tournament is computed in this loop
		boolean f = true;
		boolean wasinloop = false;
		while (this.timer.isTimeLeft()) {
			wasinloop = true;
			this.selection();
			this.crossover();
			this.mutate();
			this.genNewPopulation();
			this.evaluation();
			generation++;
			timer.addIteration();
			// printEvoStep();
		}

		this.saveAsLastPop();
		 //System.out.println("in loop geweseon:" + wasinloop + "loops:" +
		 //generation);
		// actual population -> best chromosom -> first Action
		return ActionMap2.getAction(bestChromosom.actions[0]);
	}

	@Override
	public void selection() {
		ASelection selection = Helper.getRandomEntry(this.settings.selection,
				this.r);
		selection.select(population, numberOfSelectedChildren,
				this.settings.chromosomLength, r);
	}

	@Override
	public void crossover() {
		ACrossover crossover = Helper.getRandomEntry(this.settings.crossover,
				this.r);

		// nochmal drueber nachdenken, wie das mitden crossover sinnnvoll ist
		for (int i = 0; i < numberOfCrossovers; i++) {
			crossover.crossoverCopy(population,
					Helper.getRandomEntry(population.chromoms, r),
					Helper.getRandomEntry(population.chromoms, r), r);
		}
	}

	@Override
	public void mutate() {
		AMutation mutation = Helper.getRandomEntry(this.settings.mutation,
				this.r);

		// maby check that the good ones don't be destroyed
		for (int i = 0; i < numberOfMutations; i++) {
			mutation.mutateCopy(population,
					Helper.getRandomEntry(population.chromoms, r), r);
		}
	}

	@Override
	public void genNewPopulation() {
		// has no own class like selectio/mutation/crossover
		// mayby add one

		int actualSize = population.chromoms.size();

		// debug
		if (actualSize < this.settings.populationSize) {
			System.out.println("gewNewPop fehler");
		}

		// rate new Chromosoms
		for (Chromosom chr : population.chromoms) {
			if (!chr.rated) {
				this.chromosomRating.rateChromosom(this.stateObs, chr);
			}
		}
		// check which of them is working
		// population.chromoms.sort(null);
		population.sort();

		// cut of the last elements
		for (int i = 0; i < actualSize - this.settings.populationSize; i++) {
			population.chromoms.remove(this.settings.populationSize);
		}
	}

	@Override
	public void evaluation() {
		// add the score of all chromosoms
		double score = 0;
		for (Chromosom chr : population.chromoms) {
			score += chr.score;
		}
		population.score = score;

		// if the chromosom is better than the best one, set it
		if (population.chromoms.get(0).score > this.bestChromosom.score) {
			this.bestChromosom = population.chromoms.get(0).clone();
		}
	}

	public void saveAsLastPop() {
		lastPopulation.chromoms.clear();
		lastPopulation.chromoms.addAll(this.population.chromoms);
		lastPopValid = true;
	}

	public void printEvoStep() {
		System.out.println("-------------------------------------");
		System.out.println("Generation: " + generation + "   Score: "
				+ population.score);

		for (int i = 0; i < population.chromoms.size(); i++) {
			System.out.println("Chr " + i + "  "
					+ population.chromoms.get(i).score);
		}
		System.out.println("overall best: " + bestChromosom.score + "action: "
				+ bestChromosom.actions[0]);
	}

}
