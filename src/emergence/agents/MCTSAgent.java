package emergence.agents;

import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.Factory;
import emergence.strategy.mcts.MCTSNode;
import emergence.strategy.mcts.MCTStrategy;
import emergence.util.ActionTimer;
import emergence.util.MapInfo;

public class MCTSAgent extends AbstractPlayer {


	public static final boolean VERBOSE = false;
	
	public boolean rollingHorizon = true;
	
	private MCTStrategy strategy = null;
	
	private ACTIONS lastAction = ACTIONS.ACTION_NIL;
	
	
	public MCTSAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		System.out.println("MCTS");
		strategy = new MCTStrategy(new MCTSNode(null));
		strategy.root = new MCTSNode(null);
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 100;
		strategy.expand(stateObs, timer);
	}
	
	
	public ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		// track the last action
		Factory.getFieldTracker().track(stateObs, lastAction);
		
		// create a timer
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 3;
		
		if (stateObs.getGameTick() != 0) {
			if (rollingHorizon) {
				strategy.rollingHorizon(lastAction);
			}else {
				strategy = new MCTStrategy(new MCTSNode(null));
			}
		}
		
		strategy.expand(stateObs, timer);
		ACTIONS a = strategy.act();
		lastAction = a;
		
		if (VERBOSE) System.out.println(strategy);
		
		return a;
	}




}
