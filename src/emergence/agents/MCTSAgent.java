package emergence.agents;

import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.strategy.mcts.FieldTracker;
import emergence.strategy.mcts.MCTSNode;
import emergence.strategy.mcts.MCTStrategy;
import emergence.util.ActionTimer;

public class MCTSAgent extends AbstractPlayer {


	public static final boolean VERBOSE = false;
	
	private MCTStrategy strategy = null;
	
	
	public MCTSAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		strategy = new MCTStrategy();
		strategy.root = new MCTSNode(null);
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 100;
		strategy.expand(stateObs, timer);
	}
	

	
	public ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 3;
		strategy.expand(stateObs, timer);
		
		ACTIONS a = strategy.act();
		
		strategy.rollingHorizon();
		
		FieldTracker.lastAction = a;
		
		if (VERBOSE) System.out.println(strategy);

		return a;
	}




}
