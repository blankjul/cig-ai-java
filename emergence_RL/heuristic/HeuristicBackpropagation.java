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
		    if (i >= 0 && i < s.heuristic.reward.size()) {
		    	double value = s.heuristic.reward.get(i);
		    	s.heuristic.reward.set(i, value + reward);
		    	
		    	double weight = s.heuristic.weights.get(i);
		    	if (weight <= 0) s.heuristic.weights.set(i, 0d);
		    	else if (reward > 0) {
			    	s.heuristic.weights.set(i, weight);
		    	} else if (reward < 0 ) {
		    		s.heuristic.weights.set(i, weight);
		    	} 
		    }
			
		    
			// use a discount factor for the as a weight!
			reward *= s.gamma;
			n = n.father;
		}
		
	}
	
	


}
