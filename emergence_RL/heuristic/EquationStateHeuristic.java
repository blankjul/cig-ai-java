package emergence_RL.heuristic;


import java.util.ArrayList;
import java.util.Random;

import ontology.Types.WINNER;
import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;

public class EquationStateHeuristic extends AHeuristic{

	public double[] weights;

	public double fitness = Double.NEGATIVE_INFINITY;
	
	public String createdOnGame = "";
	
	public static EquationStateHeuristic random() {
		double[] weights = new double[10];
		for (int i = 0; i < weights.length; i++) {
			Random r = new Random();
			double rangeMin = -100;
			double rangeMax = +100;
			double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
			weights[i] = randomValue;
		}
		return new EquationStateHeuristic(weights);
	}
	

	@Override
	public String toString() {
		String s = this.createdOnGame + " --> ";
		for (double d : weights) {
			s += String.valueOf(d) + ",";
			//s += String.format("%.2f,",  d);
		}
		s = s.substring(0, s.length()-1);
		return s;
	}
	
	
	public EquationStateHeuristic(double[] weights) {
		this.weights = weights;
	}
	
	public EquationStateHeuristic(String createdOnGame, double[] weights) {
		this(weights);
		this.createdOnGame = createdOnGame;
	}

	
	@Override
	public double evaluateState(StateObservation stateObs) {

		if (stateObs.getGameWinner() == WINNER.PLAYER_WINS) {
			return Double.POSITIVE_INFINITY;
		} else if (stateObs.getGameWinner() == WINNER.PLAYER_LOSES) {
			 return Double.NEGATIVE_INFINITY;
		}
		
		double[] state = new double[1];
		state[0] = stateObs.getGameScore();

		
		double[] npc = npc(stateObs, "npc");
		double[] portals = npc(stateObs, "portals");
		double[] resource = npc(stateObs, "resource");

		double[] value = concatAll(state,npc,portals,resource);

		double score = 0;
		for (int i = 0; i < value.length; i++) {
			score += value[i] * weights[i];
		}
		return score;

	}

	public static double distance(Vector2d from, Vector2d to) {
		return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
	}

	
	
	private double[] npc(StateObservation stateObs, String type) {
		double[] eq = new double[3];
		Vector2d avatarPosition = stateObs.getAvatarPosition();
		ArrayList<Observation>[] positions = null;
		
		if (type.equals("npc")) {
			positions = stateObs.getNPCPositions(avatarPosition);
		} else if (type.equals("portals")) {
			positions = stateObs.getPortalsPositions(avatarPosition);
		} else if (type.equals("resource")) {
			positions = stateObs.getResourcesPositions(avatarPosition);
		}

		if (positions == null)
			return eq;

		for (int i = 0; i < positions.length && i < 3; i++) {
			ArrayList<Observation> listObs = positions[i];
			if (listObs == null || listObs.isEmpty())
				continue;
			Observation obs = listObs.get(0);
			eq[i] = distance(stateObs.getAvatarPosition(), obs.position);
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
