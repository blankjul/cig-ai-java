package emergence_RL.strategy.uct.actor;

import ontology.Types;
import ontology.Types.ACTIONS;
import emergence_RL.strategy.UCTSearch;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class HighestReward implements IActor {

	@Override
	public ACTIONS act(UCTSearch s, Tree tree) {
		Types.ACTIONS a = null;
		double reward = Double.NEGATIVE_INFINITY;

		for (Node child : tree.root.getChildren()) {
			if (child.Q >= reward) {
				reward = child.Q;
				a = child.lastAction;
			}
		}
		return a;
	}

}
