package emergence.heuristics;

import java.awt.Dimension;

import tools.Vector2d;
import core.game.StateObservation;
import emergence.targets.ATarget;
import emergence.util.Helper;

public class DistanceRangeAscendingHeuristic extends AHeuristic{

	private ATarget target;
	
	
	public DistanceRangeAscendingHeuristic(ATarget target) {
		super();
		this.target = target;
	}

	@Override
	public double evaluateState(StateObservation stateObs) {
		// get the maximal distance
		Dimension dim = stateObs.getWorldDimension();
		double maxDistance = dim.getHeight() + dim.getWidth();
		Vector2d targetPos = target.getPosition(stateObs);
		if (targetPos == null) return 0;
		double norm = 1 - Helper.distance(stateObs.getAvatarPosition(), targetPos) / maxDistance;
		return norm;
	}

	@Override
	public String toCSVString() {
		return "DistanceRangeAscendingHeuristic";
	}
	
	

}
