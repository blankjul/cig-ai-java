package emergence;

import java.util.HashMap;

import ontology.Types.ACTIONS;
import core.game.StateObservation;
import emergence.util.Helper;

public class FieldTracker {

	// track the visited fields until yet!
	public HashMap<String, Integer> fieldVisits = new HashMap<String, Integer>();

	public int maxVisitedField = 0;

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

	public void reset() {
		maxVisitedField = 0;
		fieldVisits.clear();
	}

}