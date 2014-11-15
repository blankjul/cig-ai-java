package emergence_RL.uct.backpropagation;

import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public abstract class ABackPropagation {

	abstract public void backpropagate(UCTSettings s, Node n, double reward);
	
}
