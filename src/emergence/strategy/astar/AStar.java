package emergence.strategy.astar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import ontology.Types.ACTIONS;
import tools.Vector2d;
import core.game.StateObservation;
import emergence.Factory;
import emergence.heuristics.AHeuristic;
import emergence.heuristics.DistanceHeuristic;
import emergence.nodes.GenericNode;
import emergence.safety.ASafety;
import emergence.safety.SafetyAdvance;
import emergence.targets.ATarget;
import emergence.util.Helper;

/**
 * This AStar algorithm finds the shortest path to a given target.
 *
 */
public class AStar {

	/** all AStarNodes that are in the openList for fast access */
	private Map<String, AStarNode> openSet = new HashMap<>();

	/** all current stateObservations that could be expanded */
	private PriorityQueue<AStarNode> openList = new PriorityQueue<>(20,
			new AStarNodeScoreComparator());

	/** all the hashes of positions where the controller was before */
	private Set<String> closedHash = new HashSet<String>();

	/**
	 * how many states should be saved in the open list! If zero there is no
	 * max!
	 */
	private int maxStates = 0;

	/** heuristic that should be used! */
	private AHeuristic heuristic;

	/** iterator for the current child iteration */
	private Iterator<GenericNode<AStarInfo>> it;

	/** state observation if the node that is iterated by it */
	private StateObservation lastStateObservation;

	/** boolean if the target was found or not! */
	private boolean hasFound = false;

	/** enables to act pessimistic */
	private ASafety safetyStrategy;// = new SafetyAdvance(4);

	/**
	 * Creates a AStar object given a state observation and a target by calling
	 * another Constructor of this class.
	 * 
	 * @param stateObs
	 * @param target
	 */
	public AStar(StateObservation stateObs, ATarget target) {
		this(stateObs, new DistanceHeuristic(target));
	}

	/**
	 * Creates a AStar object given the state observation and a target and
	 * allows to specify the maximum number of states which are stored in the
	 * openlist and a safetystrategy. It calls another constructor of this
	 * class.
	 * 
	 * @param stateObs
	 * @param target
	 * @param maxStates
	 * @param safetyStrategy
	 */
	public AStar(StateObservation stateObs, ATarget target, int maxStates,
			ASafety safetyStrategy) {
		this(stateObs, new DistanceHeuristic(target), maxStates);
		this.safetyStrategy = safetyStrategy;
	}

	/**
	 * Creates a AStar object given the state observation and a heuristic. It is
	 * called from the other constructors in this class.
	 * 
	 * @param stateObs
	 * @param heuristic
	 */
	public AStar(StateObservation stateObs, AHeuristic heuristic) {
		this.heuristic = heuristic;

		// initialize root node
		AStarNode root = new AStarNode(stateObs);
		root.setCosts(0);
		root.setHeuristic(heuristic.evaluateState(stateObs));
		openList.add(root);
		openSet.put(root.hash(), root);
	}

	/**
	 * Creates a AStar object given the state observation, the target, and the
	 * maximum number of states in the openlist. It calls another constructor of
	 * this class.
	 * 
	 * @param stateObs
	 * @param target
	 * @param maxStates
	 */
	public AStar(StateObservation stateObs, ATarget target, int maxStates) {
		this(stateObs, new DistanceHeuristic(target), maxStates);
	}

	/**
	 * Creates a AStar object given the state observation, the heuristic, and
	 * the maximum number of states in the openlist. It calls another
	 * constructor of this class.
	 * 
	 * @param stateObs
	 * @param heuristic
	 * @param maxStates
	 */
	public AStar(StateObservation stateObs, AHeuristic heuristic, int maxStates) {
		this(stateObs, heuristic);
		this.maxStates = maxStates;
	}

