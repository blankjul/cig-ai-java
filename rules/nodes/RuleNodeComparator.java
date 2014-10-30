package emergence_HR.rules.nodes;

import java.util.Comparator;

/**
 * The comparator that is needed to find the node with the highest heuristic
 * at the priority queue.
 * 
 */
public class RuleNodeComparator implements Comparator<RuleNode> {
	public int compare(RuleNode first, RuleNode second) {
		if (first.getHeuristic() < second.getHeuristic()) {
			return -1;
		} else if (first.getHeuristic() > second.getHeuristic()) {
			return 1;
		} else {
			return 0;
		}
	}
}