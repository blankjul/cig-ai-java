package emergence_RL.uct.backpropagation;

import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public interface IBackPropagation {

	public void backpropagate(UCTSettings s, Node n, double reward);
	
}
