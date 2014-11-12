package emergence_RL.tree;

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
public class Tree {

	public Node root;

	public Tree(Node root) {
		this.root = root;
		this.root.level = 0;
	}

}
