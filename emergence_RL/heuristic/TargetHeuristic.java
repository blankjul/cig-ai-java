package emergence_RL.heuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;
import emergence_RL.strategies.uct.UCTSettings;

public class TargetHeuristic extends AHeuristic {

	// this is always the last used heuristic distance
	public int lastUsed = -1;

	// this just the statistic how often a minimal distance is used.
	public ArrayList<Double> distances = new ArrayList<Double>();


	public int visitAll = 0;
	
	public static Random r = new Random();

	@Override
	public double evaluateState(StateObservation stateObs) {

		input(stateObs);
		if (distances == null | distances.isEmpty()) return 0;
		double max = Collections.max(distances);
		double min = Collections.min(distances);

		for (int i = 0; i < distances.size(); i++) {
			double d = distances.get(i);
			double norm = (max != min) ? 1 - ((d - min) / (max - min)) : 1;
			distances.set(i, norm);
		}

		double maxValue = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < distances.size(); i++) {
			double value = distances.get(i);
			double tieBreaker = UCTSettings.epsilon * r.nextDouble();
			if (value +  tieBreaker > maxValue) {
				maxValue = value +  tieBreaker;
			}
		}

		++visitAll;
		return maxValue;

	}

	

	private void input(StateObservation stateObs) {
		distances.clear();

		// get all the different distances
		distances.addAll(target(stateObs, "npc"));
		distances.addAll(target(stateObs, "portals"));
		distances.addAll(target(stateObs, "resource"));
		distances.addAll(target(stateObs, "movable"));
	}

	/**
	 * This function returns always an array with the next few distances. It is
	 * an double array. If there exists not so many targets like the parameter
	 * num says, the distance is infinity!
	 * 
	 * @param stateObs
	 * @param type
	 * @param num
	 * @return
	 */
	private ArrayList<Double> target(StateObservation stateObs, String type) {
		ArrayList<Double> eq = new ArrayList<Double>();

		Vector2d avatarPosition = stateObs.getAvatarPosition();
		ArrayList<Observation>[] positions = null;

		if (type.equals("npc")) {
			positions = stateObs.getNPCPositions(avatarPosition);
		} else if (type.equals("portals")) {
			positions = stateObs.getPortalsPositions(avatarPosition);
		} else if (type.equals("resource")) {
			positions = stateObs.getResourcesPositions(avatarPosition);
		} else if (type.equals("movable")) {
			positions = stateObs.getMovablePositions(avatarPosition);
		} else if (type.equals("immovable")) {
			positions = stateObs.getImmovablePositions(avatarPosition);
		}

		if (positions == null)
			return eq;

		for (int i = 0; i < positions.length; i++) {
			ArrayList<Observation> listObs = positions[i];
			if (listObs == null || listObs.isEmpty())
				continue;
			Observation obs = listObs.get(0);
			eq.add(distance(avatarPosition, obs.position));
		}
		return eq;
	}

}
