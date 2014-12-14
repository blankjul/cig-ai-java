package emergence.strategy;

import ontology.Types.ACTIONS;
import core.game.StateObservation;
import emergence.safety.SafetyIntelligent;

public class StayAliveStrategy extends AStrategy {

	private int n;
	
	public StayAliveStrategy(StateObservation stateObs, int n) {
		super(stateObs);
		this.n = n;
	}

	@Override
	public void expand() {
		return;
	}

	@Override
	public ACTIONS act() {
		return new SafetyIntelligent(n).getOneSafeAction(stateObs);
	}
	
	
	
}
