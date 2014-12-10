package emergence.strategy.astar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import ontology.Types.ACTIONS;
import ontology.Types.WINNER;
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
	
	private Vector2d target = new Vector2d(700,140);
	

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
			boolean winner = child.stateObs.getGameWinner() == WINNER.PLAYER_WINS;
			if (found || winner) {
				System.out.println("YEAHHH FOUND");
				return false;
			}
			
			// if the agent were there before continue
			if (closedSet.contains(child.hash()))
				continue;

			// set the tentative g score
			child.g = n.g + Helper.distance(n.stateObs.getAvatarPosition(), child.stateObs.getAvatarPosition());
			child.f = child.g + Helper.distance(child.stateObs.getAvatarPosition(), target);

			AStarNode nodeSamePosition = openSet.get(child.hash());

			// if child not in open set
			if (nodeSamePosition == null) {
				openSet.put(child.hash(), child);
				openList.add(child);
				
			} else {
				// if it's in the open set

				// if the node in the open set has a longer way
				if (child.g < nodeSamePosition.g) {
					openSet.remove(nodeSamePosition);
					openList.remove(nodeSamePosition);

				}

			}

		}

		printOpenList();
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

}
