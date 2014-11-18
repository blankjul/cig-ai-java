package emergence_RL.uct.backpropagation;

import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;


/**
 * This class is just a simple back propagation that
 * allows to set a discount factor gamma.
 * if gamma is equal to one it is just normal without 
 * any discounting!
 */
public class Backpropagation extends ABackPropagation{


	@Override
	public void backpropagate(UCTSettings s, Node n, double reward) {
		
		while (n != null) {
			// now we visited the node
			++n.visited;
			n.Q += reward;
			// use a discount factor for the as a weight!
			reward *= s.gamma;
			n = n.father;
		}
		
	}
	
	


}
