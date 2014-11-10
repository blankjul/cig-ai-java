package emergence_HR.target;

import java.util.ArrayList;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;
import emergence_HR.heuristics.AHeuristic;

public class NPCTarget extends AMovableTarget {

	public NPCTarget(int index) {
		super(index);
	}

	public static ArrayList<ATarget> create(StateObservation stateObs) {

		ArrayList<ATarget> list = new ArrayList<ATarget>();
		Vector2d avatarPosition = stateObs.getAvatarPosition();
		ArrayList<Observation>[] npcPositions = stateObs
				.getNPCPositions(avatarPosition);

		if (npcPositions == null)
			return list;

		for (int i = 0; i < npcPositions.length; i++) {
			ArrayList<Observation> listObs = npcPositions[i];
			if (listObs == null || listObs.isEmpty())
				continue;
			ATarget t = new NPCTarget(i);
			list.add(t);
		}
		return list;
	}

	@Override
	public double distance(StateObservation stateObs) {
		Vector2d avatarPos = stateObs.getAvatarPosition();

		ArrayList<Observation>[] npcPositions = stateObs
				.getNPCPositions(avatarPos);

		if (npcPositions == null)
			return Double.POSITIVE_INFINITY;

		ArrayList<Observation> listObs = npcPositions[index];
		if (listObs == null || listObs.isEmpty())
			return Double.POSITIVE_INFINITY;
		Observation obs = listObs.get(0);

		return AHeuristic.distance(avatarPos, obs.position);
	}

}
