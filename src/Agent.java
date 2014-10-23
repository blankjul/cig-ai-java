package controllers.cig;

import java.util.LinkedList;
import java.util.Queue;

import ontology.Types;
import tools.ElapsedCpuTimer;
import controllers.cig.heuristics.SimpleStateHeuristic;
import core.game.StateObservation;
import core.player.AbstractPlayer;

public class Agent extends AbstractPlayer {

	protected ActionTimer timer = null;

	public Agent(StateObservation so, ElapsedCpuTimer elapsedTimer) {
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		timer = new ActionTimer(elapsedTimer); // Initialize the timer
		Types.ACTIONS action = null; // The action we will finally be executed

		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		TreeNode root = new TreeNode(stateObs, Types.ACTIONS.ACTION_NIL);
		queue.addAll(root.getChildren());

		// initialize the values for the heuristic
		double maxQ = Double.NEGATIVE_INFINITY;
		SimpleStateHeuristic heuristic = new SimpleStateHeuristic(stateObs);

		// check whether there is time and we've further tree nodes
		while (timer.isTimeLeft() && !queue.isEmpty()) {
			timer.start();

			TreeNode node = queue.poll();
			StateObservation stCopy = node.getObservation();

			double Q = heuristic.evaluateState(stCopy);

			if (Q > maxQ) {
				maxQ = Q;
				action = node.getNextAction();
			}
			
			queue.addAll(node.getChildren());

			timer.stop();
		}

		System.out.println(timer.status());
		return action;

	}
}
