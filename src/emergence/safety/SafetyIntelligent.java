package emergence.safety;

import ontology.Types.ACTIONS;
import core.game.StateObservation;

/**
 * This Safety combines SafetyAdvance and SafetyGridSearch.
 * 
 * @author spakken
 *
 */
public class SafetyIntelligent extends ASafety {

	/** safety advance object */
	private ASafety advanceSafety;

	/**
	 * Creates an Instance which is defined by a SafetyAdvance object.
	 * 
	 * @param n
	 */
	public SafetyIntelligent(int n) {
		advanceSafety = new SafetyAdvance(n);
	}

	/**
	 * Returns true if the action is safe, false otherwise. The return value is
	 * true when the SafetyGridSearch and the SafetyAdvance are true.
	 */
	@Override
	public boolean isSafe(StateObservation stateObs, ACTIONS a) {
		if (new SafetyGridSearch().isSafe(stateObs, a)) {
			return true;
		} else
			return advanceSafety.isSafe(stateObs, a);
	}

	/**
	 * Generate a String object which is used in csv files.
	 */
	@Override
	public String toCSVString() {
		return "SafetyIntelligent,1";
	}

}
