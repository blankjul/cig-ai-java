package emergence_HR.tree;

import java.util.LinkedList;

import emergence_HR.ActionTimer;
import emergence_HR.heuristics.AHeuristic;

/**
 * This strategy uses the given heuristic and just look for the next best step.
 * Normally there should not be any time problems.
 * 
 */
public class HeuristicTreeOneStep extends HeuristicTree {

	public HeuristicTreeOneStep(Node root, AHeuristic heuristic) {
		super(root, heuristic);
	}

	public void expand(ActionTimer timer) {

		double bestHeuristic = Double.NEGATIVE_INFINITY;

		LinkedList<Node> children = getChildren(root);

		// check whether there is time and we've further tree nodes
		for (Node child : children) {
			double score = heuristic.evaluateState(child.stateObs);
			if (score > bestHeuristic) {
				bestHeuristic = score;
				action = child.getRootAction();
			}
			timer.addIteration();
		}

	}

}
