package emergence_RL.uct.backpropagation;

import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public class DicountedBackpropagation implements IBackPropagation{


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
