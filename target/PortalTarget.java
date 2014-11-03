package emergence_HR.target;

import java.util.ArrayList;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;

public class PortalTarget extends AImmovableTarget{


	public PortalTarget(Vector2d targetPos) {
		super(targetPos);
	}

	public static ArrayList<ATarget> create(StateObservation stateObs) {
		
		ArrayList<ATarget> list = new ArrayList<ATarget>();
		
		Vector2d avatarPosition = stateObs.getAvatarPosition();
		ArrayList<Observation>[] portalPositions = stateObs.getPortalsPositions(avatarPosition);
		
		if (portalPositions == null) return list;
		for (int i = 0; i < portalPositions.length; i++) {
			ArrayList<Observation> listObs = portalPositions[i];
			if (listObs == null || listObs.isEmpty()) continue;
			Observation obs = listObs.get(0);
			ATarget t = new PortalTarget(obs.position);
			list.add(t);
		}
		
		return list;
		
	}




}
