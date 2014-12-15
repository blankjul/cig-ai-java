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

	public DynamicTarget(TYPE type, Integer itype) {
		super(type, itype);
	}

	@Override
	public Vector2d getPosition(StateObservation stateObs) {
		Observation obs = null;
		obs = TargetFactory.getObservationFromType(type, itype, stateObs);
		if (obs == null)
			return null;
		return obs.position;
	}

}
