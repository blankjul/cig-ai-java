package emergence_NI.GeneticStrategy.GeneticOperators;

import java.util.Random;

import emergence_NI.GeneticStrategy.Chromosom.Chromosom;
import emergence_NI.GeneticStrategy.Chromosom.Population;

public class OnePointCrossover extends ACrossover {

	public OnePointCrossover() {

	}

	/**
	 * normal one point crossover
	 */
	public void crossover(Chromosom first, Chromosom second, Random r) {
		int splitpoint = r.nextInt(first.actions.length);
		int tmp;
		for (int i = splitpoint; i < first.actions.length; i++) {
			tmp = first.actions[i];
			first.actions[i] = second.actions[i];
			second.actions[i] = tmp;
		}
	}

	@Override
	public void crossoverCopy(Population pop, Chromosom first, Chromosom second, Random r) {
		
		Chromosom childOne = first.clone();
		Chromosom childTwo = second.clone();
		
		int splitpoint = r.nextInt(childOne.actions.length);
		int tmp;
		for (int i = splitpoint; i < childOne.actions.length; i++) {
			tmp = childOne.actions[i];
			childOne.actions[i] = childTwo.actions[i];
			childTwo.actions[i] = tmp;
		}
		
		pop.chromoms.add(childOne);
		pop.chromoms.add(childTwo);
	}
}
