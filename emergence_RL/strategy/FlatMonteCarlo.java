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
	
	// there three variable are needed for the current iteration
	protected Node currentNode = null;
	
	// counter for check if there starts a new path
	protected int counter = 0;

	
	public FlatMonteCarlo(Tree tree, int nSteps) {
		super(tree);
		this.n = nSteps;
		this.currentNode = tree.root;
	}


	@Override
	public boolean expand() {
		// if we have to start a new path
		if (counter == n) {
			if (currentNode != null && isBest(currentNode)) {
				bestNode = currentNode;
				bestScore = currentNode.getScore();
			}
			currentNode = tree.root;
			counter = 0;
		// just expand random this path
		} else {
			currentNode = currentNode.getRandomChild(rand);
			// check if there is a child
			++counter;
			if (currentNode == null) counter = n;
		}
		return true;
	}

	public Types.ACTIONS act() {
		if (bestNode == null || bestNode.rootAction == null) return Types.ACTIONS.ACTION_NIL;
		return bestNode.rootAction;
	}
	
	


}
