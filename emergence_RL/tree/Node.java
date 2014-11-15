package emergence_RL.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ontology.Types;
import ontology.Types.WINNER;
import tools.Vector2d;
import core.game.StateObservation;
import emergence_RL.helper.ActionMap;

/**
 * This class represents a TreeNode. Important is that there is the possibility
 * to simulate next steps of a node. This has the consequence that there are
 * always father and children states.
 */
public class Node  {

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

	// the Q value that is needed for the MCTS
	public double Q;

	// storage for the utc value!
	public double uct;

	// counts how often any expandation function was executed!
	public int visited;

	// array of children if there were expanded
	protected Node[] children;

	// map for actions to integer and a round
	protected ActionMap map;


	/**
	 * A tree node is defined by using ONLY the state observation
	 * 
	 * @param stateObs
	 *            observation of this node!
	 */
	public Node(StateObservation stateObs) {
		this.father = null;
		this.stateObs = stateObs;
		this.map = ActionMap.create(stateObs.getAvailableActions());
		this.children = new Node[map.NUM_ACTIONS];
		this.Q = 0;
		this.level = 0;
	}

	/**
	 * Return a random child in this tree.
	 * 
	 * @param r
	 * @return null if there are no available actions else the random child!
	 */
	public Node getRandomChild(Random r, boolean mustBeNew) {

		Types.ACTIONS a = null;

		int size = stateObs.getAvailableActions().size();
		if (size == 0)
			return null;

		// get a random child
		if (!mustBeNew) {
			a = getRandomAction(r);

			// get a random child that is not expanded yet!
		} else {
			ArrayList<Types.ACTIONS> posActions = new ArrayList<Types.ACTIONS>();
			for (int i = 0; i < children.length; i++) {
				if (children[i] == null)
					posActions.add(map.getAction(i));
			}
			int index = r.nextInt(posActions.size());
			a = posActions.get(index);
		}

		Node child = getChild(a, true);

		return child;
	}

	public Types.ACTIONS getRandomAction(Random r) {
		int size = stateObs.getAvailableActions().size();
		int index = r.nextInt(size);
		Types.ACTIONS a = stateObs.getAvailableActions().get(index);
		return a;
	}

	/**
	 * Create one child if the action a is used!
	 * 
	 * @param a
	 * @return
	 */
	public Node getChild(Types.ACTIONS a) {

		// copy the state
		StateObservation tmpStateObs = this.stateObs.copy();
		tmpStateObs.advance(a);

		// create the node and set the correct values
		Node child = new Node(tmpStateObs);
		child.rootAction = (this.father == null) ? a : this.rootAction;
		child.father = this;
		child.lastAction = a;
		child.level = this.level + 1;

		// set the child that it is not expanded again!
		int index = map.getInt(a);
		children[index] = child;

		return child;
	}

	/**
	 * Create one child if the action a is used but first looks if it was
	 * created before!
	 * 
	 * @param a
	 * @return
	 */
	public Node getChild(Types.ACTIONS a, boolean useCache) {
		int index = map.getInt(a);
		if (children[index] != null)
			return children[index];
		else
			return getChild(a);

	}

	/**
	 * Create a list of all possible children that could be created from this
	 * state.
	 * 
	 * @param node
	 *            node that should be expanded
	 * @return list of all possible children states
	 */
	public List<Node> getChildren() {

		// if children are cached use them
		if (isFullyExpanded())
			return Arrays.asList(children);

		ArrayList<Types.ACTIONS> actionList = stateObs.getAvailableActions();

		// for each possible action
		for (int i = 0; i < actionList.size(); i++) {

			// action that has to be performed
			Types.ACTIONS a = actionList.get(i);
			getChild(a, true);

		}

		return Arrays.asList(children);
	}

	/**
	 * Return whether this node was simulated for all the actions!
	 * 
	 * @return fully expanded or not!
	 */
	public boolean isFullyExpanded() {
		for (int i = 0; i < children.length; i++) {
			if (children[i] == null)
				return false;
		}
		return true;
	}

	public String hash() {
		Vector2d pos = stateObs.getAvatarPosition();
		String used = (lastAction == Types.ACTIONS.ACTION_USE) ? "y" : "n";
		return String.format("[%s,%s,%s]", pos.x, pos.y, used);
	}



	@Override
	public String toString() {
		Vector2d pos = stateObs.getAvatarPosition();
		String s =  String.format("me:[%s,%s] | root:%s | last:%s | level:%s | Q:%s | visited:%s | utc:%s | fE:%s | children:[",
						pos.x, pos.y, rootAction, lastAction, level,
						 Q, visited, uct, isFullyExpanded());
		for (int i = 0; i < children.length; i++) {
			if (children[i] == null) s += "_,";
			else s += "x,";
		}
		s += "]";
		return s;
	}

	public static double getScore(StateObservation stateObs) {
		if (stateObs.getGameWinner() == WINNER.PLAYER_WINS)
			return 100;
		else if (stateObs.getGameWinner() == WINNER.PLAYER_LOSES)
			return -100;
		else
			return stateObs.getGameScore();
	}

}
