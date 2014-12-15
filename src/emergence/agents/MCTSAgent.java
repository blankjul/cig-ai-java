package emergence.agents;

import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;

public class MCTSAgent extends AbstractPlayer {

	@Override
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		// TODO Auto-generated method stub
		return null;
	}

/*
	public static final boolean VERBOSE = true;
	
	private MCTStrategy strategy = null;
	

	
	public MCTSAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		strategy = new MCTStrategy();
		strategy.root = new MCTSNode(null, ACTIONS.ACTION_NIL);
		ActionTimer timer = new ActionTimer(elapsedTimer);
		strategy.expand(stateObs, timer);
	}
	

	
	public ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		// set the current state observation of the last root
		strategy.root = new MCTSNode(null, ACTIONS.ACTION_NIL);
		
		// get the next best action that will be executed
		ActionTimer timer = new ActionTimer(elapsedTimer);
		strategy.expand(stateObs, timer);
		
		
		ACTIONS a = strategy.act();
		
		strategy.rollingHorizon();
		
		FieldTracker.lastAction = a;
		if (VERBOSE) System.out.println(strategy);

		return a;
	}

*/


}
