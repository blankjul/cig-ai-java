package emergence_HR.heuristics;

import java.util.ArrayList;

import ontology.Types;
import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;

public class PortalHeuristic extends StateHeuristic {

	public PortalHeuristic() {
	}

	public double evaluateState(StateObservation stateObs) {

		Vector2d avatarPosition = stateObs.getAvatarPosition();
		ArrayList<Observation>[] portals = stateObs
				.getPortalsPositions(avatarPosition);

		Types.WINNER w = stateObs.getGameWinner();
		if (w == Types.WINNER.PLAYER_WINS) {
			return 0;
		} else if (w == Types.WINNER.PLAYER_LOSES) {
			return Double.MAX_VALUE;
		}

		// if there are portals take the nearest one
		if (portals != null && portals.length > 0 && portals[0] != null
				&& !portals[0].isEmpty()) {
			
			Observation portal = portals[0].get(0);
			
			Vector2d myPos = stateObs.getAvatarPosition();
			Vector2d portalPos = portal.position;
			return Math.abs(myPos.x - portalPos.x) + (myPos.y - portalPos.y);
		}
		return Double.MAX_VALUE;

	}
	
	public String toString() {
		return String.format("%s ", getClass().getSimpleName());
	}

}
