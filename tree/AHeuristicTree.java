package emergence_HR.tree;

import ontology.Types;
import ontology.Types.ACTIONS;
import emergence_HR.ActionTimer;
import emergence_HR.heuristics.AHeuristic;

/**
 * This is also an abstract class that adds the feature of a heuristic to the
 * game tree. Every game node gets a heuristic value that should be used to find
 * a good solution and iterate through the tree!
 */
abstract public class AHeuristicTree extends ATree {

	/**
	 * action that is returned by the action(). It has to be PROOVED that the
	 * expand method sets the actions after each call. otherwise it is always
	 * return NIL.
	 */
	Types.ACTIONS bestAction = Types.ACTIONS.ACTION_NIL;
	
	/**
	 * best current node in the tree. this is important for extracting the path.
	 */
	public Node bestNode;

	/**
	 * Expand the tree given to that heuristic.
	 * 
	 * @param heuristic
	 */
	abstract public void expand(ActionTimer timer, AHeuristic heuristic);

	/**
	 * Create a game tree that uses a heuristic for iteration.
	 * 
	 * @param root
	 * @param heuristic
	 */
	public AHeuristicTree(Node root) {
		super(root);
		bestNode = root;
	}

	/**
	 * return the attribute action. for heuristic it is the root action with the
	 * highest heuristic.
	 */
	@Override
	public ACTIONS action() {
		return bestAction;
	}

}
