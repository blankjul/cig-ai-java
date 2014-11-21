package emergence_RL.strategies.uct.actor;

import java.util.ArrayList;

import ontology.Types;
import ontology.Types.ACTIONS;
import ontology.Types.WINNER;
import emergence_RL.helper.Helper;
import emergence_RL.strategies.uct.UCTSettings;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class MostVisited implements IActor {

	@Override
	public ACTIONS act(UCTSettings s, Tree tree) {
		
		ArrayList<Types.ACTIONS> bestActions = new ArrayList<Types.ACTIONS>();
		
		Types.ACTIONS a = null;
		int mostVisited = -1;

		for (Node child : tree.root.getChildren()) {
			
			// if loose or no new position!
			if (child.stateObs.getGameWinner() == WINNER.PLAYER_LOSES) continue;
			
			if (child.visited == mostVisited) {
				bestActions.add(child.lastAction);
			}
			else if (child.visited > mostVisited) {
				bestActions.clear();
				bestActions.add(child.lastAction);
				mostVisited = child.visited;
			}
		}
		if (bestActions.size() == 0) return Types.ACTIONS.ACTION_NIL;
		a = Helper.getRandomEntry(bestActions, s.r);
		return a;

	}

}
