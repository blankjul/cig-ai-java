package emergence_HR.strategy;

import emergence_HR.heuristics.AHeuristic;
import emergence_HR.tree.Node;
import emergence_HR.tree.Tree;

/**
 * This is also an abstract class that adds the feature of a heuristic to the
 * game tree. Every game node gets a heuristic value that should be used to find
 * a good solution and iterate through the tree!
 */
abstract public class AStrategy {

	// game tree that should be expanded by this class
	public Tree tree;

	// heuristic that is used for expanding the tree
	public AHeuristic heuristic;
	
	// best current node in the tree. this is important for extracting the path.
	public Node bestNode;

	// best score that has reached with this heuristic
	public double bestScore;
	

	/**
	 * Create a game tree that uses a heuristic for iteration.
	 * 
	 * @param root
	 * @param heuristic
	 */
	public AStrategy(Tree tree, AHeuristic heuristic) {
		this.tree = tree;
		this.heuristic = heuristic;
		bestNode = tree.root;
		bestScore = Double.NEGATIVE_INFINITY;
	}
	
	/**
	 * Checks if the node evaluated with heuristic h is better
	 * than the best saved node.
	 * @param n
	 * @param heuristic
	 */
	public void checkBest(Node n, AHeuristic heuristic) {
		if (n.score > bestScore) {
			bestNode = n;
			bestScore = n.score;
		}
	}
	
	/**
	 * Expand the tree given to that heuristic.
	 */
	abstract public boolean expand();

}
