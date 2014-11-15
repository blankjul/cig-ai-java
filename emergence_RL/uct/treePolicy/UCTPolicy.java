package emergence_RL.uct.treePolicy;

import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public class UCTPolicy extends ATreePolicy {

	// epsilon for the utc formula
	public double epsilon = 1e-6;

	public Node bestChild(UCTSettings s, Node n, double c) {
		double bestUTC = Double.NEGATIVE_INFINITY;
		Node bestNode = null;

		for (Node child : n.getChildren()) {
			child.uct = child.Q
					/ (child.visited + epsilon)
					+ c
					* Math.sqrt(Math.log(n.visited + 1)
							/ (child.visited + epsilon)) + s.r.nextDouble()
					* epsilon;

			if (child.uct >= bestUTC) {
				bestNode = child;
				bestUTC = child.uct;
			}
		}
		return bestNode;
	}

}
