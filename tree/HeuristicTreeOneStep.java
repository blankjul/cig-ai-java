package emergence_HR.tree;

import java.util.LinkedList;

import emergence_HR.ActionTimer;
import emergence_HR.heuristics.AHeuristic;

/**
 * This strategy uses the given heuristic and just look for the next best step.
 * Normally there should not be any time problems.
 * 
 */
public class HeuristicTreeOneStep extends AHeuristicTree {

	public HeuristicTreeOneStep(Node root, AHeuristic heuristic) {
		super(root);
	}

	public void expand(ActionTimer timer, AHeuristic heuristic) {

		double bestHeuristic = Double.NEGATIVE_INFINITY;

		LinkedList<Node> children = root.getChildren();

		// check whether there is time and we've further tree nodes
		for (Node child : children) {
			heuristic.addScore(child);

			double score = heuristic.evaluateState(child.stateObs);
			if (score > bestHeuristic) {
				bestHeuristic = score;
				bestAction = child.rootAction;
			}
			timer.addIteration();
		}

	}

}
