package emergence.strategy;

import core.game.StateObservation;
import emergence.Factory;
import emergence.Simulator;
import emergence.util.ActionTimer;
import ontology.Types.ACTIONS;

public abstract class AStrategy {
	
	// simulator for advancing the actions
	protected Simulator simulator = Factory.getSimulator();
	
	// state observation that is the starting point
	protected StateObservation stateObs;
	
	// action timer to perform
	protected ActionTimer timer;
	
	
	protected AStrategy(StateObservation stateObs) {
		this.stateObs = stateObs;
	}
	
	protected AStrategy(StateObservation stateObs, ActionTimer timer) {
		this(stateObs);
		this.timer = timer;
	}

	abstract public void expand();
	
	abstract public ACTIONS act();

	public StateObservation getStateObs() {
		return stateObs;
	}

	public void setStateObs(StateObservation stateObs) {
		this.stateObs = stateObs;
	}

	public ActionTimer getTimer() {
		return timer;
	}

	public void setTimer(ActionTimer timer) {
		this.timer = timer;
	}
	

	public void reset(StateObservation stateObs, ActionTimer timer) {
		this.timer = timer;
		this.stateObs = stateObs;
	}
	
	

}
