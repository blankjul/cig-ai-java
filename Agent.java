package emergence_HR;

import java.util.LinkedList;
import java.util.Queue;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.PortalHeuristic;

public class Agent extends AbstractPlayer {

	final private boolean VERBOSE = true;

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		LevelInfo.print(stateObs);
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		Types.ACTIONS action = null; // The action we will finally be executed

		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		TreeNode root = new TreeNode(stateObs);
		queue.addAll(root.getChildren());

		// initialize the values for the heuristic
		PortalHeuristic heuristic = new PortalHeuristic(stateObs);

		ActionTimer timer = new ActionTimer(elapsedTimer); // Initialize the
															// timer

		// check whether there is time and we've further tree nodes
		while (timer.isTimeLeft() && !queue.isEmpty()) {

			TreeNode node = queue.poll();
			StateObservation stCopy = node.getObservation();

			double Q = heuristic.evaluateState(stCopy);
			if (Q > maxQ) {
				maxQ = Q;
				action = node.getRootAction();
			}
			queue.addAll(node.getChildren());

			timer.addIteration();
		}

		if (VERBOSE)
			System.out.println(timer.status());
		
		return action;

	}
}
