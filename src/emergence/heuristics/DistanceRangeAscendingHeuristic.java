package emergence.heuristics;

import java.awt.Dimension;

import tools.Vector2d;
import core.game.StateObservation;
import emergence.targets.ATarget;
import emergence.util.Helper;

/**
 * A Heuristic which is based on the distance to a target.
 * 
 */
public class DistanceRangeAscendingHeuristic extends AHeuristic {

	/** the target to which the distance will be computed */
	private ATarget target;

	/**
	 * Construct an Instance by setting the target.
	 * 
	 * @param target
	 */
	public DistanceRangeAscendingHeuristic(ATarget target) {
		super();
		this.target = target;
	}

	/**
	 * Computes the normalized distance to the next target object.
	 */
	@Override
	public double evaluateState(StateObservation stateObs) {
		// get the maximal distance
		Dimension dim = stateObs.getWorldDimension();
		double maxDistance = dim.getHeight() + dim.getWidth();
		Vector2d targetPos = target.getPosition(stateObs);
		if (targetPos == null)
			return 0;
		double norm = 1
				- Helper.distance(stateObs.getAvatarPosition(), targetPos)
				/ maxDistance;
		return norm;
	}

	/**
	 * Create String Object for csv file.
	 */
	@Override
	public String toCSVString() {
		return "DistanceRangeAscendingHeuristic";
	}

}
