package emergence_HR.target;

import core.game.StateObservation;
import emergence_HR.heuristics.AHeuristic;
import tools.Vector2d;

abstract public class AImmovableTarget extends ATarget{

	Vector2d targetPos;
	
	public AImmovableTarget(Vector2d targetPos) {
		this.targetPos = targetPos;
	}
	
	@Override
	public double distance(StateObservation stateObs) {
		Vector2d avatarPos = stateObs.getAvatarPosition();
		double dist = AHeuristic.distance(avatarPos, targetPos);
		return dist;
	}

	/**
	 * Print all needed information to specify a target.
	 */
	@Override
	public String toString() {
		return super.toString() + String.format(" [%s,%s]", targetPos.x, targetPos.y);
	}


}
