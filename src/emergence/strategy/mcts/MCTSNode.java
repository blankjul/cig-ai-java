package emergence.strategy.mcts;

import java.util.ArrayList;
import java.util.List;

import ontology.Types;
import tools.Vector2d;
import core.game.StateObservation;
import emergence.nodes.GenericNode;
import emergence.strategy.astar.AStarInfo;

/**
 * This class represents a TreeNode. Important is that there is the possibility
 * to simulate next steps of a node. This has the consequence that there are
 * always father and children states.
 */
public class MCTSNode extends GenericNode<AStarInfo> {

	// father node, if null it's the root
	public MCTSNode father = null;

	// reward of this node
	public double Q = 0d;

	// number of visits
	public int visited = 0;

	// the uct value
	public double uct = 0d;
	
	// if at this state the game is lost or not
	public boolean isLooser = false;


	/**
	 * Node with father and saved last action
	 * 
	 * @param father
	 *            father of this node
	 * @param lastAction
	 *            last action to come to this state
	 */
	public MCTSNode(MCTSNode father, Types.ACTIONS lastAction) {
		this.father = father;
	}

	/**
	 * Return if this node is fully expanded or not!
	 * 
	 * @return whether all children exists or some are missing
	 */
	public boolean isFullyExpanded(StateObservation stateObs) {
		return stateObs.getAvailableActions().size() == children.size();
	}

	public static String hash(StateObservation stateObs, Types.ACTIONS action) {
		Vector2d pos = stateObs.getAvatarPosition();
		String used = (action == null || action != Types.ACTIONS.ACTION_USE) ? "n"
				: "y";
		return String.format("[%s,%s,%s]", pos.x, pos.y, used);
	}

	/**
	 * Returns all children of a node without saving the state.
	 * 
	 * @param stateObsOfFather
	 * @return children nodes.
	 */
	public static List<MCTSNode> getChildren(MCTSNode n) {
		List<MCTSNode> result = new ArrayList<MCTSNode>();

		// for each possible action
		for (Types.ACTIONS a : n.children.keySet()) {
			MCTSNode nodeToAdd = n.children.get(a);
			if (nodeToAdd != null)
				result.add(nodeToAdd);
			else {
				// create a new node with father child relation
				nodeToAdd = new MCTSNode(n, a);
				result.add(nodeToAdd);
			}
		}
		return result;
	}



}
