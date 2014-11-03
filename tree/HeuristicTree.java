package emergence_HR.tree;

import ontology.Types;
import ontology.Types.ACTIONS;
import emergence_HR.heuristics.StateHeuristic;

/**
 * This is also an abstract class that adds the feature of a heuristic to the
 * game tree. Every game node gets a heuristic value that should be used to find
 * a good solution and iterate through the tree!
 */
abstract public class HeuristicTree extends ATree {

	protected StateHeuristic heuristic;

	/**
	 * action that is returned by the action(). It has to be PROOVED that the
	 * expand method sets the actions after each call. otherwise it is always
	 * return NIL.
	 */
	Types.ACTIONS action = Types.ACTIONS.ACTION_NIL;

	/**
	 * Create a game tree that uses a heuristic for iteration.
	 * 
	 * @param root
	 * @param heuristic
	 */
	public HeuristicTree(Node root, StateHeuristic heuristic) {
		super(root);
		this.heuristic = heuristic;
	}

	/**
	 * @return heuristic that is used
	 */
	public StateHeuristic getHeuristic() {
		return heuristic;
	}

	/**
	 * sets the heuristic for this tree search!
	 * 
	 * @param heuristic
	 */
	public void setHeuristic(StateHeuristic heuristic) {
		this.heuristic = heuristic;
	}

	/**
	 * return the attribute action. for heuristic it is the root action with the
	 * highest heuristic.
	 */
	@Override
	public ACTIONS action() {
		return action;
	}

}
