package emergence_HR.tree;

import ontology.Types;

/**
 * This is an abstract tree that has several attributes that should be needed by
 * every class that inherits from this one.
 * 
 * There are abstract method that has to be implement at children classes: -
 * expand: strategy to expand the tree -> oneStepAhead, levelOrder,
 * bestHeuristic, astar - action: returns always the action that is currently
 * the best for this simulation
 * 
 */
abstract public class ATree {

	public Node root;

	public ATree(Node root) {
		this.root = root;
		this.root.level = 0;
	}

	/**
	 * returns the action that should be done if we need a decision now!
	 */
	abstract public Types.ACTIONS action();

}
