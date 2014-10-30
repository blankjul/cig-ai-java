package emergence_HR.heuristics;

import java.util.ArrayList;

import ontology.Types;
import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;

public class PortalHeuristic extends StateHeuristic {

	public PortalHeuristic(StateObservation stateObs) {
	}

	public double evaluateState(StateObservation stateObs) {

		Vector2d avatarPosition = stateObs.getAvatarPosition();
		ArrayList<Observation>[] portals = stateObs
				.getPortalsPositions(avatarPosition);

		Types.WINNER w = stateObs.getGameWinner();
		if (w == Types.WINNER.PLAYER_WINS) {
			return Double.MAX_VALUE;
		} else if (w == Types.WINNER.PLAYER_LOSES) {
			return Double.MIN_VALUE;
		}

		// if there are portals take the nearest one
		if (portals != null && portals.length > 0 && portals[0] != null
				&& !portals[0].isEmpty()) {
			double dist = portals[1].get(0).sqDist;
			return (dist == 0) ? Double.MAX_VALUE : 1 / dist;
		}
		return 0;

	}

}
