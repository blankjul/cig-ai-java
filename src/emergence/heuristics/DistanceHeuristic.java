package emergence.heuristics;

import ontology.Types.WINNER;
import tools.Vector2d;
import core.game.StateObservation;
import emergence.targets.ATarget;
import emergence.util.Helper;
import emergence.util.ObservationUtil;

public class DistanceHeuristic {

	private ATarget target;

	public DistanceHeuristic(ATarget target) {
		this.target = target;
	}

	public double getDistance(StateObservation stateObs) {
		if (stateObs.getGameWinner() == WINNER.PLAYER_WINS) return 0;
		else if (stateObs.getGameWinner() == WINNER.PLAYER_LOSES) return Double.POSITIVE_INFINITY;
		
		if (ObservationUtil.collisionLastStep(stateObs) == target.getItype()) return 0;

		
		Vector2d pos = stateObs.getAvatarPosition();
		return Helper.distance(pos, target.position());
		
	}



}
