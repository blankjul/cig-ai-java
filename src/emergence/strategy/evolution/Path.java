package emergence.strategy.evolution;

import java.util.ArrayList;

import ontology.Types;
import ontology.Types.ACTIONS;
import tools.Vector2d;
import core.game.StateObservation;
import emergence.agents.EvolutionaryAgent;
import emergence.heuristics.AHeuristic;
import emergence.heuristics.DeltaScoreHeuristic;
import emergence.heuristics.TargetHeuristic;
import emergence.util.Helper;

/**
 * This is just one element that is evolutionary. It's a path that consists of
 * several actions that could be simulated. Also it has several scores that
 * could be interesting for the evaluation.
 *
 */
public class Path extends Evolutionary<Path> {

	// all moves that should be performed!
	private ArrayList<ACTIONS> list = null;

	// all scores that are saved from this path
	private double[] scores = new double[] { Double.NEGATIVE_INFINITY,
			Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY , Double.NEGATIVE_INFINITY};

	// length of the calculated path
	private int pathLength;

	// all available actions
	private ArrayList<Types.ACTIONS> actions;

	
	/**
	 * Constructs a path that has the size of path length. Only actions of the
	 * actions list are used.
	 * 
	 * @param pathLength
	 *            length of the path
	 * @param actions
	 *            could be used
	 */
	public Path(int pathLength, ArrayList<Types.ACTIONS> actions) {
		this.pathLength = pathLength;
		this.list = new ArrayList<Types.ACTIONS>();
		this.actions = actions;

		// generate the path -> depends on the path length
		for (int i = 0; i < pathLength; i++) {
			Types.ACTIONS random = Helper.getRandomEntry(actions);
			list.add(random);
		}

	}

	@Override
	public Path random() {
		Path p = new Path(pathLength, actions);
		return p;
	}

	public void simulate(StateObservation stateObs) {
		AHeuristic heuristic = new DeltaScoreHeuristic(stateObs.getGameScore());
		scores[0] = 0;
		Vector2d last_avatar_position = stateObs.getAvatarPosition();
		double blocksize = stateObs.getBlockSize();
		boolean portal = false;
		
		for (int i = 0; i < pathLength; i++) {
			Types.ACTIONS a = list.get(i);
			stateObs.advance(a);
			//when he used an portal
			if(last_avatar_position.dist(stateObs.getAvatarPosition()) > blocksize*2 && !stateObs.isGameOver()){
				portal = true;
				//System.out.println("USED PORTAL: " + last_avatar_position.toString() + "  new pos " + stateObs.getAvatarPosition() + "  blocksize: " + blocksize);
			}
			scores[0] += heuristic.evaluateState(stateObs);
			last_avatar_position = stateObs.getAvatarPosition().copy();
		}
		TargetHeuristic targetHeuristic = new TargetHeuristic(new int[] { 0, 0,
				0, 1, 0, 0, 0, 0, 0, 0, 0, 0 });
		
		if(!portal){
			scores[1] = targetHeuristic.evaluateState(stateObs);
		}else{
			scores[1] = 10;
		}
		
		targetHeuristic = new TargetHeuristic(new int[] { 1, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
		scores[2] = targetHeuristic.evaluateState(stateObs);
		
		targetHeuristic = new TargetHeuristic(new int[] { 0, 0,
				0, 0, 1, 0, 0, 0, 0, 0, 0, 0 });
		scores[3] = targetHeuristic.evaluateState(stateObs);
		
		//System.out.println("scores  " + scores[0] + "  " + scores[1] +  "  " + scores[2]);
	}

	public void resetScore() {
		scores = new double[] { Double.NEGATIVE_INFINITY,
				Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY , Double.NEGATIVE_INFINITY};
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
			ACTIONS a = (EvolutionaryAgent.r.nextDouble() < 0.5) ? list.get(i) : p.list
					.get(i);
			result.list.add(a);
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("[score:%s , portal:%s, npc:%s, length:%s] ", scores[0],
				scores[1], scores[2], list.size()));
		for (ACTIONS a : list) {
			sb.append(a + ",");
		}
		return sb.toString();
	}

	public double getScore() {
		return scores[0];
	}

	public double getPortalValue() {
		return scores[1];
	}

	public double getNPCValue() {
		return scores[2];
	}
	
	public double getPortal2Value() {
		return scores[3];
	}

	/**
	 * Sets the path length of this path by removing or adding actions.
	 * 
	 * @param pathLength that should be set!
	 */
	public void setPathLength(int pathLength) {
		this.pathLength = pathLength;
		while (list.size() < this.pathLength) {
			list.add(Helper.getRandomEntry(actions));
		}
		while (list.size() > this.pathLength) {
			list.remove(list.size() - 1);
		}
	}
	
	
	public void removeFirstAction() {
		if (!list.isEmpty()) {
			list.remove(0);
			--pathLength;
		}
	}

	public Types.ACTIONS getFirstAction() {
		if (!list.isEmpty()) {
			return list.get(0);
		} else return Types.ACTIONS.ACTION_NIL;
	}

	public int getPathLength() {
		return pathLength;
	}

	public ArrayList<Types.ACTIONS> getActions() {
		return actions;
	}

	
}
