package emergence.targets;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;

/**
 * This class represents an dynamic target because the position could change
 * immediately. It's always the nearest fixed target at the agent.
 *
 */
public class DynamicTarget extends ATarget {

	/**
	 * Creates a target (just calls the super constructor).
	 * 
	 * @param type
	 * @param itype
	 */
	public DynamicTarget(TYPE type, Integer itype) {
		super(type, itype);
	}

	/**
	 * Returns the position of the target.
	 */
	@Override
	public Vector2d getPosition(StateObservation stateObs) {
		Observation obs = null;
		obs = TargetFactory.getObservationFromType(type, itype, stateObs);
		if (obs == null)
			return null;
		return obs.position;
	}

}
