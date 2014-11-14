package emergence_RL.tree;

import java.util.LinkedList;
import java.util.Random;

import ontology.Types;
import ontology.Types.WINNER;
import tools.Vector2d;
import core.game.StateObservation;

/**
 * This class represents a TreeNode. Important is that there is the possibility
 * to simulate next steps of a node. This has the consequence that there are
 * always father and children states.
 */
public class Node implements Comparable<Node>{

	// father node, if null it's the root
	public Node father;

	// state observation. if it's ones advanced we need not to do it again!
	public StateObservation stateObs;

	// this is the action of the root node that brings us to this tree node
	// by using this we need no traversal to the root again!
	public Types.ACTIONS rootAction;

	// always the last action
	public Types.ACTIONS lastAction;

	// it's static to get it fast
	public int level;

	
	// array of children if there were expanded
	protected LinkedList<Node> children;
	



	/**
	 * A tree node is defined by using ONLY the state observation
	 * 
	 * @param stateObs
	 *            observation of this node!
	 */
	public Node(StateObservation stateObs) {
		this.father = null;
		this.stateObs = stateObs;
		this.children = new LinkedList<Node>();
	}

	
	
	/**
	 * Return a random child in this tree.
	 * @param r
	 * @return null if there are no available actions else the random child!
	 */
	public Node getRandomChild(Random r) {
		int size = stateObs.getAvailableActions().size();
		if (size == 0) return null;
		
		int index = r.nextInt(size);
		Types.ACTIONS a = stateObs.getAvailableActions().get(index);
		StateObservation tmpStateObs = stateObs.copy();
		tmpStateObs.advance(a);
		Node child = new Node(tmpStateObs);
		child.rootAction = (this.father == null) ? a : this.rootAction;
		child.father = this;
		child.lastAction = a;
		child.level = this.level + 1;
		return child;
	}
	
	/**
	 * Create a list of all possible children that could be created from this
	 * state.
	 * 
	 * @param node
	 *            node that should be expanded
	 * @return list of all possible children states
	 */
	public LinkedList<Node> getChildren() {

		// if children are cached use them
		if (stateObs.getAvailableActions().size() ==  children.size())
			return children;

		// state observation from the father
		StateObservation stateObs = this.stateObs;
		// create result list and reserve memory for the temporary state object
		StateObservation tmpStateObs;

		// for each possible action
		for (Types.ACTIONS action : stateObs.getAvailableActions()) {
			// create the next state
			tmpStateObs = stateObs.copy();
			tmpStateObs.advance(action);

			Node child = new Node(tmpStateObs);
			// set the correct action from the root. if it's the root set action
			// else just inherit
			child.rootAction = (this.father == null) ? action : this.rootAction;
			child.father = this;
			child.lastAction = action;
			child.level = this.level + 1;

			children.add(child);
		}
		tmpStateObs = null;

		return children;
	}

	public String toString() {
		Vector2d pos = stateObs.getAvatarPosition();
		return String.format(
				"me:[%s,%s] | root:%s | last:%s | level:%s ", pos.x,
				pos.y, rootAction, lastAction, level);
	}

	public String hash() {
		Vector2d pos = stateObs.getAvatarPosition();
		String used = (lastAction == Types.ACTIONS.ACTION_USE) ? "y" : "n";
		return String.format("[%s,%s,%s]", pos.x, pos.y, used);
	}


	@Override
	public int compareTo(Node o) {
		if (this.getScore() < o.getScore()) {
			return 1;
		} else if (this.getScore() > o.getScore()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	
	public double getScore() {
		if (stateObs.getGameWinner() == WINNER.PLAYER_WINS) return Double.POSITIVE_INFINITY;
		else if (stateObs.getGameWinner() == WINNER.PLAYER_LOSES) return Double.NEGATIVE_INFINITY;
		else return stateObs.getGameScore();
	}


	

}
