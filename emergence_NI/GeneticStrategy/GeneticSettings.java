package emergence_NI.GeneticStrategy;

import java.util.ArrayList;

import emergence_NI.GeneticStrategy.GeneticOperators.ACrossover;
import emergence_NI.GeneticStrategy.GeneticOperators.AMutation;
import emergence_NI.GeneticStrategy.GeneticOperators.ASelection;
import emergence_NI.GeneticStrategy.GeneticOperators.NBestSelection;
import emergence_NI.GeneticStrategy.GeneticOperators.OnePointCrossover;
import emergence_NI.GeneticStrategy.GeneticOperators.StandardMutation;

public class GeneticSettings {
	
	public int populationSize;
	
	public int chromosomLength;
	
	public ArrayList<AMutation> mutation;
	
	public ArrayList<ACrossover> crossover;
	
	public ArrayList<ASelection> selection;
	
	public GeneticSettings() {
		mutation = new ArrayList<AMutation>();
		crossover = new ArrayList<ACrossover>();
		selection = new ArrayList<ASelection>();
	}
	
	public void setDefaultSettings(){
		this.populationSize = 10;
		this.chromosomLength = 10;
		//put standard one point mutation in mutation list
		this.mutation.add(new StandardMutation());
		//put the onePoint crossover in the list
		this.crossover.add(new OnePointCrossover());
		//put the n best selection in the list
		this.selection.add(new NBestSelection());
		
	}
}
