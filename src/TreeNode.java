package controllers.cig;

import java.util.LinkedList;

import ontology.Types;
import core.game.StateObservation;

/**
 * This class represents a TreeNode. Important is that there is the possibility
 * to simulate next steps of a node. This has the consequence that there are
 * always father and children states.
 */
public class TreeNode {

	private StateObservation fatherStateObs;

	private Types.ACTIONS nextAction;

	


	private StateObservation myStateObs = null;

	public TreeNode(StateObservation so, Types.ACTIONS action) {
		this.fatherStateObs = so;
		this.nextAction = action;
	}

	/**
	 * Create a list of all possible children that could be created from this
	 * state. There is no copy of the last state for wasting no time. This
	 * should be done later when we simulate the next step!
	 * 
	 * @return list of all possible children states
	 */
	public LinkedList<TreeNode> getChildren() {
		LinkedList<TreeNode> nodes = new LinkedList<TreeNode>();
		for (Types.ACTIONS action : fatherStateObs.getAvailableActions()) {
			nodes.add(new TreeNode(fatherStateObs, action));
		}
		return nodes;
	}

	
	/**
	 * This method is used to calculate the next state observation lazy.
	 * That means only if we need actually the state of this node after doing the action
	 * there is done a copy and called the advance method.
	 * @return StateObservation after executing nextAction.
	 */
	public StateObservation getObservation() {
		if (myStateObs == null) {
			myStateObs = fatherStateObs.copy();
			myStateObs.advance(nextAction);
			return myStateObs;
		}
		return myStateObs;
	}
	
	
	/**
	 * Just a getter method.
	 * @return nextAction that should be executed.
	 */
	public Types.ACTIONS getNextAction() {
		return nextAction;
	}
	
	

}
