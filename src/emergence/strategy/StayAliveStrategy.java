package emergence.strategy;

import ontology.Types.ACTIONS;
import core.game.StateObservation;
import emergence.safety.SafetyIntelligent;
import emergence.util.ActionTimer;

public class StayAliveStrategy extends AStrategy {

	private int n;
	private StateObservation stateObs;
	
	public StayAliveStrategy(StateObservation stateObs, int n) {
		this.n = n;
		this.stateObs = stateObs;
	}

	@Override
	public boolean expand(StateObservation stateObs,ActionTimer timer) {
		return true;
	}

	@Override
	public ACTIONS act() {
		return new SafetyIntelligent(n).getOneSafeAction(stateObs);
	}
	
	
	
}
