package emergence.strategy;

import java.util.Set;

import ontology.Types.ACTIONS;
import core.game.StateObservation;
import emergence.util.Helper;

public class StayAlive extends AStrategy {

	public StayAlive(StateObservation stateObs) {
		super(stateObs);
	}

	@Override
	public boolean expand() {
		return false;
	}

	@Override
	public ACTIONS act() {
		Set<ACTIONS> actions = simulator.getSafeActions(stateObs);
		if (!actions.isEmpty()) {
			return Helper.getRandomEntry(actions);
		}
		actions = simulator.getSafeActionAdvance(stateObs, 6);
		//System.out.println("NOCHANCE");
		return Helper.getRandomAction(stateObs);

		// actions = simulator.getSafeActionAdvance(stateObs, 5);
		// return Helper.getRandomEntry(actions);
	}

}
