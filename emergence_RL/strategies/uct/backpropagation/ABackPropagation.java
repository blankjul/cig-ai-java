package emergence_RL.strategies.uct.backpropagation;

import emergence_RL.strategies.uct.UCTSettings;
import emergence_RL.tree.Node;

public abstract class ABackPropagation {

	abstract public void backpropagate(UCTSettings s, Node n, double reward);
	
}
