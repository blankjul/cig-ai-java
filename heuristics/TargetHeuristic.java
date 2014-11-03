package emergence_HR.heuristics;

import tools.Vector2d;
import core.game.StateObservation;
import emergence_HR.target.Target;

/**
 * This a a heuristic that aims to one target. If the avatar is very near to
 * that target we will hopefully get points!
 * 
 */
public class TargetHeuristic extends AHeuristic {

	public Target target;

	public TargetHeuristic(Target t) {
		this.target = t;
	}

	
	@Override
	protected double getRank(StateObservation stateObs) {
		// get the position of the target
		Vector2d targetPos = target.getPosition(stateObs);

		// get the distance from the avatar to the target!
		double distance = distance(avatarPosition, targetPos);

		if (distance == 0)
			return Double.POSITIVE_INFINITY;

		return 100000 / distance;
	}
	
	public String toString() {
		return String.format("%s, %s", getClass().getSimpleName(), target);
	}


}
