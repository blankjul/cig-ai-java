package emergence_HR.target;

import core.game.StateObservation;
import emergence_HR.heuristics.AHeuristic;
import tools.Vector2d;

abstract public class AImmovableTarget extends ATarget{

	Vector2d targetPos;
	double distance = -1;
	
	public AImmovableTarget(Vector2d targetPos) {
		this.targetPos = targetPos;
	}
	
	@Override
	public double distance(StateObservation stateObs) {
		Vector2d avatarPos = stateObs.getAvatarPosition();
		if (distance == -1) distance = AHeuristic.distance(avatarPos, targetPos);
		return distance;
	}



}
