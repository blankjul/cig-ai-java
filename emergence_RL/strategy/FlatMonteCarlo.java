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


	public FlatMonteCarlo(Tree tree, int nSteps) {
		super(tree);
		this.n = nSteps;
	}


	@Override
	public boolean expand() {
		
		// set currentNode as the root
		Node currentNode = tree.root;
		
		// simulate just n random steps
		for (int i = 0; i < n; i++) {
			if (currentNode == null) break;
			currentNode = currentNode.getRandomChild(rand, false);
		}
		
		// look how good it is
		if (currentNode != null && isBest(currentNode)) {
			bestNode = currentNode;
			bestScore = currentNode.heuristicScore;
		}
		return true;
	}

	public Types.ACTIONS act() {
		if (bestNode == null || bestNode.rootAction == null) return Types.ACTIONS.ACTION_NIL;
		return bestNode.rootAction;
	}
	
	


}
