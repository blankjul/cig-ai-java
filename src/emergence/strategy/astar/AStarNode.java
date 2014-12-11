package emergence.strategy.astar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ontology.Types.ACTIONS;
import tools.Vector2d;
import core.game.StateObservation;
import emergence.Factory;
import emergence.Simulator;
import emergence.util.Helper;

public class AStarNode implements Comparable<AStarNode> {

	public StateObservation stateObs;

	public ArrayList<ACTIONS> path = new ArrayList<>();

	// costs until now
	public double g;

	// total costs
	public double f;

	// level of this node
	public int level;

	public AStarNode(StateObservation stateObs) {
		super();
		this.stateObs = stateObs;
		this.f = 0;
		this.g = 0;
	}

	public ACTIONS getLastAction() {
		if (path.isEmpty())
			return ACTIONS.ACTION_NIL;
		else
			return path.get(path.size() - 1);
	}

	/**
	 * Overwritten method from Class ATree. only create the children
	 * 
	 * @param node
	 *            node that should be expanded
	 * @return list of all possible children states
	 */
	public List<AStarNode> getChildren(Set<ACTIONS> actionSet) {

		// create result list and reserve memory for the temporary state object
		LinkedList<AStarNode> nodes = new LinkedList<AStarNode>();
		StateObservation tmpStateObs;
		
		Simulator sim = Factory.getSimulator();

		// for each possible action
		for (ACTIONS action : actionSet) {
			// create the next state
			tmpStateObs = stateObs.copy();
			sim.advance(tmpStateObs, action);

			AStarNode child = new AStarNode(tmpStateObs);
			child.level = level + 1;
			child.path.add(action);
			child.g = g + Helper.distance(stateObs.getAvatarPosition(), child.stateObs.getAvatarPosition());

			nodes.add(child);
		}
		tmpStateObs = null;
		return nodes;
	}

	/**
	 * Return a "hash string" of each node.
	 * 
	 * @return
	 */
	public String hash() {
		return Helper.hash(stateObs);
	}

	@Override
	public int compareTo(AStarNode o) {
		Double d1 = o.f;
		Double d2 = this.f;
		return d2.compareTo(d1);
	}

	@Override
	public String toString() {
		Vector2d pos = stateObs.getAvatarPosition();
		return String.format("me:[%s,%s] | level%s | g:%s | f:%s", pos.x, pos.y, level, g, f);
	}

}
