package emergence_HR;

import java.util.LinkedList;
import java.util.Queue;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.LookBackHeuristic;
import emergence_HR.heuristics.SimpleStateHeuristic;
import emergence_HR.heuristics.SpriteHeuristic;

/**
 * this class tries to build a deep, but slim Tree. To do that, 
 * some decisions are made randomly. To execute this agent, just
 * copy the act method in the Agent.java class.
 * @author spakken
 *
 */
public class AgentRandom extends AbstractPlayer {

	final private boolean VERBOSE = true;

	public AgentRandom(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
	}

	
	
	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		//set the number of children you want to compute
		int numberOfChildren = 1;
		
		Types.ACTIONS action = null;           // The action we will finally be executed

		Queue<TreeNodeRandom> queue = new LinkedList<TreeNodeRandom>();
		TreeNodeRandom root = new TreeNodeRandom(stateObs);
		
		//in the first step, put all available Actions in the Queue
		queue.addAll(root.getChildren(stateObs.getAvailableActions().size()));

		// initialize the values for the heuristic
		double maxQ = Double.NEGATIVE_INFINITY;
		SimpleStateHeuristic heuristic = new SimpleStateHeuristic(stateObs);
		
		//compute SpriteHeuristic for test only
		//SpriteHeuristic sprite_heuristic = new SpriteHeuristic(stateObs);
		//sprite_heuristic.evaluateState(stateObs);
		
		//compute LookBackHeuristic
		LookBackHeuristic lbheuristic = new LookBackHeuristic(stateObs);
		
		ActionTimer timer = new ActionTimer(elapsedTimer); // Initialize the timer
		//int depth = 0;
		//boolean b = true;
		// check whether there is time and we've further tree nodes
		while (timer.isTimeLeft() && !queue.isEmpty()) {

			TreeNodeRandom node = queue.poll();
			StateObservation stCopy = node.getObservation();

			double Q = heuristic.evaluateState(stCopy);
			double Q2 = lbheuristic.evaluateState(node);
			if (Q > maxQ) {
				maxQ = Q;
				action = node.getRootAction();
			}
			queue.addAll(node.getChildren(numberOfChildren));
			
			//depth++;
			timer.addIteration();
		}
		//System.out.println("tick: " + stateObs.getGameTick() + "  depth: " + depth);
		//if (VERBOSE) System.out.println(timer.status());
		return action;

	}
}