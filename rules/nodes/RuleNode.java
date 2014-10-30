package emergence_HR.rules.nodes;

import java.util.LinkedList;

import ontology.Types;
import tools.Vector2d;
import core.game.StateObservation;

public class RuleNode {

	// father node, if null it's the root
	public RuleNode father;

	// current state of this node
	public StateObservation stateObs;

	// heuristic until we found our destination
	protected double h;

	// the destination that we want to reach
	protected Vector2d dest;

	// always the last action
	public Types.ACTIONS lastAction;

	/**
	 * 
	 * @param father
	 * @param stateObs
	 */
	public RuleNode(RuleNode father, StateObservation stateObs, Vector2d dest) {
		this.father = father;
		this.stateObs = stateObs;
		this.dest = dest;
		this.h = getHeuristic();
	}

	/**
	 * Create a list of all possible children that could be created from this
	 * state.
	 * 
	 * @return list of all possible children states
	 */
	public LinkedList<RuleNode> getChildren() {
		// create result list and reserve memory for the temporary state object
		LinkedList<RuleNode> nodes = new LinkedList<RuleNode>();
		// for each possible action
		for (Types.ACTIONS action : stateObs.getAvailableActions()) {
			// create the next state
			StateObservation s = stateObs.copy();
			s.advance(action);
			RuleNode n = new RuleNode(this, s, dest);
			n.father = this;
			n.lastAction = action;
			nodes.add(n);
		}
		return nodes;
	}

	
	public double getDistance(Vector2d src, Vector2d dest) {
		return Math.abs(src.x - dest.x) + Math.abs(src.y - dest.y);
	}

	public double getHeuristic() {
		if (stateObs.isGameOver() && stateObs.getGameWinner() == Types.WINNER.PLAYER_WINS) return Double.MIN_VALUE;
		return getDistance(stateObs.getAvatarPosition(), dest);
	}

	
	@Override
	public boolean equals(Object obj) {
		RuleNode node = (RuleNode) obj;
		Vector2d myPos = this.stateObs.getAvatarPosition();
		Vector2d nodePos = node.stateObs.getAvatarPosition();
		return myPos.x == nodePos.x && myPos.y == nodePos.y;
	}
	
	public String toString() {
		Vector2d pos = stateObs.getAvatarPosition();
		return String.format("me:[%s,%s] dest:[%s,%s] | %s | h:%s | ", pos.x, pos.y, dest.x, dest.y, lastAction , getHeuristic());
	}

}
