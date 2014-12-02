package emergence_NI.helper;

import java.awt.Desktop.Action;
import java.util.ArrayList;
import java.util.HashMap;

import ontology.Types;
import core.game.StateObservation;
import emergence_NI.GeneticStrategy.MemoryGenStrategy;

/**
 * idea from sampleGA Controller, maybe faster than the normal ActionMap
 * @author spakken
 *
 */
public class ActionMap2 {
	public static HashMap<Types.ACTIONS, Integer> actionToInteger;
	public static HashMap<Integer, Types.ACTIONS> integerToAction;
	public static int numberOfActions;
	
	public ActionMap2(StateObservation stateObs){
		actionToInteger = new HashMap<Types.ACTIONS, Integer>();
		integerToAction = new HashMap<Integer, Types.ACTIONS>();
		
		ArrayList<Types.ACTIONS> actions = stateObs.getAvailableActions();
		numberOfActions = actions.size();
		
		for(int i = 0; i < numberOfActions; i++){
			actionToInteger.put(actions.get(i), i);
			integerToAction.put(i,  actions.get(i));
		}
	}
	
	public static Types.ACTIONS getAction(int i){
		return integerToAction.get(i);
	}
	
	public static int getInt(Types.ACTIONS action){
		return actionToInteger.get(action);
	}
	
	public void checkValidation(StateObservation stateObs){
		int goalSize = stateObs.getAvailableActions().size();
		
		if(numberOfActions != goalSize){
			MemoryGenStrategy.lastPopValid = false;
			MemoryGenStrategy.lastPopulation.chromoms.clear();
			actionToInteger.clear();
			integerToAction.clear();
			
			ArrayList<Types.ACTIONS> actions = stateObs.getAvailableActions();
			numberOfActions = actions.size();
			
			for(int i = 0; i < numberOfActions; i++){
				actionToInteger.put(actions.get(i), i);
				integerToAction.put(i,  actions.get(i));
			}
		}
	}
}
