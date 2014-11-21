package emergence_RL.strategies.uct.backpropagation;

import emergence_RL.strategies.uct.UCTSettings;
import emergence_RL.tree.Node;

public class SampleBackpropagation extends ABackPropagation {

	@Override
	public void backpropagate(UCTSettings s, Node n, double reward) {
		while (n != null) {
			n.visited++;
			n.Q += reward;
			n = n.father;
		}
	}

}
