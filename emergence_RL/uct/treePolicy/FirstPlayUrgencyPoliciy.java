package emergence_RL.uct.treePolicy;

import java.util.ArrayList;

import emergence_RL.helper.Helper;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

/**
 * paper page 15, selection enhancements
 * @author spakken
 *
 */
public class FirstPlayUrgencyPoliciy extends ATreePolicy {

	// high-> exploration/normal low -> exploitation
	public double defaultFactor = Double.POSITIVE_INFINITY;

	// epsilon for the utc formula
	public double epsilon = 1e-6;

	@Override
	public Node treePolicy(UCTSettings s, Node n) {
		while (!n.stateObs.isGameOver() && n.level <= s.maxDepth) {
			if (!n.isFullyExpanded()) {
				return bestChildExpand(s, n, s.C);
			} else {
				n = bestChild(s, n, s.C);
			}
		}
		return n;
	}

	/**
	 *best child function from Normal UCTPolicy (just an example
	 *every normal policy can be used here)
	 */
	@Override
	public Node bestChild(UCTSettings s, Node n, double c) {

		double bestUTC = Double.NEGATIVE_INFINITY;
		ArrayList<Node> bestNodes = new ArrayList<Node>();

		Node result = null;

		for (Node child : n.getChildren()) {

			child.uct = child.Q
					/ (child.visited + epsilon)
					+ c
					* Math.sqrt(Math.log(n.visited + 1)
							/ (child.visited + epsilon)) + s.r.nextDouble()
					* epsilon;

			if (child.uct == bestUTC) {
				bestNodes.add(child);
			} else if (child.uct > bestUTC) {
				bestNodes.clear();
				bestNodes.add(child);
				bestUTC = child.uct;
			}
		}
		if (bestNodes.isEmpty())
			return n;
		result = Helper.getRandomEntry(bestNodes, s.r);
		return result;

	}

	public Node bestChildExpand(UCTSettings s, Node n, double c) {

		double bestUTC = defaultFactor;
		ArrayList<Node> bestNodes = new ArrayList<Node>();

		Node result = null;

		Node[] nodes = n.children.clone();

		ArrayList<Node> existingChildren = new ArrayList<Node>();

		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				existingChildren.add(nodes[i]);
			}
		}

		for (Node child : existingChildren) {

			child.uct = child.Q
					/ (child.visited + epsilon)
					+ c
					* Math.sqrt(Math.log(n.visited + 1)
							/ (child.visited + epsilon)) + s.r.nextDouble()
					* epsilon;

			if (child.uct == bestUTC) {
				bestNodes.add(child);
			} else if (child.uct > bestUTC) {
				bestNodes.clear();
				bestNodes.add(child);
				bestUTC = child.uct;
			}
		}

		// no Node is very good, get a new one
		if (bestNodes.isEmpty())
			return n.getRandomChild(s.r, true);

		result = Helper.getRandomEntry(bestNodes, s.r);
		return result;

	}
}
