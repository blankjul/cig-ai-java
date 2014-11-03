package emergence_HR.target;

import java.util.ArrayList;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;

public class ResourceTarget extends AImmovableTarget{


	public ResourceTarget(Vector2d targetPos) {
		super(targetPos);
	}

	public static ArrayList<ATarget> create(StateObservation stateObs) {
		
		ArrayList<ATarget> list = new ArrayList<ATarget>();
		
		Vector2d avatarPosition = stateObs.getAvatarPosition();
		ArrayList<Observation>[] resourcePositions = stateObs.getResourcesPositions(avatarPosition);
		
		if (resourcePositions == null) return list;
		for (int i = 0; i < resourcePositions.length; i++) {
			ArrayList<Observation> listObs = resourcePositions[i];
			if (listObs == null || listObs.isEmpty()) continue;
			Observation obs = listObs.get(0);
			ATarget t = new ResourceTarget(obs.position);
			list.add(t);
		}
		
		return list;
		
	}




}
