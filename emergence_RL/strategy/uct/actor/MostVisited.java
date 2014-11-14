package emergence_RL.strategy.uct.actor;

import ontology.Types;
import ontology.Types.ACTIONS;
import emergence_RL.strategy.UCTSearch;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class MostVisited implements IActor {

	@Override
	public ACTIONS act(UCTSearch s, Tree tree) {
		Types.ACTIONS a = null;
		int mostVisited = -1;

		for (Node child : tree.root.getChildren()) {
			if (child.visited > mostVisited) {
				mostVisited = child.visited;
				a = child.lastAction;
			}
		}
		return a;
	}

}
