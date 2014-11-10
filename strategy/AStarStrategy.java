package emergence_HR.strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

import ontology.Types;
import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;
import emergence_HR.ActionTimer;
import emergence_HR.heuristics.AHeuristic;
import emergence_HR.tree.Node;
import emergence_HR.tree.Tree;

/**
 * this class expands the Tree using a modified a-star algorithm. There is no
 * check if we found our Goal, the actual Action is set by every loop in the
 * expand method using the rootaction from the best Node of the closedList. and
 * the given heuristic
 * 
 * @author spakken
 *
 */
public class AStarStrategy extends AStrategy {

	// (a < b) = 1 if a.score > b.score
	Comparator<Node> comparator;

	// to check if a Node is on the openlist
	Map<String, Node> openSet = new HashMap<String, Node>();

	// to check if a Node is on the closedlist
	Map<String, Node> closedSet = new HashMap<String, Node>();

	// to get the best Node from the openlist
	PriorityQueue<Node> openList;

	// to get the best Node from the closedlist
	PriorityQueue<Node> closedList;

	// the forbidden Actions
	ArrayList<Types.ACTIONS> forbidden_actions = new ArrayList<Types.ACTIONS>();

	public AStarStrategy(Tree tree, AHeuristic heuristic) {
		super(tree, heuristic);
		tree.root.score = this.heuristic.evaluateState(tree.root.stateObs);
		
		comparator = new NodeComparator();
		
		openList = new PriorityQueue<Node>(10, comparator);
		closedList = new PriorityQueue<Node>(10, comparator);
		
		// add own node to the open list
		openSet.put(tree.root.hash(), tree.root);
		openList.add(tree.root);
	}

	private void nextStep(Node n) {

		// generate all children
		LinkedList<Node> children = this.getChildren(n);

		// for every child that can be computed:
		for (Node child : children) {

			// store the score in the node, so that the NodeComparator can
			// compare them
			child.score = this.heuristic.evaluateState(child.stateObs);

			// if the node (Position) is already on the closed List, do nothing
			if (closedSet.containsKey(child.hash())) {
				continue;
			}

			Node node = openSet.get(child.hash());

			if (node == null) {
				// add node to the openList
				openSet.put(child.hash(), child);
				openList.add(child);

			} else if (node.level >= child.level) {
				// change path to child in openSet
				openSet.put(child.hash(), child);
				openList.add(child);
			}
		}
	}

	/**
	 * Overwritten method from Class ATree. only create the children
	 * 
	 * @param node
	 *            node that should be expanded
	 * @return list of all possible children states
	 */
	public LinkedList<Node> getChildren(Node node) {

		// state observation from the father
		StateObservation stateObs = node.stateObs;

		// generate the list with all available actions
		ArrayList<Types.ACTIONS> actions = stateObs.getAvailableActions();

		// forbid the actions which are sensless
		forbid_actions(stateObs);

		// delete the forbidden actions
		if (!forbidden_actions.isEmpty()) {
			actions.removeAll(forbidden_actions);
		}

		// create result list and reserve memory for the temporary state object
		LinkedList<Node> nodes = new LinkedList<Node>();
		StateObservation tmpStateObs;

		// for each possible action
		for (Types.ACTIONS action : actions) {
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

	private void forbid_actions(StateObservation stateObs) {

		// store the blocksize
		double blocksize = (double) stateObs.getBlockSize();

		// get the position of the avatar
		Vector2d avatar_position = stateObs.getAvatarPosition();

		// store the x and y value of the avatar position
		double x = avatar_position.x;
		double y = avatar_position.y;

		// delete all forbidden actions from a further iteration
		forbidden_actions.clear();

		// the list of immovable Objects to get the positions of the walls
		ArrayList<Observation>[] immovableObjects = stateObs
				.getImmovablePositions(avatar_position);

		// generate the Positions near to the avatar
		Vector2d left = new Vector2d(x - blocksize, y);
		Vector2d up = new Vector2d(x, y - blocksize);
		Vector2d right = new Vector2d(x + blocksize, y);
		Vector2d down = new Vector2d(x, y + blocksize);

		// for debug
		// System.out.println("position itype: " +
		// immovableObjects[0].get(0).itype);

		// check the first 3 Walls
		for (int i = 0; i < 3; i++) {

			// the position of an immovable object
			Vector2d temp = immovableObjects[0].get(i).position;

			// the distance must be 1 field
			if (avatar_position.dist(temp) / blocksize < 1.01) {

				// forbid the corresponding action if a wall has the same
				// position as
				// left, up ect.
				if (temp.equals(left)) {
					forbidden_actions.add(Types.ACTIONS.ACTION_LEFT);
				} else if (temp.equals(up)) {
					forbidden_actions.add(Types.ACTIONS.ACTION_UP);
				} else if (temp.equals(right)) {
					forbidden_actions.add(Types.ACTIONS.ACTION_RIGHT);
				} else if (temp.equals(down)) {
					forbidden_actions.add(Types.ACTIONS.ACTION_DOWN);
				} else {
					// if the distance is only 1, the position has to match one
					// of the
					// positions before
					// System.out.println("fehler forbidden actions / oder winning path gefunden");
				}
			}
		}
	}

	@Override
	public boolean expand() {
		
		if (openSet.isEmpty())
			return false;

		// int iterations = 0;
		// store the score in the node, so that the NodeComparator can compare
		// them
		

		

			Node n = openList.poll();
			openSet.remove(n.hash());

			// TODO check if path is found, maybe sensless in this case...

			// add the actual position to the closed list
			closedSet.put(n.hash(), n);
			closedList.add(n);

			// set the actual action, the rootaction from the best Node
			// on the closed List
			bestNode = closedList.peek();
			// iterations++;
			this.nextStep(n);
		
		// System.out.println("iterations: " + iterations);
		return true;
	}

}
