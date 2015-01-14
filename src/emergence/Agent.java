package emergence;

import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.agents.MCTSAgent;

/**
 * This is just a wrapper class for for executing antoher controller.
 */
public class Agent extends AbstractPlayer {

	private AbstractPlayer agent;

	public Agent(){};
	
	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		agent = new MCTSAgent(stateObs, elapsedTimer);
	}
	

	@Override
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		return agent.act(stateObs, elapsedTimer);
	}

}
