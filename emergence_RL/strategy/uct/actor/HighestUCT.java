package emergence_RL.strategy.uct.actor;

import ontology.Types;
import ontology.Types.ACTIONS;
import emergence_RL.strategy.UCTSearch;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class HighestUCT implements IActor {

	@Override
	public ACTIONS act(UCTSearch s, Tree tree) {
		s.uct(tree.root);
		
		Types.ACTIONS a = null;
		double bestUTC = Double.NEGATIVE_INFINITY;

		for (Node child : tree.root.getChildren()) {
			if (child.utcValue >= bestUTC) {
				bestUTC = child.utcValue;
				a = child.lastAction;
			}
		}
		return a;
	}

}
