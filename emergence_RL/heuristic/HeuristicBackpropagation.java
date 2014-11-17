package emergence_RL.heuristic;

import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;
import emergence_RL.uct.backpropagation.ABackPropagation;

public class HeuristicBackpropagation extends ABackPropagation{


	@Override
	public void backpropagate(UCTSettings s, Node n, double reward) {
		
		while (n != null) {
			// now we visited the node
			++n.visited;
			n.Q += reward;
			int i = n.targetHeuristicIndex;
			if (i  >= 0 && i < TargetHeuristic.reward.length) {
				TargetHeuristic.reward[i] += reward;
			}
			
			// use a discount factor for the as a weight!
			reward *= s.gamma;
			n = n.father;
		}
		
	}
	
	


}
