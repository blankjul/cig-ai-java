package emergence_RL.strategy;

import ontology.Types;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

/**
 * This is also an abstract class that adds the feature of a heuristic to the
 * game tree. Every game node gets a heuristic value that should be used to find
 * a good solution and iterate through the tree!
 */
abstract public class AStrategy {

	// game tree that should be expanded by this class
	public Tree tree;

	// best current node in the tree. this is important for extracting the path.
	public Node bestNode = null;

	// best score that has reached with this heuristic
	public double bestScore = Double.NEGATIVE_INFINITY;
	

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
	 * Returns true if the given node n has a higher heuristicScore than 
	 * the actually best node
	 * @param n
	 * @return
	 */
	protected boolean isBest(Node n) {
		return n.heuristicScore > bestScore;
	}
	
	/**
	 * Return the action that the agent should actually do!
	 * @return
	 */
	abstract public Types.ACTIONS act();
	
	
	/**
	 * Expand the tree given to that heuristic.
	 */
	abstract public boolean expand();

}