package emergence_HR.rules.nodes;

import java.util.LinkedList;

import ontology.Types;
import core.game.StateObservation;

/**
 * This class represents a TreeNode. Important is that there is the possibility
 * to simulate next steps of a node. This has the consequence that there are
 * always father and children states.
 */
public class StateNode extends Node {

	// this is the action of the root node that brings us to this tree node
	// by using this we need no traversal to the root again!
	protected Types.ACTIONS rootAction;

	public StateNode(StateObservation stateObs) {
		super(stateObs);
	}

	public StateNode(StateObservation stateObs, Node father) {
		super(stateObs, father);
	}

	/**
	 * Create a list of all possible children that could be created from this
	 * state.
	 * 
	 * @return list of all possible children states
	 */
	public LinkedList<StateNode> getChildren() {
		// create result list and reserve memory for the temporary state object
		LinkedList<StateNode> nodes = new LinkedList<StateNode>();
		StateObservation tmpStateObs;
		// for each possible action
		for (Types.ACTIONS action : stateObs.getAvailableActions()) {
			// create the next state
			tmpStateObs = stateObs.copy();
			tmpStateObs.advance(action);

			StateNode n = new StateNode(tmpStateObs, this);
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
