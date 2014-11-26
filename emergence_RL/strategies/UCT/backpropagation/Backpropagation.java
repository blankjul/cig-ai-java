package emergence_RL.strategies.UCT.backpropagation;

import emergence_RL.strategies.UCTSearch;
import emergence_RL.tree.Node;


/**
 * This class is just a simple back propagation that
 * allows to set a discount factor gamma.
 * if gamma is equal to one it is just normal without 
 * any discounting!
 */
public class Backpropagation extends ABackPropagation{


	@Override
	public void backpropagate(UCTSearch search, Node n, double reward) {
		
		while (n != null) {
			// now we visited the node
			++n.visited;
			n.Q += reward;
			// use a discount factor for the as a weight!
			reward *= search.gamma;
			n = n.father;
		}
		
	}
	
	


}
