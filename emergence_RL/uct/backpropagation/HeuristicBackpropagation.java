package emergence_RL.uct.backpropagation;

import emergence_RL.heuristic.TargetHeuristic;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public class HeuristicBackpropagation extends ABackPropagation{


	@Override
	public void backpropagate(UCTSettings s, Node n, double reward) {
		
		while (n != null) {
			// now we visited the node
			++n.visited;
			n.Q += reward;
			if (n.targetHeuristicIndex != -1) TargetHeuristic.rewards[n.targetHeuristicIndex] += reward;
			
			// use a discount factor for the as a weight!
			reward *= s.gamma;
			n = n.father;
		}
		
	}
	
	


}
