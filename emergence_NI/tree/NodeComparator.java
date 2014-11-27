package emergence_NI.tree;

import java.util.Comparator;

/**
 * The comparator that is needed to find the node with the highest heuristic at
 * the priority queue.
 * 
 */
public class NodeComparator implements Comparator<Node> {
	
	public int compare(Node firstNode, Node secondNode) {
		if (firstNode.score < secondNode.score) {
			return 1;
		} else if (firstNode.score > secondNode.score) {
			return -1;
		} else {
			return 0;
		}
	}
	
}