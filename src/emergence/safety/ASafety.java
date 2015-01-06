package emergence.safety;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ontology.Types.ACTIONS;
import core.game.StateObservation;

public abstract class ASafety {
	
	
	public abstract boolean isSafe(StateObservation stateObs, ACTIONS a);
	
	
	public Set<ACTIONS> getSafeActions(StateObservation stateObs) {
		return getSafeActions(stateObs, stateObs.getAvailableActions() );
	}
	
	
	public Set<ACTIONS> getSafeActions(StateObservation stateObs, Collection<ACTIONS> actions) {
		Set<ACTIONS> result = new HashSet<>();
		// for all possible moves
		for (ACTIONS a : actions) {
			if (isSafe(stateObs, a))
				result.add(a);
		}
		return result;
	}
	
	public ACTIONS getOneSafeAction(StateObservation stateObs) {
		return getOneSafeAction(stateObs, stateObs.getAvailableActions());
	}
	
	
	public ACTIONS getOneSafeAction(StateObservation stateObs, Collection<ACTIONS> actions ) {
		ArrayList<ACTIONS> list = new ArrayList<>(actions);
		Collections.shuffle(list);
		for (ACTIONS a : list) {
			if (isSafe(stateObs, a)) return a;
		}
		System.out.println("[" + stateObs.getGameTick() + "] NO SAFE ACTION FOUND!");
		return ACTIONS.ACTION_NIL;
	}
	
	public abstract String toCSVString();
}
