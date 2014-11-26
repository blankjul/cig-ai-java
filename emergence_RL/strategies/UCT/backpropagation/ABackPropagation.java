package emergence_RL.strategies.UCT.backpropagation;

import emergence_RL.strategies.UCTSearch;
import emergence_RL.tree.Node;

public abstract class ABackPropagation {

	abstract public void backpropagate(UCTSearch search, Node n, double reward);
	
}
