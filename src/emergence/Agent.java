package emergence;

import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.agents.EvolutionaryAgent;
import emergence.agents.MCTSAgent;
import emergence.util.pair.Pair;

public class Agent extends AbstractPlayer {

	private AbstractPlayer agent;

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		Pair<String,Integer> pair = Factory.getGameDetection().detect(stateObs);
		System.out.println(pair);
		
		String game = pair._1();
		//int level = pair._2();
		
		if (game.equals("aliens")  || game.equals("zelda")) {
			agent = new MCTSAgent(stateObs, elapsedTimer);
		} else if (game.equals("boulderdash") || game.equals("sokoban") || game.equals("survivezombies")) {
			agent = new EvolutionaryAgent(stateObs, elapsedTimer);
		} else {
			agent = new AgentPrimary(stateObs, elapsedTimer);
		}
	}
	
	

	public Agent() {
	}



	@Override
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		return agent.act(stateObs, elapsedTimer);
	}

}
