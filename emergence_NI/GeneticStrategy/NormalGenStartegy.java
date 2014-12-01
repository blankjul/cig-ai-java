package emergence_NI.GeneticStrategy;

import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import emergence_NI.GeneticStrategy.Chromosom.AChromosomRating;
import emergence_NI.GeneticStrategy.Chromosom.Chromosom;
import emergence_NI.GeneticStrategy.Chromosom.Population;
import emergence_NI.GeneticStrategy.GeneticOperators.ACrossover;
import emergence_NI.GeneticStrategy.GeneticOperators.AMutation;
import emergence_NI.GeneticStrategy.GeneticOperators.ASelection;
import emergence_NI.helper.ActionMap2;
import emergence_NI.helper.Helper;

public class NormalGenStartegy extends AGeneticStrategy{

	public Population selectedPop;
	
	public int numberOfSelectedChildren = 5;
	
	public int numberOfCrossovers = 2;
	
	public int numberOfMutations = 4;
	
	public NormalGenStartegy(GeneticSettings settings, ElapsedCpuTimer elapsedTimer,
			AChromosomRating rating) {
		super(settings, elapsedTimer, rating);
		this.selectedPop = new Population(this.settings.populationSize);
	}


	public void createRandomPopulation(){
		int i = this.settings.populationSize;
		while(i > 0){
			//generate array to store the Actions
			int[] tempActions = new int[this.settings.chromosomLength];
			//fill one chromosom
			for(int e = 0; e < this.settings.chromosomLength; e++){
				tempActions[e] = this.r.nextInt(ActionMap2.numberOfActions);
			}
			//create Chromosom and rank it
			Chromosom temp = new Chromosom(0, tempActions);
			this.chromosomRating.rateChromosom(temp);
			//add chromosom to the population
			this.population.chromoms.add(new Chromosom(0, tempActions));
			
			i--;
		}
	}

	/**
	 * used as a start point of the algorithm
	 */
	@Override
	public ACTIONS compute() {
		
		createRandomPopulation();
		
		//the tournament is computed in this loop
		while(timer.isTimeLeft()){
			selection();
			crossover();
			mutate();
			genNewPopulation();
			evaluation();
		}
		
		//actual population -> best chromosom -> first Action
		return ActionMap2.getAction(population.chromoms.get(0).actions[0]);
	}


	@Override
	public void selection() {
		ASelection selection = Helper.getRandomEntry(this.settings.selection, this.r);
		selection.select(population, numberOfSelectedChildren, this.settings.chromosomLength, r);
	}


	@Override
	public void crossover() {
		ACrossover crossover = Helper.getRandomEntry(this.settings.crossover, this.r);
		
		//nochmal dr�ber nachdenken, wie das mitden crossover sinnnvoll ist
		for(int i = 0; i < numberOfCrossovers; i++){
			crossover.crossoverCopy(population, Helper.getRandomEntry(population.chromoms, r),
					Helper.getRandomEntry(population.chromoms, r), r);
		}
	}


	@Override
	public void mutate() {
		AMutation mutation = Helper.getRandomEntry(this.settings.mutation, this.r);
		
		//maby check that the good ones don't be destroyed
		for(int i = 0; i < numberOfMutations; i++){
			mutation.mutateCopy(population, Helper.getRandomEntry(population.chromoms, r), r);
		}
	}


	@Override
	public void genNewPopulation() {
		//has no own class like selectio/mutation/crossover
		//mayby add one
		
		//debug
		if(population.chromoms.size() < this.settings.populationSize){
			System.out.println("gewNewPop fehler");
		}
		
		//check which of them is working
		//population.chromoms.sort(null);
		population.sort();
		
		population.chromoms.
	}


	@Override
	public void evaluation() {
		// TODO Auto-generated method stub
		
	}

}
