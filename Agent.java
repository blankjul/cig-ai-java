package emergence_HR;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.PortalHeuristic;
import emergence_HR.heuristics.StateHeuristic;
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
		StateHeuristic heuristic = new PortalHeuristic();

		// queue for all the following nodes and set the heuristic
		final Queue<Node> queue = new PriorityQueue<Node>(11,
				new NodeComparator());

		final Set<String> closedList = new HashSet<String>();

		Node root = new Node(stateObs);
		root.setHeuristic(heuristic);
		root.level = 0;
		queue.add(root);
		closedList.add(root.hash());

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

			// add children to the queue
			for (Node child : node.getChildren()) {
				if (!closedList.contains(child.hash())) {
					queue.add(child);
					closedList.add(child.hash());
				}
			}

			timer.addIteration();
		}

		if (VERBOSE)
			System.out.println(timer.status());

		return action;

	}
}
