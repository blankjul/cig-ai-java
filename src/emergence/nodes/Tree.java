package emergence.nodes;

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
public class Tree<T> {

	
	public GenericNode<T> root;
	
	public Tree(GenericNode<T> root) {
		this.root = root;
	}
	
	
}
