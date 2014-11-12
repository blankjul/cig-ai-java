package emergence_RL.strategy;

import java.util.Random;

import ontology.Types;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class FlatMonteCarlo extends AStrategy {

	// thats the height of the tree - that is fix
	protected int n;

	// generator for random numbers
	protected Random rand = new Random();

	// there three variable are needed for the current iteration
	protected Node bestNode = null;
	
	
	public FlatMonteCarlo(Tree tree, int nSteps) {
		super(tree);
		this.n = nSteps;
	}


	@Override
	public boolean expand() {
		Node currentNode = null;
		// simulate just n random steps
		for (int i = 0; i < n; i++) {
			if (currentNode == null) break;
			currentNode = currentNode.getRandomChild(rand);
		}
		// look how good it is
		if (currentNode != null && isBest(currentNode)) {
			bestNode = currentNode;
			bestScore = currentNode.getScore();
		}
		return true;
	}

	public Types.ACTIONS act() {
		if (bestNode == null || bestNode.rootAction == null) return Types.ACTIONS.ACTION_NIL;
		return bestNode.rootAction;
	}
	
	


}
