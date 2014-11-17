package emergence_RL.uct.treePolicy;

import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public abstract class ATreePolicy {

	public Node treePolicy(UCTSettings s,Node n) {
		while (!n.stateObs.isGameOver() && n.level <= s.maxDepth) {
			if (!n.isFullyExpanded()) {
				return n.getRandomChild(s.r, true);
			} else {
				n = bestChild(s,n, s.c);
			}
		}
		return n;
	}
	
	abstract public Node bestChild(UCTSettings s, Node n, double c);
		

}
