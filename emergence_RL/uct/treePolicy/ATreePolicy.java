package emergence_RL.uct.treePolicy;

import ontology.Types.WINNER;
import core.game.StateObservation;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public abstract class ATreePolicy {

	final int MAX_PESSIMISTIC_ITERATION = 4;
	
	public Node treePolicy(UCTSettings s,Node n) {
		
		
		while (!n.stateObs.isGameOver() && n.level <= s.maxDepth) {
			if (!n.isFullyExpanded()) {
				Node child = n.getRandomChild(s.r, true);
				return child;
			} else {
				pessimisticExploring(n);
				n = bestChild(s,n, s.c);
			}
		}
		return n;
	}
	
	abstract public Node bestChild(UCTSettings s, Node n, double c);
		
	/**
	 * Pessimistic iteration of the next possible move from the root.
	 * @param n
	 */
	private void pessimisticExploring(Node n) {
		if (n.level == 1) {
			StateObservation fatherObs = n.father.stateObs;
			for (int i = 0; i < MAX_PESSIMISTIC_ITERATION; i++) {
				StateObservation stateObs = fatherObs.copy();
				stateObs.advance(n.lastAction);
				if (stateObs.getGameWinner() == WINNER.PLAYER_LOSES) {
					n.stateObs = stateObs;
					break;
				}
			}
		}
	}
}
