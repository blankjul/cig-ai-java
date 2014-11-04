package emergence_HR.tree;

import java.util.Comparator;

import emergence_HR.heuristics.AHeuristic;

/**
 * The comparator that is needed to find the node with the highest heuristic at
 * the priority queue.
 */
public class NodeComparator implements Comparator<Node> {
	
	// heuristic that is applied for the ordering
	private AHeuristic heuristic;
	
	public NodeComparator(AHeuristic heuristic) {
		this.heuristic = heuristic;
	}
	
	public int compare(Node firstNode, Node secondNode) {
		double heurFirst = heuristic.evaluateState(firstNode.stateObs);
		double heurSecond = heuristic.evaluateState(secondNode.stateObs);
		if (heurFirst < heurSecond) {
			return 1;
		} else if (heurFirst > heurSecond) {
			return -1;
		} else {
			return 0;
		}
		
	}
}