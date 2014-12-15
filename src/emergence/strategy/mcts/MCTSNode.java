package emergence.strategy.mcts;

import java.util.HashMap;

import ontology.Types;
import ontology.Types.ACTIONS;
import tools.Vector2d;
import core.game.StateObservation;
import emergence.nodes.GenericNode;

/**
 * This class represents a TreeNode. Important is that there is the possibility
 * to simulate next steps of a node. This has the consequence that there are
 * always father and children states.
 */
public class MCTSNode extends GenericNode<Object> {

	
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
	
	// children store
	public HashMap<ACTIONS, MCTSNode> children = new HashMap<>();


	public MCTSNode(MCTSNode father, Types.ACTIONS lastAction) {
		super();
		this.father = father;
	}

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
	 * Removes the first action
	 */
	public void removeFirstAction() {
		if (!path.isEmpty()) {
			path.remove(0);
		}
	}
	
	



	


}
