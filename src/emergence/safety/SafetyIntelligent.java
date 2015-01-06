package emergence.safety;

import ontology.Types.ACTIONS;
import core.game.StateObservation;

public class SafetyIntelligent extends ASafety{
	
	// safety advance object
	private ASafety advanceSafety;
	
	public SafetyIntelligent(int n) {
		advanceSafety = new SafetyAdvance(n);
	}
	
	@Override
	public boolean isSafe(StateObservation stateObs, ACTIONS a) {
		if (new SafetyGridSearch().isSafe(stateObs, a)) {
			return true;
		} else return advanceSafety.isSafe(stateObs, a);
	}

	@Override
	public String toCSVString() {
		return "SafetyIntelligent,1";
	}


}
