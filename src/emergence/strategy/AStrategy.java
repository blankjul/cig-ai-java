package emergence.strategy;

import core.game.StateObservation;
import emergence.Factory;
import emergence.Simulator;
import emergence.util.ActionTimer;
import ontology.Types.ACTIONS;

/**
 * A strategy expands a state observation (node) and/or builds a tree and
 * traverses trough this tree.
 * 
 * @author freddy
 *
 */
public abstract class AStrategy {

	/** simulator for advancing the actions */
	protected Simulator simulator = Factory.getSimulator();

	/**
	 * Expands the node by calling the advance method depending on the strategy.
	 * 
	 * @param stateObs
	 * @param timer
	 * @return
	 */
	abstract public boolean expand(StateObservation stateObs, ActionTimer timer);

	/**
	 * Returns the actual best action. It is usually called when no more time
	 * for a advance step is available.
	 * 
	 * @return
	 */
	abstract public ACTIONS act();

}
