package emergence.strategy.evolution;

import java.util.ArrayList;

import ontology.Types;
import ontology.Types.ACTIONS;
import tools.Vector2d;
import core.game.StateObservation;
import emergence.Factory;
import emergence.agents.EvolutionaryAgent;
import emergence.heuristics.AHeuristic;
import emergence.heuristics.DeltaScoreHeuristic;
import emergence.nodes.GenericNode;
import emergence.targets.ATarget;
import emergence.util.Helper;

public class EvolutionaryNode extends GenericNode<Object> implements Comparable<EvolutionaryNode>{

	private double score = Double.NEGATIVE_INFINITY;
	
	private double heuristicValue = Double.POSITIVE_INFINITY;
	
	
	public EvolutionaryNode(StateObservation stateObs) {
		super(stateObs);
	}

	public EvolutionaryNode(StateObservation stateObs, ArrayList<ACTIONS> path) {
		super(stateObs, path);
	}
	

	/**
	 * Sets the path length of this path by removing or adding actions.
	 * 
	 * @param level
	 *            that should be set!
	 */
	public void setLength(int level, StateObservation stateObs) {
		while (path.size() < level) {
			path.add(Helper.getRandomEntry(stateObs.getAvailableActions()));
		}
		while (path.size() > level) {
			path.remove(path.size() - 1);
		}
	}

	/**
	 * Removes the first action
	 */
	public void removeFirstAction() {
		if (!path.isEmpty()) {
			path.remove(0);
		}
	}

	public static EvolutionaryNode random(StateObservation stateObs, int pathLength) {
		EvolutionaryNode evoNode = new EvolutionaryNode(stateObs);
		// generate the path -> depends on the path length
		for (int i = 0; i < pathLength; i++) {
			Types.ACTIONS random = Helper.getRandomEntry(stateObs.getAvailableActions());
			evoNode.getPath().add(random);
		}
		return evoNode;
	}
	
	
	public void simulate(StateObservation stateObs) {
		this.stateObs = stateObs;
		this.score = 0;
		
		StateObservation simulatedState = stateObs.copy();
		for (ACTIONS a : path) {
			AHeuristic heuristic = new DeltaScoreHeuristic(simulatedState.getGameScore());
			Factory.getSimulator().advance(simulatedState, a);
			score += heuristic.evaluateState(simulatedState);
		}
		
		ATarget winningTarget = Factory.getEnvironment().getWinningTarget(stateObs);
		ATarget scoringTarget = Factory.getEnvironment().getScoringTarget(stateObs);
		if (winningTarget != null) {
			Vector2d targetPos = winningTarget.getPosition(simulatedState);
			if (targetPos == null) heuristicValue = 0;
			else heuristicValue = Helper.distance(targetPos, simulatedState.getAvatarPosition());
		} else if (scoringTarget != null) {
			Vector2d targetPos = scoringTarget.getPosition(simulatedState);
			if (targetPos == null) heuristicValue = 0;
			else heuristicValue = Helper.distance(targetPos, simulatedState.getAvatarPosition());
		}
		
	}
	

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	
	public EvolutionaryNode mutate() {
		return crossover(EvolutionaryNode.random(stateObs, getLevel()));
	}

	
	public EvolutionaryNode crossover(EvolutionaryNode p) {
		ArrayList<ACTIONS> pathNew = new ArrayList<>();
		for (int i = 0; i < path.size(); i++) {
			ACTIONS a = (EvolutionaryAgent.r.nextDouble() < 0.5) ? path.get(i) : p.getPath().get(i);
			pathNew.add(a);
		}
		return new EvolutionaryNode(stateObs, pathNew);
	}
	
	@Override
	public String toString() {
		return String.format("score:%s | heuristic:%s | ", score, heuristicValue) + super.toString() ;
	}
	
	
	@Override
	public int compareTo(EvolutionaryNode o) {
		int result = ((Double)o.getScore()).compareTo((Double) getScore());
		if (result == 0) {
			result = ((Double)getHeuristicValue()).compareTo((Double) o.getHeuristicValue());
		}
		return result;
	}

	public double getHeuristicValue() {
		return heuristicValue;
	}
	

}
