package emergence_HR.nodes;

import java.util.LinkedList;

import ontology.Types;
import tools.Vector2d;
import core.game.StateObservation;
import emergence_HR.heuristics.StateHeuristic;

/**
 * This class represents a TreeNode. Important is that there is the possibility
 * to simulate next steps of a node. This has the consequence that there are
 * always father and children states.
 */
public class Node {

	// father node, if null it's the root
	protected Node father;

	// state observation. if it's ones advanced we need not to do it again!
	protected StateObservation stateObs;

	// this is the action of the root node that brings us to this tree node
	// by using this we need no traversal to the root again!
	protected Types.ACTIONS rootAction;
	
	// always the last action
	protected Types.ACTIONS lastAction;

	// the heuristic that is used for this node
	protected StateHeuristic stateHeuristic;
	
	// level in the tree
	public int level;
	


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
	 * Create a list of all possible children that could be created from this
	 * state.
	 * 
	 * @return list of all possible children states
	 */
	public LinkedList<Node> getChildren() {
		// create result list and reserve memory for the temporary state object
		LinkedList<Node> nodes = new LinkedList<Node>();
		StateObservation tmpStateObs;
		// for each possible action
		for (Types.ACTIONS action : stateObs.getAvailableActions()) {
			// create the next state
			tmpStateObs = stateObs.copy();
			tmpStateObs.advance(action);

			Node n = new Node(tmpStateObs);
			// set the correct action from the root. if it's the root set action
			// else just inherit
			n.rootAction = (this.father == null) ? action : this.rootAction;
			n.father = this;
			n.stateHeuristic = this.stateHeuristic;
			n.lastAction= action; 
			n.level = this.level + 1;
			nodes.add(n);
		}
		tmpStateObs = null;
		return nodes;
	}
	
	public String toString() {
		Vector2d pos = stateObs.getAvatarPosition();
		return String.format("me:[%s,%s] | root:%s | last:%s | level:%s | %s | value: %s", pos.x, pos.y, rootAction, lastAction, level, stateHeuristic.toString(), getHeuristic());
	}


	public StateObservation getObservation() {
		return stateObs;
	}

	public Types.ACTIONS getRootAction() {
		return rootAction;
	}

	
	public double getHeuristic() {
		return stateHeuristic.evaluateState(stateObs);
	}
	
	
	public void setHeuristic(StateHeuristic stateHeuristic) {
		this.stateHeuristic = stateHeuristic;
	}

	public String hash() {
		Vector2d pos = stateObs.getAvatarPosition();
		return String.format("[%s,%s]", pos.x, pos.y);
	}



}