	/**
	 * This is the core method of the AStar algorithm. Every step is expanding
	 * one node depending on one actions. If of one node all the actions are
	 * expanded, the new best node of the open list is taken.
	 * 
	 * @return the node that was expanded or null if we found the winning node
	 *         or it's not reachable.
	 */
	public AStarNode expand() {

		// if the open list is empty no path will be ever found
		if ((openList.isEmpty() && !it.hasNext())) {
			return null;
		}

		// if there is a max state feature check for it
		if (maxStates > 0 && openList.size() > maxStates) {
			resizeOpenNodes((int) (maxStates / 2));
		}

		// if the current node is completely expanded get the next node
		if (it == null || !it.hasNext()) {

			// best Node. Remove from open set and list. add this position to
			// closed.
			AStarNode n = openList.poll();
			openSet.remove(n.hash());
			closedHash.add(n.hash());

			// for all the actions that are recommended by the simulator
			Set<ACTIONS> recommendedActions = Factory.getEnvironment()
					.getMoveActions(n.getStateObs());

			// just prune the actions by the opposite of the last - if last was
			// RIGHT then remove LEFT
			recommendedActions.remove(Helper.getOppositeAction(n
					.getLastAction()));

			lastStateObservation = n.getStateObs();
			it = n.iteratorFromActions(recommendedActions);

			// get the AStarNode and set the costs
			if (!it.hasNext())
				return n;

		}

		AStarNode child = new AStarNode(it.next());
		child.setCosts(Helper.distance(
				lastStateObservation.getAvatarPosition(), child.getStateObs()
						.getAvatarPosition()));
		child.setHeuristic(heuristic.evaluateState(child.getStateObs()));

		if (safetyStrategy != null && child.getLevel() == 1) {
			if (!safetyStrategy.isSafe(child.getStateObs(),
					child.getLastAction())) {
				child.setHeuristic(Double.POSITIVE_INFINITY);
			}
		}

		// if the target was found. YEAH!
		if (child.heuristic() == 0) {
			openList.clear();
			openSet.clear();
			hasFound = true;
			return child;
		}

		// if the agent were there before continue
		if (closedHash.contains(child.hash())
				|| child.heuristic() == Double.POSITIVE_INFINITY) {
			return child;
		}

		// set the tentative g score
		AStarNode nodeSamePosition = openSet.get(child.hash());

		// if child is not in open set -> just add
		if (nodeSamePosition == null) {
			openSet.put(child.hash(), child);
			openList.add(child);
		} else {
			// if it's in the open set so we have a node at the same
			// position
			// check if the current child has lower costs
			if (child.costs() < nodeSamePosition.costs()) {
				openSet.remove(nodeSamePosition);
				openList.remove(nodeSamePosition);
				openSet.put(child.hash(), child);
				openList.add(child);
			}
		}
		return child;
	}

	/**
	 * Change the size of the openlist by changing the size of members openList
	 * and openSet.
	 * 
	 * @param size
	 */
	private void resizeOpenNodes(int size) {
		Map<String, AStarNode> openSetNew = new HashMap<>();
		PriorityQueue<AStarNode> openListNew = new PriorityQueue<>(size,
				new AStarNodeScoreComparator());

		for (AStarNode n : openList) {
			openListNew.add(n);
			openSetNew.put(n.hash(), n);
			if (openListNew.size() > size)
				break;
		}

		openList = openListNew;
		openSet = openSetNew;

	}

	/**
	 * Prints the nodes in the openlist.
	 */
	public void printOpenList() {
		// System.out.println("---------BEST--------");
		// System.out.println(act().toString());
		System.out.println("---------OPENLIST--------");
		for (AStarNode n : openList) {
			System.out.println(n);
		}
	}

	/**
	 * Returns the actual openlist.
	 * 
	 * @return
	 */
	public PriorityQueue<AStarNode> getOpenList() {
		return openList;
	}

	/**
	 * Returns true if the astar algorithm does not find a solution, false
	 * otherwise.
	 * 
	 * @return
	 */
	public boolean isNotReachable() {
		return openList.isEmpty() && !hasFound;
	}

	/**
	 * Returns true if the algorithm has found a path, false otherwise.
	 * 
	 * @return
	 */
	public boolean hasFound() {
		return hasFound;
	}

	/**
	 * Prints debug information in the displayed window
	 * 
	 * @param
	 */
	public void paint(Graphics2D g) {
		int markerSize = 15;

		for (String str : closedHash) {
			String[] strPoint = str.substring(1, str.length() - 1).split(",");
			Vector2d v = new Vector2d(Double.valueOf(strPoint[0]),
					Double.valueOf(strPoint[1]));
			Point p = new Point((int) v.x, (int) v.y);
			g.setColor(Color.BLACK);
			g.fillOval(p.x + 5, p.y + 5, markerSize, markerSize);
		}

		for (AStarNode node : openList) {
			g.setColor(Color.YELLOW);
			Vector2d v = node.getStateObs().getAvatarPosition();
			Point p = new Point((int) v.x, (int) v.y);
			g.fillOval(p.x, p.y, markerSize, markerSize);
		}

		if (lastStateObservation != null) {
			Vector2d v = lastStateObservation.getAvatarPosition();
			Point p = new Point((int) v.x, (int) v.y);
			g.setColor(Color.RED);
			g.fillOval(p.x, p.y, markerSize, markerSize);
		}

	}

	/**
	 * Returns the parameters, used for csv output.
	 * 
	 * @return
	 */
	public String toCSVString() {
		String par = "";
		par += "DistanceHeuristic,";
		par += (safetyStrategy == null ? "null,null" : safetyStrategy
				.toCSVString());

		return par;
	}
}
