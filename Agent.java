package emergence_HR;

import java.util.LinkedList;
import java.util.Queue;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.SimpleStateHeuristic;
import emergence_HR.rules.RuleDeduction;
import emergence_HR.rules.RuleDeduction.RuleType;
import emergence_HR.rules.nodes.StateNode;

public class Agent extends AbstractPlayer {

	final private boolean VERBOSE = false;

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		LevelInfo.print(stateObs);
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		ActionTimer timer = new ActionTimer(elapsedTimer);

		RuleDeduction rd = new RuleDeduction();
		rd.explore(RuleType.AVATAR_PORTAL, stateObs, timer);

		Types.ACTIONS action = null; // The action we will finally be executed

		Queue<StateNode> queue = new LinkedList<StateNode>();
		StateNode root = new StateNode(stateObs);
		queue.addAll(root.getChildren());

		// initialize the values for the heuristic
		double maxQ = Double.NEGATIVE_INFINITY;
		SimpleStateHeuristic heuristic = new SimpleStateHeuristic(stateObs);

		// check whether there is time and we've further tree nodes
		while (timer.isTimeLeft() && !queue.isEmpty()) {

			StateNode node = queue.poll();
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
		System.out.println(action);
		return action;

	}
}
