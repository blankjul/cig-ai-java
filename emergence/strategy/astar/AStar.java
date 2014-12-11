package emergence.strategy.astar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import ontology.Types.ACTIONS;
import tools.Vector2d;
import core.game.StateObservation;
import emergence.Factory;
import emergence.util.Helper;

/**
 * This AStar algorithm finds the path to a given target.
 *
 */
public class AStar {

	// all AStarNodes that are in the openList for fast access
	private Map<String, AStarNode> openSet = new HashMap<>();

	// all current stateObservations that could be expanded
	private PriorityQueue<AStarNode> openList = new PriorityQueue<>(20);

	// all the hashes of positions where the controller was before
	private Set<String> closedSet = new HashSet<>();
	
	private Vector2d target = new Vector2d(736.0,80.0);
	

	public AStar(StateObservation stateObs) {
		AStarNode root = new AStarNode(stateObs);
		openList.add(root);
		closedSet.add(root.hash());
	}

	/**
	 * This is the core method that expands for one times the tree
	 */
	public boolean expand() {

		if (openList.isEmpty()) return false;

		// best Node. Remove from open set and list. add this position to
		// closed.
		AStarNode n = openList.poll();
		openSet.remove(Helper.hash(n.stateObs));
		closedSet.add(Helper.hash(n.stateObs));

		// for all the actions that are recommended by the simulator
		Set<ACTIONS> recommendedActions = Factory.getSimulator().getRecommendActions(n.stateObs);
		
		// just prune the actions by the opposite of the last - if last was RIGHT then remove LEFT
		recommendedActions.remove(Helper.getOppositeAction(n.getLastAction()));
		
		// for each possible child
		for (AStarNode child : n.getChildren(recommendedActions)) {

			boolean found = Helper.distance(child.stateObs.getAvatarPosition(), target) == 0;
			boolean gameOver = child.stateObs.isGameOver();
			if (found || gameOver) {
				System.out.println("YEAHHH FOUND");
				return false;
			}
			
			// if the agent were there before continue
			if (closedSet.contains(child.hash()))
				continue;

			// set the tentative g score
			child.f = child.g + Helper.distance(child.stateObs.getAvatarPosition(), target);
			AStarNode nodeSamePosition = openSet.get(child.hash());

			// if child is not in open set -> just add
			if (nodeSamePosition == null) {
				openSet.put(child.hash(), child);
				openList.add(child);
			} else {
				// if it's in the open set so we have a node at the same position
				// check if the current child has lower costs
				if (child.g < nodeSamePosition.g) {
					openSet.remove(nodeSamePosition);
					openList.remove(nodeSamePosition);
					openSet.put(child.hash(), child);
					openList.add(child);
				}
			}
		}

		return true;
	}
	
	
	public AStarNode getBest() {
		return openList.peek();
	}
	
	public void printOpenList() {
		System.out.println("---------OPENLIST--------");
		for (AStarNode n : openList) {
			System.out.println(n);
		}
	}

	public PriorityQueue<AStarNode> getOpenList() {
		return openList;
	}

	public Set<String> getClosedSet() {
		return closedSet;
	}

}
