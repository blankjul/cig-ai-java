package emergence.strategy.mcts;


import java.util.HashMap;

import ontology.Types;
import core.game.StateObservation;
import emergence.util.Helper;

public class FieldTracker {
	
	// track the visited fields until yet!
	public static HashMap<String, Integer> fieldVisits = new HashMap<String, Integer>();
	public static int maxVisitedField = 0;
	public static Types.ACTIONS lastAction = Types.ACTIONS.ACTION_NIL;
	
	
	public static void track(StateObservation stateObs) {
		// track the statistic of each field!
		String fieldHash = Helper.hash(stateObs, lastAction);
		boolean visited = fieldVisits.containsKey(fieldHash);
		if (visited) {
			int value = fieldVisits.get(fieldHash) + 1;
			if (value > maxVisitedField)
				value = maxVisitedField;
			fieldVisits.put(fieldHash, value);
		} else {
			if (1 > maxVisitedField)
				maxVisitedField = 1;
			fieldVisits.put(fieldHash, 1);
		}
	}
	
	public static void reset() {
		maxVisitedField = 0;
		fieldVisits.clear();
		lastAction = Types.ACTIONS.ACTION_NIL;
	}
	
	

}