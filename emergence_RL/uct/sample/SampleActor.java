package emergence_RL.uct.sample;

import ontology.Types;
import ontology.Types.ACTIONS;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;
import emergence_RL.uct.UCTSettings;
import emergence_RL.uct.actor.IActor;

public class SampleActor implements IActor {

	@Override
	public ACTIONS act(UCTSettings s, Tree tree) {

		int selected = -1;
		double bestValue = -Double.MAX_VALUE;
		boolean allEqual = true;
		double first = -1;
		
		Node n = tree.root;

		for (int i = 0; i < n.children.length; i++) {

			if (n.children[i] != null) {
				if (first == -1)
					first = n.children[i].visited;
				else if (first != n.children[i].visited) {
					allEqual = false;
				}

				double tieBreaker = s.r.nextDouble() * UCTSettings.epsilon;
				if (n.children[i].visited + tieBreaker > bestValue) {
					bestValue = n.children[i].visited + tieBreaker;
					selected = i;
				}
			}
		}

		if (selected == -1) {
			System.out.println("Unexpected selection!");
			selected = 0;
		} else if (allEqual) {
			// If all are equal, we opt to choose for the one with the best Q.
			selected = -1;
	        bestValue = -Double.MAX_VALUE;

	        for (int i=0; i<n.children.length; i++) {
	            double tieBreaker = s.r.nextDouble() * UCTSettings.epsilon;
	            if(n.children[i] != null && n.children[i].Q + tieBreaker > bestValue) {
	                bestValue = n.children[i].Q + tieBreaker;
	                selected = i;
	            }
	        }

	        if (selected == -1)
	        {
	            System.out.println("Unexpected selection!");
	            selected = 0;
	        }

	
			
		}
		Node result = n.children[selected];
		if (result == null) return Types.ACTIONS.ACTION_NIL;
		return result.lastAction;

	}
	


}
