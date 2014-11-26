package emergence_RL.strategies;

import ontology.Types;
import emergence_RL.tree.Tree;

/**
 * This is also an abstract class that adds the feature of a heuristic to the
 * game tree. Every game node gets a heuristic value that should be used to find
 * a good solution and iterate through the tree!
 */
abstract public class AStrategy {

	// game tree that should be expanded by this class
	public Tree tree;

	
	public AStrategy() {
	}
	
	
	/**
	 * Create a game tree that uses a heuristic for iteration.
	 * 
	 * @param root
	 * @param heuristic
	 */
	public AStrategy(Tree tree) {
		this.tree = tree;
	}
	

	
	/**
	 * Expand the tree given to that heuristic.
	 */
	abstract public boolean expand();
	
	abstract public Types.ACTIONS act();
	

	
	


}
