package emergence_HR.heuristics;

import core.game.StateObservation;
import emergence_HR.target.ATarget;

/**
 * This a a heuristic that aims to one target. If the avatar is very near to
 * that target we will hopefully get points!
 * 
 */
public class TargetHeuristic extends AHeuristic {

	public ATarget target;

	public TargetHeuristic(ATarget t) {
		this.target = t;
	}

	@Override
	protected double getRank(StateObservation stateObs) {

		// get the distance from the avatar to the target!
		double distance = target.distance(stateObs);

		if (distance == 0)
			return Double.POSITIVE_INFINITY;

		return 100000 / distance;
	}

	public String toString() {
		return String.format("%s, %s", getClass().getSimpleName(), target);
	}

}
