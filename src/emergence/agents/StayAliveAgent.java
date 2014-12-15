package emergence.agents;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.safety.SafetyAdvance;

public class StayAliveAgent extends AbstractPlayer {


	public StayAliveAgent() {
	}

	public StayAliveAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
	}

	public Types.ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		return new SafetyAdvance(10).getOneSafeAction(stateObs);
	}



}
