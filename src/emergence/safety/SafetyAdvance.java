package emergence.safety;

import ontology.Types.ACTIONS;
import ontology.Types.WINNER;
import core.game.StateObservation;
import emergence.Factory;

public class SafetyAdvance extends ASafety{
	

	private int numOfIterations;
	
	public SafetyAdvance(int n) {
		this.numOfIterations = n;
	}
	
	@Override
	public boolean isSafe(StateObservation stateObs, ACTIONS a) {
		for (int i = 0; i < numOfIterations; i++) {
			StateObservation tmp = stateObs.copy();
			Factory.getSimulator().advance(tmp, a);
			if (tmp.getGameWinner() == WINNER.PLAYER_LOSES) return false;
		}
		return true;
	}

	

}
