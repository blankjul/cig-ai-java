package emergence_RL.strategies.UCT.treePolicy;

import ontology.Types.WINNER;
import core.game.StateObservation;
import emergence_RL.strategies.UCTSearch;
import emergence_RL.tree.Node;

public abstract class ATreePolicy {
	

	abstract public Node bestChild(UCTSearch search, Node n, double c);
	
	
	
	public Node expand(UCTSearch search, Node n) {
		while (!n.stateObs.isGameOver() && n.level <= search.maxDepth) {
			if (!n.isFullyExpanded()) {
				Node child = n.getRandomChild(UCTSearch.r, true);
				if (child.level == 1 && search.pessimisticIterations > 0)
					pessimisticExploring(child, search.pessimisticIterations);
				return child;
			} else {
				n = bestChild(search, n, search.c);
			}
		}
		
		return n;
	}
	

	/**
	 * Pessimistic iteration of the next possible move from the root.
	 */
	protected void pessimisticExploring(Node n, int iteration) {
		StateObservation fatherObs = n.father.stateObs;
		for (int i = 0; i < iteration; i++) {
			StateObservation stateObs = fatherObs.copy();
			stateObs.advance(n.lastAction);
			if (stateObs.getGameWinner() == WINNER.PLAYER_LOSES) {
				n.stateObs = stateObs;
				break;
			}
		}
	}
}
