package emergence.strategy;

import core.game.StateObservation;
import emergence.Factory;
import emergence.Simulator;
import emergence.util.ActionTimer;
import ontology.Types.ACTIONS;

public abstract class AStrategy {
	
	// simulator for advancing the actions
	protected Simulator simulator = Factory.getSimulator();
	
	
	abstract public boolean expand(StateObservation stateObs,ActionTimer timer);
	
	
	abstract public ACTIONS act();
	

}
