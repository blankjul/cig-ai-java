package emergence_HR.target;

import java.util.ArrayList;

import core.game.StateObservation;

public class TargetFactory {

	/**
	 * This is just a factory method that returns all the possible targets at
	 * the given state.
	 * 
	 * @param stateObs
	 *            current state
	 * @return list with all possible targets.
	 */
	public static ArrayList<ATarget> getAllTargets(StateObservation stateObs) {
		ArrayList<ATarget> targetList = new ArrayList<ATarget>();
		targetList.addAll(PortalTarget.create(stateObs));
		targetList.addAll(ResourceTarget.create(stateObs));
		targetList.addAll(NPCTarget.create(stateObs));
		return targetList;
	}

}
