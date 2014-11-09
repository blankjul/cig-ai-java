package emergence_HR;

import java.util.LinkedList;
import java.util.Queue;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.EquationStateHeuristic;

public class Agent extends AbstractPlayer {

	final private boolean VERBOSE = false;

	double[] weights = { -82.59390978541923, 63.04858071534042, -7.002335306168078, -2.730598787524329, -59.79051490304785, -73.03200846990599, 39.91585019469065, -79.5935860994278, -42.0011469372217, -66.83035683017607};
	
	public EquationStateHeuristic heuristic = new EquationStateHeuristic(weights);
	
	
	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
	}

	
	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		
		Types.ACTIONS action = null;           // The action we will finally be executed

		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		TreeNode root = new TreeNode(stateObs);
		queue.addAll(root.getChildren());

		// initialize the values for the heuristic
		double maxQ = Double.NEGATIVE_INFINITY;
		
		
		ActionTimer timer = new ActionTimer(elapsedTimer); // Initialize the timer
		
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

		if (VERBOSE) System.out.println(timer.status());
		return action;

	}
}
