package emergence.agents;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.safety.SafetyAdvance;

/**
 * Agent which returns an action which is very safe.
 */
public class StayAliveAgent extends AbstractPlayer {



	/**
	 * Constructs an StayAliveAgent.
	 * 
	 * @param stateObs
	 * @param elapsedTimer
	 */
	public StayAliveAgent(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {
	}

	/**
	 * returns an action which is save. It will be checked by safetyAdvance
	 */
	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {
		return new SafetyAdvance(10).getOneSafeAction(stateObs);
	}
	

}
