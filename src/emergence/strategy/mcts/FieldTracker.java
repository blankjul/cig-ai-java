package emergence.strategy.mcts;

import java.util.HashMap;

import ontology.Types.ACTIONS;
import core.game.StateObservation;
import emergence.util.Helper;

/**
 * This class stores how often a field was visited.
 *
 */
public class FieldTracker {

	/** track the visited fields until yet */
	public HashMap<String, Integer> fieldVisits = new HashMap<String, Integer>();

	/** the highest number of field visits */
	public int maxVisitedField = 0;

	/**
	 * Increments the field-visits if a field was visited before. When a field
	 * was not visited before, it is pushed in the hashmap "fieldVisits" with a
	 * visit-number of one.
	 * 
	 * @param stateObs
	 * @param lastAction
	 */
	public void track(StateObservation stateObs, ACTIONS lastAction) {
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

	/**
	 * clears all visited fields and sets the maxvisited field 0.
	 */
	public void reset() {
		maxVisitedField = 0;
		fieldVisits.clear();
	}

}