package emergence_RL.strategies.UCT.actor;

import java.util.ArrayList;

import ontology.Types.WINNER;
import emergence_RL.helper.Helper;
import emergence_RL.strategies.UCTSearch;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class MostVisitedAdvanced implements IActor {

	@Override
	public Node act(UCTSearch search, Tree tree) {

		ArrayList<Node> bestNodes = new ArrayList<Node>();

		int mostVisited = -1;

		for (Node child : tree.root.getChildren()) {

			// if loose or no new position!
			if (child.stateObs.getGameWinner() == WINNER.PLAYER_LOSES)
				continue;
			else if (child.stateObs.getGameScore() < tree.root.stateObs
					.getGameScore())
				continue;

			if (child.visited == mostVisited) {
				bestNodes.add(child);
			} else if (child.visited > mostVisited) {
				bestNodes.clear();
				bestNodes.add(child);
				mostVisited = child.visited;
			}
		}

		if (bestNodes.size() == 0)
			return null;
		Node n = Helper.getRandomEntry(bestNodes, UCTSearch.r);
		return n;

	}

}
