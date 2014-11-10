package emergence_HR;

import java.util.LinkedList;
import java.util.Queue;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.helper.ActionTimer;
import emergence_HR.heuristics.EquationStateHeuristic;
import emergence_HR.tree.Node;

public class StaticAgentManuell extends AbstractPlayer {

	final private boolean VERBOSE = false;

	
	public EquationStateHeuristic heuristic ;
	
	
	public StaticAgentManuell(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		heuristic = new EquationStateHeuristic(new double[] {-81.90769324283849,-39.44793318768141,-65.46251835204176,67.66230768594019,-41.746347038418286,11.276588777149186,63.79286972090824,-23.396258609779736,-80.43084681995711,-51.50159000365024});
	}

	
	
	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		Types.ACTIONS action = null;           // The action we will finally be executed
		Queue<Node> queue = new LinkedList<Node>();
		Node root = new Node(stateObs);
		queue.addAll(root.getChildren());

		// initialize the values for the heuristic
		double maxQ = Double.NEGATIVE_INFINITY;
		
		
		ActionTimer timer = new ActionTimer(elapsedTimer); // Initialize the timer
		
		// check whether there is time and we've further tree nodes
		while (timer.isTimeLeft() && !queue.isEmpty()) {

			Node node = queue.poll();
			StateObservation stCopy = node.stateObs;

			double Q = heuristic.evaluateState(stCopy);
			if (Q > maxQ) {
				maxQ = Q;
				action = node.rootAction;
			}
			queue.addAll(node.getChildren());
			timer.addIteration();
		}

		if (VERBOSE) System.out.println(timer.status());
		return action;

	}
}

