package emergence_HR.heuristics;

import tools.Vector2d;
import core.game.StateObservation;

/**
 * This a a heuristic that aims to one target. If the avatar is very near to
 * that target we will hopefully get points!
 * 
 */
public class TargetHeuristic extends StateHeuristic {

	public Target target;

	public TargetHeuristic(Target t) {
		this.target = t;
	}

	public String toString() {
		return String.format("%s, %s", getClass().getSimpleName(), target );
	}

	@Override
	protected double getRank(StateObservation stateObs) {
		// get the position of the target
		Vector2d targetPos = target.getPosition(stateObs);

		// get the distance from the avatar to the target!
		double distance = distance(avatarPosition, targetPos);

		if (distance == 0)
			return Double.MAX_VALUE;

		return 100000 / distance;
	}

}
