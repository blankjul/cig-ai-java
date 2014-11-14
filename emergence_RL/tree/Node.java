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
public class Node implements Comparable<Node> {

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

	// just the score of a node. normally heuristic based!
	public double heuristicScore;

	// the Q value that is needed for the MCTS
	public double Q;

	// storage for the utc value!
	public double utcValue;

	// counts how often any expandation function was executed!
	public int visited;

	// array of children if there were expanded
	protected Node[] children;

	// map for actions to integer and a round
	protected ActionMap map;

	// boolean value to check if this node is fully expanded!
	protected boolean isFullyExpanded;

	// counts how often this node was randomly expanded.
	// if it's lower than the available actions is could not be
	// fully expanded. --> fast check
	protected int randomExpandedCounter = 0;

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
		this.isFullyExpanded = false;
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
			int index = r.nextInt(size);
			a = stateObs.getAvailableActions().get(index);

			// get a random child that is not expanded yet!
		} else {
			ArrayList<Integer> posActions = new ArrayList<Integer>();
			for (int i = 0; i < children.length; i++) {
				if (children[i] == null)
					posActions.add(i);
			}
			int index = r.nextInt(posActions.size());
			a = map.getAction(index);
		}

		Node child = getChild(a, true);

		// check if it is fully expanded by using first of all the counter!
		++randomExpandedCounter;
		setFullyExpanded();

		return child;
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
		child.heuristicScore = getScore();

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
		if (isFullyExpanded)
			return Arrays.asList(children);

		ArrayList<Types.ACTIONS> actionList = stateObs.getAvailableActions();

		// for each possible action
		for (int i = 0; i < actionList.size(); i++) {

			// action that has to be performed
			Types.ACTIONS a = actionList.get(i);
			getChild(a, true);

		}

		isFullyExpanded = true;

		return Arrays.asList(children);
	}

	/**
	 * Return whether this node was simulated for all the actions!
	 * 
	 * @return fully expanded or not!
	 */
	public boolean isFullyExpanded() {
		return this.isFullyExpanded;
	}

	private void setFullyExpanded() {
		if (randomExpandedCounter >= stateObs.getAvailableActions().size()) {
			for (int i = 0; i < children.length; i++) {
				if (children[i] == null) {
					isFullyExpanded = false;
					return;
				}
			}
			isFullyExpanded = true;
			return;
		}
	}

	public String hash() {
		Vector2d pos = stateObs.getAvatarPosition();
		String used = (lastAction == Types.ACTIONS.ACTION_USE) ? "y" : "n";
		return String.format("[%s,%s,%s]", pos.x, pos.y, used);
	}

	@Override
	public int compareTo(Node o) {
		if (this.heuristicScore < o.heuristicScore) {
			return 1;
		} else if (this.heuristicScore > o.heuristicScore) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		Vector2d pos = stateObs.getAvatarPosition();
		return String.format("me:[%s,%s] | root:%s | last:%s | level:%s | score:%s | Q:%s | visited:%s | utc:%s",
				pos.x, pos.y, rootAction, lastAction, level, heuristicScore, Q, visited, utcValue);
	}

	private double getScore() {
		if (stateObs.getGameWinner() == WINNER.PLAYER_WINS)
			return Double.POSITIVE_INFINITY;
		else if (stateObs.getGameWinner() == WINNER.PLAYER_LOSES)
			return Double.NEGATIVE_INFINITY;
		else
			return stateObs.getGameScore();
	}

}
