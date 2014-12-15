package emergence.targets;

import core.game.StateObservation;
import tools.Vector2d;

public class ImmovableTarget extends ATarget{

	Vector2d targetPos;
	
	public ImmovableTarget(TYPE type, Integer itype, Vector2d targetPos) {
		super(type, itype);
		this.targetPos = targetPos;
	}
	
	@Override
	public Vector2d getPosition(StateObservation stateObs) {
		return targetPos;
	}



}
