package emergence.strategy.astar;

import core.game.StateObservation;
import emergence.nodes.GenericNode;

/**
 * This is a AStar node that has several info objects e.g. costs, heuristic and
 * both together.
 *
 */
public class AStarNode extends GenericNode<AStarInfo> {

	/**
	 * Creates an AStarNode and calls the super constructor.
	 * 
	 * @param stateObs
	 */
	public AStarNode(StateObservation stateObs) {
		super(stateObs);
		this.info = new AStarInfo();
	}

	/**
	 * Creates an AStarNode from an generic node, calls the super constructor.
	 * 
	 * @param n
	 */
	public AStarNode(GenericNode<AStarInfo> n) {
		super(n.getStateObs());
		this.path = n.getPath();
		this.info = new AStarInfo();
	}

	/**
	 * Returns a String representation of the AStarNode.
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		String s = "";
		s += String.format("g:%s | h:%s | f:%s | ", costs(), heuristic(),
				score());
		s += super.toString();
		return s;
	}

	/*
	 * Getter and setter for the info object. Law of demeter!
	 */

	/**
	 * getter of the
	 * 
	 * @return
	 */
	public double costs() {
		return getInfo().g;
	}

	/**
	 * Returns the heuristic.
	 * 
	 * @return
	 */
	public double heuristic() {
		return getInfo().h;
	}

	/**
	 * Returns the score, adds the costs and heuristic value.
	 * 
	 * @return
	 */
	public double score() {
		return costs() + heuristic();
	}

	/**
	 * Set the heuristic.
	 * 
	 * @param h
	 */
	public void setHeuristic(double h) {
		getInfo().h = h;
	}

	/**
	 * Set the costs.
	 * 
	 * @param g
	 */
	public void setCosts(double g) {
		getInfo().g = g;
	}

}
