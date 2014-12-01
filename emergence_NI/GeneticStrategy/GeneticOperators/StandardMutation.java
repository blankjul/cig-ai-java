package emergence_NI.GeneticStrategy.GeneticOperators;

import java.util.Random;

import emergence_NI.GeneticStrategy.Chromosom.Chromosom;
import emergence_NI.GeneticStrategy.Chromosom.Population;
import emergence_NI.helper.ActionMap2;

/**
 * standard mutation, just generate one new action
 * @author spakken
 *
 */
public class StandardMutation extends AMutation{
	
	public void mutate(Chromosom chr, Random r){
		chr.actions[r.nextInt(chr.actions.length)] = r.nextInt(ActionMap2.numberOfActions);
	}

	@Override
	public void mutateCopy(Population pop, Chromosom chr, Random r) {
		Chromosom newChr = chr.clone();
		newChr.actions[r.nextInt(newChr.actions.length)] = r.nextInt(ActionMap2.numberOfActions);
		pop.chromoms.add(newChr);
	}
}
