package emergence_HR;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.StateHeuristic;
import emergence_HR.heuristics.Target;
import emergence_HR.heuristics.TargetHeuristic;
import emergence_HR.nodes.Node;
import emergence_HR.nodes.NodeComparator;

public class Agent extends AbstractPlayer {

	final private boolean VERBOSE = true;

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		LevelInfo.print(stateObs);
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		// The action we will finally be executed
		Types.ACTIONS action = Types.ACTIONS.ACTION_NIL;
		
		double bestHeuristic = Double.MAX_VALUE;

		// the current heuristic that is used
		ArrayList<Target> l = Target.getPortals(stateObs);
		Target t = l.get(0);
		StateHeuristic heuristic = new TargetHeuristic(t);

		// queue for all the following nodes and set the heuristic
		final Queue<Node> queue = new PriorityQueue<Node>(11,
				new NodeComparator());

		Node root = new Node(stateObs);
		root.setHeuristic(heuristic);
		root.level = 0;
		queue.add(root);

		// initialize the values for the heuristic and the timer
		ActionTimer timer = new ActionTimer(elapsedTimer);

		// check whether there is time and we've further tree nodes
		while (timer.isTimeLeft() && !queue.isEmpty()) {

			// get the first node 
			Node node = queue.poll();

			// look if we have a subtree with a really good heuristic
			if (node.getHeuristic() < bestHeuristic) {
				bestHeuristic = node.getHeuristic();
				action = node.getRootAction();
			}

		
			//LevelInfo.printNodes(queue);

			timer.addIteration();
		}

		if (VERBOSE)
			System.out.println(timer.status());

		return action;

	}
}
