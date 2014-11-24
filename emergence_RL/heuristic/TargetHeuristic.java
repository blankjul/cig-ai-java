package emergence_RL.heuristic;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;
import emergence_RL.strategies.UCT.UCTSearch;

public class TargetHeuristic extends AHeuristic {

	// number of targets of category that could be followed
	public static final int NUM_TARGETS = 3;

	// all heuristics that are possible!
	public static Set<TargetHeuristic> heuristics = new HashSet<TargetHeuristic>();
	
	// add a tie breaker to the normed values or not
	public final boolean USE_TIEBREAKER = true;

	// weights for the formula
	public int[] weights = {0,0,0,0,0,0,0,0,0,0,0,0};
	
	public ArrayList<Double> distances = new ArrayList<Double>();
	
	
	
	public TargetHeuristic(int[] weights) {
		this.weights = weights;
	}
	

	@Override
	/**
	 * This method does not fit into the interface
	 */
	public double evaluateState(StateObservation stateObs) {

		// get all the distances to the objects
		distances = getDistances(stateObs);

		// get the maximal distance
		Dimension dim = stateObs.getWorldDimension();
		double max = dim.getHeight() + dim.getWidth();

		// norm all the values!
		for (int i = 0; i < distances.size(); i++) {
			double d = distances.get(i);
			double norm = -1;
			if (d == 0) {
				norm = 1;
			} else if (d == -1) {
				norm = 0;
			} else {
				norm = 1 - d / max;
				if (USE_TIEBREAKER)
					norm += UCTSearch.epsilon * UCTSearch.r.nextDouble();
			}
			double value = norm * weights[i];
			distances.set(i, value);
		}
		return -1;
	}
	
	

	
	public int hashCode() {
		for (int i = 0; i < weights.length; i++) {
			if (weights[i] == 1) return i;
		}
		return weights.length;
    }
	
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof TargetHeuristic))return false;
	    TargetHeuristic obj = (TargetHeuristic)other;
	    for (int i = 0; i < weights.length; i++) {
			if (obj.weights[i] != weights[i]) return false;
		}
	    return true;
	}
	
	
	public String toString() {
		return Arrays.toString(weights);
	}
	
	
	public static TargetHeuristic createRandom(StateObservation stateObs) {
		createAll(stateObs);
		if (heuristics.size() > 0) {
			List<TargetHeuristic> asList = new ArrayList<TargetHeuristic>(
					heuristics);
			Collections.shuffle(asList);
			return (TargetHeuristic) asList.get(0);
		}
		return null;
	}
	
	/**
	 * Return all possible good heuristics!
	 * @param stateObs
	 * @return
	 */
	public static Set<TargetHeuristic> createAll(StateObservation stateObs) {
		ArrayList<Double> distances = getDistances(stateObs);
		// calculate a weight vector for using a strategy or not
		for (int i = 0; i < distances.size(); i++) {
			if (distances.get(i) != -1) {
				int[] weights = new int[NUM_TARGETS * 4];
				weights[i] = 1;
				// update the target heuristic set all the time!
				heuristics.add( new TargetHeuristic(weights));
			}
		}
		return heuristics;
	}
	

	/*
	 * get all the distances in a list
	 */
	public static ArrayList<Double> getDistances(StateObservation stateObs) {
		ArrayList<Double> distances = new ArrayList<Double>();
		// get all the different distances
		distances.addAll(target(stateObs, "npc"));
		distances.addAll(target(stateObs, "portals"));
		distances.addAll(target(stateObs, "resource"));
		distances.addAll(target(stateObs, "movable"));
		return distances;
	}

	/*
	 * This function returns always an array with the next few distances. It is
	 * an double array. If there exists not so many targets like the parameter
	 * num says, the distance is infinity!
	 */
	public static ArrayList<Double> target(StateObservation stateObs,
			String type) {
		ArrayList<Double> eq = new ArrayList<Double>();
		for (int i = 0; i < NUM_TARGETS; i++) {
			eq.add(-1d);
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
		for (int i = 0; i < positions.length && i < eq.size(); i++) {
			ArrayList<Observation> listObs = positions[i];
			if (listObs == null || listObs.isEmpty())
				continue;
			Observation obs = listObs.get(0);
			eq.set(i, distance(avatarPosition, obs.position));
		}
		return eq;
	}
	
	

}
