package emergence_HR.tree;

import java.util.LinkedList;

import ontology.Types;
import core.game.StateObservation;
import emergence_HR.ActionTimer;
import emergence_HR.heuristics.StateHeuristic;

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

	public StateHeuristic heuristic;

	private double score;

	public ATree(Node root) {
		this.root = root;
		this.root.level = 0;
		this.score = 0;
	}

	/**
	 * expand the tree until the timer says there is no time left. there could
	 * be implemented different methods!
	 */
	abstract public void expand(ActionTimer timer);

	/**
	 * returns the action that should be done if we need a decision now!
	 */
	abstract public Types.ACTIONS action();

	/**
	 * Get the score from the current tree that uses the given stratagy
	 */
	public double getScore() {
		return score;
	}

	/**
	 * Create a list of all possible children that could be created from this
	 * state.
	 * 
	 * @param node
	 *            node that should be expanded
	 * @return list of all possible children states
	 */
	public LinkedList<Node> getChildren(Node node) {

		// state observation from the father
		StateObservation stateObs = node.stateObs;

		// create result list and reserve memory for the temporary state object
		LinkedList<Node> nodes = new LinkedList<Node>();
		StateObservation tmpStateObs;

		// for each possible action
		for (Types.ACTIONS action : stateObs.getAvailableActions()) {
			// create the next state
			tmpStateObs = stateObs.copy();
			tmpStateObs.advance(action);

			Node child = new Node(tmpStateObs);
			// set the correct action from the root. if it's the root set action
			// else just inherit
			child.rootAction = (node.father == null) ? action : node.rootAction;
			child.father = node;
			child.lastAction = action;
			child.level = node.level + 1;
			
			nodes.add(child);
		}
		tmpStateObs = null;
		return nodes;
	}

}
