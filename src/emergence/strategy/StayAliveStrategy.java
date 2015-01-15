package emergence.strategy;

import ontology.Types.ACTIONS;
import core.game.StateObservation;
import emergence.safety.SafetyIntelligent;
import emergence.util.ActionTimer;

/**
 * This agent uses SafetyIntelligent to choose the next save action.
 * 
 *
 */
public class StayAliveStrategy extends AStrategy {

	/** number of iterations for the Safety method */
	private int n;

	/** actual state observation */
	private StateObservation stateObs;

	/**
	 * Creates a StayAliveStrategy.
	 * 
	 * @param stateObs
	 * @param n
	 */
	public StayAliveStrategy(StateObservation stateObs, int n) {
		this.n = n;
		this.stateObs = stateObs;
	}

	/**
	 * Returns true. Must be implemented because the class inherits from
	 * AStrategy.
	 */
	@Override
	public boolean expand(StateObservation stateObs, ActionTimer timer) {
		return true;
	}

	/**
	 * Execute the next safe action.
	 */
	@Override
	public ACTIONS act() {
		return new SafetyIntelligent(n).getOneSafeAction(stateObs);
	}

}
