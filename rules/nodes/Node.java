package emergence_HR.rules.nodes;

import java.util.LinkedList;

import ontology.Types;
import tools.Vector2d;
import core.game.StateObservation;

/**
 * This class represents a TreeNode. Important is that there is the possibility
 * to simulate next steps of a node. This has the consequence that there are
 * always father and children states.
 */
public abstract class Node {

	// father node, if null it's the root
	public Node father;

	// state observation. if it's ones advanced we need not to do it again!
	public StateObservation stateObs;

	/**
	 * A tree node is defined by using ONLY the state observation
	 * 
	 * @param stateObs
	 *            observation of this node!
	 */
	public Node(StateObservation stateObs) {
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
	public Node(StateObservation stateObs, Node father) {
		this(stateObs);
		this.father = father;
	}

	/**
	 * Create a list of all possible children that could be created from this
	 * state.
	 * 
	 * @return list of all possible children states
	 */
	public LinkedList<StateObservation> getChildrenStates() {
		// create result list and reserve memory for the temporary state object
		LinkedList<StateObservation> nodes = new LinkedList<StateObservation>();
		StateObservation tmpStateObs;
		// for each possible action
		for (Types.ACTIONS action : stateObs.getAvailableActions()) {
			// create the next state
			tmpStateObs = stateObs.copy();
			tmpStateObs.advance(action);
			nodes.add(tmpStateObs);
		}
		tmpStateObs = null;
		return nodes;
	}

	public boolean equals(Object obj) {
		Node n = (Node) obj;
		Vector2d myPos = stateObs.getAvatarPosition();
		Vector2d destPos = n.stateObs.getAvatarPosition();
		if (myPos.x == destPos.x && myPos.y == destPos.y) {
			return true;
		}
		return false;
	}

	public double getDistance(StateObservation destObs) {
		Vector2d myPos = stateObs.getAvatarPosition();
		Vector2d destPos = destObs.getAvatarPosition();
		return Math.abs(myPos.x - destPos.x) + Math.abs(myPos.y - destPos.y);
	}
	
}
