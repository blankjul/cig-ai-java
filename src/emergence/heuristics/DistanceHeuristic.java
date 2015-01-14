package emergence.heuristics;

import ontology.Types.WINNER;
import tools.Vector2d;
import core.game.StateObservation;
import emergence.targets.ATarget;
import emergence.util.Helper;
import emergence.util.ObservationUtil;

/**
 * A Heuristic which is based on the distance to a target.
 * @author spakken
 *
 */
public class DistanceHeuristic extends AHeuristic {

	/** the target to which the distance will be computed */
	private ATarget target;

	/**
	 * Construct an Instance by setting the target.
	 * 
	 * @param target
	 */
	public DistanceHeuristic(ATarget target) {
		this.target = target;
	}

	/**
	 * Computes the distance to the next target object.
	 */
	@Override
	public double evaluateState(StateObservation stateObs) {
		if (stateObs.getGameWinner() == WINNER.PLAYER_WINS)
			return 0;
		else if (stateObs.getGameWinner() == WINNER.PLAYER_LOSES)
			return Double.POSITIVE_INFINITY;

		if (ObservationUtil.collisionLastStep(stateObs) == target.getItype())
			return 0;

		Vector2d pos = stateObs.getAvatarPosition();
		
		// if the target does not exists anymore
		Vector2d targetPos = target.getPosition(stateObs);
		if (targetPos == null) return Double.POSITIVE_INFINITY;
		
		return Helper.distance(pos, targetPos);
	}
	
	/**
	 * Create String Object for csv file.
	 */
	@Override
	public String toCSVString() {
		return "DistanceHeuristic";
	}

	
}
