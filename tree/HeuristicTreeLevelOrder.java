package emergence_HR.tree;

import java.util.LinkedList;
import java.util.Queue;

import emergence_HR.ActionTimer;
import emergence_HR.heuristics.AHeuristic;

/**
 * This strategy uses the given heuristic and just look for the best step in level order.
 * 
 * 
 * 
 * Normally there should not be any time problems
 */
public class HeuristicTreeLevelOrder extends AHeuristicTree {

	public HeuristicTreeLevelOrder(Node root, AHeuristic heuristic) {
		super(root);
	}

	public void expand(ActionTimer timer, AHeuristic heuristic) {

		double bestHeuristic = Double.NEGATIVE_INFINITY;

		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);

		// check whether there is time and we've further tree nodes
		while (timer.isTimeLeft() && !queue.isEmpty()) {
			
			// just look for the head of the queue
			Node n = queue.poll();
			heuristic.addScore(n);
			
			// if it is the best state until now save root as the best action
			double score = heuristic.evaluateState(n.stateObs);
			if (score > bestHeuristic) {
				bestHeuristic = score;
				bestAction = n.rootAction;
			}
			
			// add all children to the queue
			LinkedList<Node> children = n.getChildren();
			queue.addAll(children);

			timer.addIteration();
		}

	}

}