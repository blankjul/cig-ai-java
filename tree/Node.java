package emergence_HR.tree;

import ontology.Types;
import tools.Vector2d;
import core.game.StateObservation;

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

	// level in the tree
	public int level;
	
	//following attributes are only used by HeuristicTreeAStar
	
	protected Vector2d avatarPosition;

	/**
	 * A tree node is defined by using ONLY the state observation
	 * 
	 * @param stateObs
	 *            observation of this node!
	 */
	public Node(StateObservation stateObs) {
		this.father = null;
		this.stateObs = stateObs;
		this.avatarPosition = stateObs.getAvatarPosition();
	}


	public String toString() {
		Vector2d pos = stateObs.getAvatarPosition();
		return String.format("me:[%s,%s] | root:%s | last:%s | level:%s",
				pos.x, pos.y, rootAction, lastAction, level);
	}

	public StateObservation getObservation() {
		return stateObs;
	}

	public Types.ACTIONS getRootAction() {
		return rootAction;
	}

	public String hash() {
		Vector2d pos = stateObs.getAvatarPosition();
		return String.format("[%s,%s]", pos.x, pos.y);
	}

	//this method is used by HeuristicTreeAStar to check wheather a Node
	//(Position of the Avatar) is already in the open List
	public boolean equals(Node other){
		if(this.avatarPosition.equals(other)){
			return true;
		}
		return false;
	}
}
