package emergence_RL.uct.actor;

import ontology.Types;
import ontology.Types.ACTIONS;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;
import emergence_RL.uct.UCTSettings;

public class MostVisited implements IActor {

	@Override
	public ACTIONS act(UCTSettings s, Tree tree) {
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
