package emergence_RL.strategies.uct.actor;

import ontology.Types;
import ontology.Types.ACTIONS;
import emergence_RL.strategies.uct.UCTSettings;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class HighestReward implements IActor {

	@Override
	public ACTIONS act(UCTSettings s, Tree tree) {
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
