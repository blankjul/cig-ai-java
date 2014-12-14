package emergence.targets;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;

public class MovableTarget extends ATarget{

	private StateObservation stateObs;
	
	public MovableTarget(TYPE type, Integer itype, StateObservation stateObs) {
		super(type, itype);
		this.stateObs = stateObs;
	}
	
	
	@Override
	public Vector2d position() {
		Observation obs = TargetFactory.getObservationFromType(itype, stateObs)._2();
		return obs.position;
	}


}
