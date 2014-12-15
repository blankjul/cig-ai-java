package emergence.strategy.mcts;

import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.util.ActionTimer;

public class Agent extends AbstractPlayer {

	@Override
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	
	public static final boolean VERBOSE = false;
	
	private MCTStrategy strategy = null;
	
	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		strategy = new MCTStrategy(stateObs);
		strategy.tree = new Tree(new MCTSNode());
		ActionTimer timer = new ActionTimer(elapsedTimer);
		while (timer.isTimeLeft()) {
			strategy.expand();
			timer.addIteration();
		}
	}

	
	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		// track all the fields that were visited
		FieldTracker.track(stateObs);
		
		// set the current state observation of the last root
		strategy.stateObsOfRoot = stateObs;
		
		// get the next best action that will be executed
		ActionTimer timer = new ActionTimer(elapsedTimer);
		while (timer.isTimeLeft()) {
			strategy.expand();
			timer.addIteration();
		}
		
		MCTSNode bestNode = strategy.act();
		Types.ACTIONS a = (bestNode != null) ? bestNode.lastAction : Types.ACTIONS.ACTION_NIL;
		if (bestNode != null) strategy.rollingHorizon(bestNode);
		else strategy.rollingHorizon(new MCTSNode());
		
		
		FieldTracker.lastAction = a;
		
		if (VERBOSE) System.out.println(strategy);
		

		return a;
	}


*/

}
