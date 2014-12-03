package emergence_NI;

import java.util.ArrayList;

import ontology.Types;
import ontology.Types.ACTIONS;
import core.game.StateObservation;
import emergence_NI.helper.Helper;
import emergence_NI.heuristic.AHeuristic;
import emergence_NI.heuristic.DeltaHeuristic;
import emergence_NI.heuristic.TargetHeuristic;


/**
 * This is just one element that is evolutionary.
 * It's a path that consists of several actions that could be simulated.
 * Also it has several scores that could be interesting for the evaluation.
 *
 */
public class Path extends Evolutionary<Path> {

	// all moves that should be performed!
	public ArrayList<ACTIONS> list = null;

	// current score. null if not calculated yet
	private double score = Double.NEGATIVE_INFINITY;

	// length of the calculated path
	public int pathLength;

	// all available actions
	public ArrayList<Types.ACTIONS> actions;

	// heuristic value of this path
	public double portalValue = 0;
	
	// heuristic value of this path
	public double npcValue = 0;
	

	
	public Path(int pathLength, ArrayList<Types.ACTIONS> actions) {
		this.pathLength = pathLength;
		this.list = new ArrayList<Types.ACTIONS>();
		this.actions = actions;
		for (int i = 0; i < pathLength; i++) {
			Types.ACTIONS random = Helper.getRandomEntry(actions, Agent.r);
			list.add(random);
		}
	}

	@Override
	public Path random() {
		Path p = new Path(pathLength, actions);
		return p;
	}

	public void simulate(StateObservation stateObs) {
		AHeuristic heuristic = new DeltaHeuristic(stateObs.getGameScore());
		score = 0;
		for (int i = 0; i < pathLength; i++) {
			Types.ACTIONS a = list.get(i);
			stateObs.advance(a);
			score += heuristic.evaluateState(stateObs);
		}
		TargetHeuristic targetHeuristic = new TargetHeuristic(new int[] {0,0,0,1,0,0,0,0,0,0,0,0});
		portalValue = targetHeuristic.evaluateState(stateObs);
		targetHeuristic.weights = new int[] {1,0,0,0,0,0,0,0,0,0,0,0};
		npcValue = targetHeuristic.evaluateState(stateObs);
		
	}

	public void reset() {
		score = Double.NEGATIVE_INFINITY;
	}

	@Override
	public Path mutate() {
		Path entry = random();
		return crossover(entry);
	}

	@Override
	public Path crossover(Path p) {
		Path result = new Path(pathLength, actions);
		result.list.clear();
		for (int i = 0; i < list.size(); i++) {
			ACTIONS a = (Agent.r.nextDouble() < 0.5) ? list.get(i) : p.list
					.get(i);
			result.list.add(a);
		}
		return result;
	}

	public double getScore() {
		//return score + Agent.r.nextDouble()* 0.0000001;
		return score;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("[score:%s , portal:%s] ", score, portalValue));
		for (ACTIONS a : list) {
			sb.append(a + ",");
		}
		return sb.toString();
	}



	
}
