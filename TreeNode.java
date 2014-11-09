package emergence_HR;

import java.util.LinkedList;

import ontology.Types;
import core.game.StateObservation;

/**
 * This class represents a TreeNode. Important is that there is the possibility
 * to simulate next steps of a node. This has the consequence that there are
 * always father and children states.
 */
public class TreeNode {

	// father node, if null it's the root
	protected TreeNode father;

	// state observation. if it's ones advanced we need not to do it again!
	protected StateObservation stateObs;

	// this is the action of the root node that brings us to this tree node
	// by using this we need no traversal to the root again!
	protected Types.ACTIONS rootAction;
	
	
	public double score = -1;

	/**
	 * A tree node is defined by using ONLY the state observation
	 * 
	 * @param stateObs
	 *            observation of this node!
	 */
	public TreeNode(StateObservation stateObs) {
		this.father = null;
		this.stateObs = stateObs;
	}

	/**
	 * If the node is not a root it's good to know the father. Always use this
	 * constructor!
	 * 
	 * @param father
	 *            father tree node.
	 */
	public TreeNode(StateObservation stateObs, TreeNode father) {
		this(stateObs);
		this.father = father;
	}

	/**
	 * Create a list of all possible children that could be created from this
	 * state.
	 * 
	 * @return list of all possible children states
	 */
	public LinkedList<TreeNode> getChildren() {
		// create result list and reserve memory for the temporary state object
		LinkedList<TreeNode> nodes = new LinkedList<TreeNode>();
		StateObservation tmpStateObs;
		// for each possible action
		for (Types.ACTIONS action : stateObs.getAvailableActions()) {
			// create the next state
			tmpStateObs = stateObs.copy();
			tmpStateObs.advance(action);

			TreeNode n = new TreeNode(tmpStateObs, this);
			// set the correct action from the root. if it's the root set action
			// else just inheritate
			n.rootAction = (this.father == null) ? action : this.rootAction;
			nodes.add(n);
		}
		tmpStateObs = null;
		return nodes;
	}

	public StateObservation getObservation() {
		return stateObs;
	}

	public Types.ACTIONS getRootAction() {
		return rootAction;
	}

}
