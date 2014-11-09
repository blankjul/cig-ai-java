package emergence_HR;

import java.util.Comparator;


/**
 * The comparator that is needed to find the node with the highest heuristic at
 * the priority queue.
 */
public class NodeComparator implements Comparator<TreeNode> {
	
	public int compare(TreeNode firstNode, TreeNode secondNode) {
		if (firstNode.score > secondNode.score) {
			return 1;
		} else if (firstNode.score < secondNode.score) {
			return -1;
		} else {
			return 0;
		}
		
	}
}