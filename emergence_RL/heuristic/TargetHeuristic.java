package emergence_RL.heuristic;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;

public class TargetHeuristic extends AHeuristic {

	// number of targets that should be tracked
	public static int numberOfTargets = 2;

	// dimension of the field that has to be set ones!
	public double dimension = -1;

	// this is always the last used heuristic distance
	public static int lastUsed = -1;

	// this just the statistic how often a minimal distance is used.
	public static int[] used = new int[4 * numberOfTargets];

	// this just the statistic how often a minimal distance is used.
	public static double[] rewards = new double[4 * numberOfTargets];

	public TargetHeuristic() {
	}

	@Override
	public double evaluateState(StateObservation stateObs) {

		// get the maximal distance
		if (dimension == -1) {
			Dimension d = stateObs.getWorldDimension();
			dimension = d.getHeight() + d.getWidth();
		}

		// get all the different distances
		Double[] npc = target(stateObs, "npc", numberOfTargets);
		Double[] portals = target(stateObs, "portals", numberOfTargets);
		Double[] resource = target(stateObs, "resource", numberOfTargets);
		Double[] movable = target(stateObs, "movable", numberOfTargets);

		// create a list with all that values
		ArrayList<Double> values = new ArrayList<Double>();
		values.addAll(Arrays.asList(npc));
		values.addAll(Arrays.asList(portals));
		values.addAll(Arrays.asList(resource));
		values.addAll(Arrays.asList(movable));


		// just normalize all the values that they are in between [0,1]
		for (int i = 0; i < values.size(); i++) {
			Double distance = values.get(i);
			// if we had reached the target immediately return 1!
			if (distance <= 0)
				values.set(i, 1.0d);
			else if (distance == Double.POSITIVE_INFINITY)
				values.set(i, 0.0d);
			else
				// normalize the value
				// if we are very close return 1 else something that is lower
				// the heuristic is always larger than zero!
				values.set(i, 1 - (distance / dimension));
		}
		
		// calculate the average rewards
		double[] avgRewards = new double[rewards.length];
		for (int i = 0; i < avgRewards.length; i++) {
			// exploration! use one times each heuristic target
			if (used[i] == 0) avgRewards[i] = 1;
			else avgRewards[i] = rewards[i] / used[i];
		}
		
		ArrayList<Double> result = new ArrayList<Double>(rewards.length);
		for (int i = 0; i < rewards.length; i++) {
			result.add(avgRewards[i] * values.get(i));
		}
		
		
		// get the minimal distance - only that target counts!
		double best = Collections.min(result);

		// add one to the used field!
		lastUsed = result.indexOf(best);
		used[lastUsed] += 1;

		return best;

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
			eq[i] = Double.POSITIVE_INFINITY;
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

	public double[] concatAll(double[]... array) {
		int len = 0;
		for (final double[] job : array) {
			len += job.length;
		}

		final double[] result = new double[len];

		int currentPos = 0;
		for (final double[] job : array) {
			System.arraycopy(job, 0, result, currentPos, job.length);
			currentPos += job.length;
		}

		return result;
	}

}
