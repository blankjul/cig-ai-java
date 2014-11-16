package emergence_RL.uct.backpropagation;

import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

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
