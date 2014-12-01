package emergence_NI.helper;

import java.util.ArrayList;

import ontology.Types;
import core.game.StateObservation;

/**
 * The ActionMap is useful to get very fast the integer from a action or the
 * other way a round. it is a singleton class because normally that does not
 * change during one game!
 */
public class ActionMap {

	public static int numActions(StateObservation stateObs) {
		return stateObs.getAvailableActions().size();
	}

	public static int getInt(StateObservation stateObs, Types.ACTIONS a) {
		ArrayList<Types.ACTIONS> actions = stateObs.getAvailableActions();
		for (int i = 0; i < actions.size(); i++) {
			if (actions.get(i).equals(a)) return i;
		}
		return -1;
	}
	
	
	public static Types.ACTIONS getAction(StateObservation stateObs, int i) {
		ArrayList<Types.ACTIONS> actions = stateObs.getAvailableActions();
		if (i >= 0 && i < actions.size()) return actions.get(i);
		return Types.ACTIONS.ACTION_NIL;
	}
	


}
