package emergence_RL.uct.actor;

import ontology.Types;
import ontology.Types.ACTIONS;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;
import emergence_RL.uct.UCTSettings;

public class HighestUCT implements IActor {

	@Override
	public ACTIONS act(UCTSettings s, Tree tree) {
		
		s.treePolicy.bestChild(s, tree.root,0);
		
		Types.ACTIONS a = null;
		double bestUTC = Double.NEGATIVE_INFINITY;

		for (Node child : tree.root.getChildren()) {
			if (child.uct >= bestUTC) {
				bestUTC = child.uct;
				a = child.lastAction;
			}
		}
		return a;
	}

}
