package emergence_RL.heuristic;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;
import emergence_RL.helper.Helper;
import emergence_RL.uct.UCTSettings;

public class TargetHeuristic extends AHeuristic {

	// number of targets that should be tracked
	public final static int numberOfTargets = 3;

	// this is always the last used heuristic distance
	public static int lastUsed = -1;

	// this just the statistic how often a minimal distance is used.
	public static Double[] distancesReal = new Double[] {0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d};
	
	// this just the statistic how often a minimal distance is used.
	public static Double[] distances = new Double[] {0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d};

	// this just the statistic how often a minimal distance is used.
	public static int[] used = new int[] {0,0,0,0,0,0,0,0,0,0,0,0};

	// track the reward of the used statistic
	public static Double[] reward = new Double[] {0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d};
	
	// track the reward of the used statistic
	public static Double[] exploitation = new Double[] {0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d};
	
	public static Double[] exploration = new Double[] {0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d};
	
	public static Double[] result = new Double[] {0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d};
	
	public static Random r = new Random();
	
	
	
	public double visitAll = 0;

	@Override
	public double evaluateState(StateObservation stateObs) {

		
		/*
		 * the next step normalizes the distances between [0,1]
		 */
		distancesReal = input(stateObs);
		distances = input(stateObs);
		Dimension d = stateObs.getWorldDimension();
		double maxDistance = d.height + d.width;
		for (int i = 0; i < distances.length; i++) {
			 if (distances[i] == -1) {
				 distances[i] = maxDistance;
			 }
		}
		Helper.normalize(distances);
		for (int i = 0; i < distances.length; i++) {
			distances[i] = 1 - distances[i];
		}
		maxDistance = Double.NEGATIVE_INFINITY;

		
		
		/*
		 * Calculate for each target the average reward!
		 */
		for (int i = 0; i < exploitation.length; i++) {
			if (distances[i] == 0 || used[i] == 0) exploitation[i] = 0d;
			else exploitation[i] = reward[i] / (double) used[i];
		}
		Helper.normalize(exploitation);
		
		for (int i = 0; i < exploration.length; i++) {
			exploration[i] = Math.sqrt(2) * Math.sqrt(Math.log(visitAll + 1)
					/ (used[i]));
		}

		
		for (int i = 0; i < result.length; i++) {
			result[i] = distances[i] * (exploitation[i] + exploration[i]);
		}
		Helper.normalize(result);
		
		
		int maxIndex = 0;
		double maxValue = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < result.length; i++) {
			if (result[i] + r.nextDouble() * UCTSettings.epsilon > maxValue) {
				maxIndex = i;
				maxValue = result[i];
			}
		}

		// add one to the used field!
		lastUsed = maxIndex;
		used[lastUsed] += 1;
		++visitAll;
		
		
		return maxValue;

	}

	public void norm(Double[] values) {
		double max = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < values.length; i++) {
			if (values[i] > max) max = values[i];
		}
		// just normalize all the values that they are in between [0,1]
		for (int i = 0; i < values.length; i++) {
			Double distance = values[i];
			// if we had reached the target immediately return 1!
			if (distance <= 0)
				values[i] = 1.0d;
			else if (distance == Double.POSITIVE_INFINITY)
				values[i] =  0.0d;
			else
				// normalize the value
				// if we are very close return 1 else something that is lower
				// the heuristic is always larger than zero!
				values[i] = 1 - (distance / max);
		}
	}

	private Double[] input(StateObservation stateObs) {

		// get all the different distances
		Double[] npc = target(stateObs, "npc", numberOfTargets);
		Double[] portals = target(stateObs, "portals", numberOfTargets);
		Double[] resource = target(stateObs, "resource", numberOfTargets);
		Double[] movable = target(stateObs, "movable", numberOfTargets);
		
		return Helper.concatAll(npc, portals, resource, movable);

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
	private Double[] target(StateObservation stateObs, String type, int num) {
		Double[] eq = new Double[num];
		for (int i = 0; i < eq.length; i++) {
			eq[i] = -1d;
		}

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

		for (int i = 0; i < positions.length && i < num; i++) {
			ArrayList<Observation> listObs = positions[i];
			if (listObs == null || listObs.isEmpty())
				continue;
			Observation obs = listObs.get(0);
			eq[i] = distance(avatarPosition, obs.position);
		}
		return eq;
	}

}
