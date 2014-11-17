package emergence_RL.heuristic;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;

public class TargetHeuristic extends AHeuristic{

	// number of targets that should be tracked
	public int numberOfTargets = 2;
	
	public double dimension = -1;
	
	public TargetHeuristic() {
	}

	
	@Override
	public double evaluateState(StateObservation stateObs) {

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

		
		// get the minimal distance - only that target counts!
		double distance = Collections.min(values);
		
		// if we had reached the target immediately return 1!
		if (distance <= 0) return 1;
		else if (distance == Double.POSITIVE_INFINITY) return 0;
		
		//get the maximal distance
		if (dimension == -1) {
			Dimension d = stateObs.getWorldDimension();
			dimension = d.getHeight() + d.getWidth();
		}
		
		// normalize the value
		// if we are very close return 1 else something that is lower
		// the heuristic is always larger than zero!
		double value = 1 - (distance / dimension);
		
		return value;

	}


	
	/**
	 * This function returns always an array with the next few distances.
	 * It is an double array. If there exists not so many targets like
	 * the parameter num says, the distance is infinity!
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
