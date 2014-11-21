package emergence_RL.strategies.uct.treePolicy;

import ontology.Types.WINNER;
import core.game.StateObservation;
import emergence_RL.strategies.uct.UCTSettings;
import emergence_RL.tree.Node;

public abstract class ATreePolicy {
	

	public Node treePolicy(UCTSettings s, Node n) {
		while (!n.stateObs.isGameOver() && n.level <= s.maxDepth) {
			if (!n.isFullyExpanded()) {
				Node child = n.getRandomChild(s.r, true);
				
				if (child.level == 1 && s.pessimisticIterations > 0)
					pessimisticExploring(child, s.pessimisticIterations);
				
				return child;
			} else {
				n = bestChild(s, n, s.c);
			}
		}
		return n;
	}

	abstract public Node bestChild(UCTSettings s, Node n, double c);

	
	
	/**
	 * Pessimistic iteration of the next possible move from the root.
	 */
	private void pessimisticExploring(Node n, int iteration) {
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
