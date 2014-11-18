package emergence_RL.heuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;
import emergence_RL.uct.UCTSettings;

public class TargetHeuristic extends AHeuristic {

	// this is always the last used heuristic distance
	public int lastUsed = -1;

	// this just the statistic how often a minimal distance is used.
	public ArrayList<Double> distances = new ArrayList<Double>();

	// this just the statistic how often a minimal distance is used.
	public ArrayList<Double> result = new ArrayList<Double>();

	// this just the statistic how often a minimal distance is used.
	public ArrayList<Integer> used = null;

	// this just the statistic how often a minimal distance is used.
	public ArrayList<Double> reward = null;

	public ArrayList<String> names = new ArrayList<String>();

	public ArrayList<Double> weights = new ArrayList<Double>();

	public int visitAll = 0;
	
	public static Random r = new Random();

	@Override
	public double evaluateState(StateObservation stateObs) {

		
		/*
		 * the next step normalizes the distances between [0,1]
		 */
		names.clear();
		input(stateObs);
		if (distances == null | distances.isEmpty()) return 0;
		double max = Collections.max(distances);
		double min = Collections.min(distances);

		// initialize weights if needed
		if (weights == null || weights.size() != distances.size() || Collections.max(weights) == 0) {
			weights = new ArrayList<Double>();
			for (int i = 0; i < distances.size(); i++) {
				weights.add(1d);
			}
		}

		result.clear();
		for (int i = 0; i < distances.size(); i++) {
			double d = distances.get(i);
			double norm = (max != min) ? 1 - ((d - min) / (max - min)) : 1;
			result.add(norm * weights.get(i));
		}

		// initialize reward
		if (reward == null || reward.size() != distances.size()) {
			reward = new ArrayList<Double>();
			for (int i = 0; i < distances.size(); i++) {
				reward.add(0d);
			}
		}

		
		double maxValue = Double.NEGATIVE_INFINITY;
		int maxIndex = 0;
		for (int i = 0; i < result.size(); i++) {
			double value = result.get(i);
			double tieBreaker = UCTSettings.epsilon * r.nextDouble();
			if (value +  tieBreaker > maxValue) {
				maxValue = value +  tieBreaker;
				maxIndex = i;
			}
		}

		
		// check for used field or init
		lastUsed = maxIndex;
		if (used == null || used.size() != distances.size()) {
			used = new ArrayList<Integer>();
			for (int i = 0; i < distances.size(); i++) {
				used.add(0);
			}
		}
		used.set(lastUsed, used.get(lastUsed) + 1);

		++visitAll;
		return maxValue;

	}

	public void norm(Double[] values) {
		double max = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < values.length; i++) {
			if (values[i] > max)
				max = values[i];
		}
		// just normalize all the values that they are in between [0,1]
		for (int i = 0; i < values.length; i++) {
			Double distance = values[i];
			// if we had reached the target immediately return 1!
			if (distance <= 0)
				values[i] = 1.0d;
			else if (distance == Double.POSITIVE_INFINITY)
				values[i] = 0.0d;
			else
				// normalize the value
				// if we are very close return 1 else something that is lower
				// the heuristic is always larger than zero!
				values[i] = 1 - (distance / max);
		}
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
			names.add(type);
		}
		return eq;
	}

}
