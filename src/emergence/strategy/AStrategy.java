package emergence.strategy;

import core.game.StateObservation;
import emergence.Factory;
import emergence.Simulator;
import ontology.Types.ACTIONS;

public abstract class AStrategy {
	
	protected Simulator simulator = Factory.getSimulator();
	
	protected StateObservation stateObs;
	
	protected AStrategy( StateObservation stateObs) {
		this.stateObs = stateObs;
	}

	abstract public boolean expand();
	
	abstract public ACTIONS act();
	
	

}
