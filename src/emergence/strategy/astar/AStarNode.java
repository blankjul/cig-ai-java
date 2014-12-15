package emergence.strategy.astar;

import core.game.StateObservation;
import emergence.nodes.GenericNode;

/**
 * This is a AStar node that has several info objects e.g. costs, heuristic and both together.
 *
 */
public class AStarNode extends GenericNode<AStarInfo> {



	public AStarNode(StateObservation stateObs) {
		super(stateObs);
		this.info = new AStarInfo();
	}
	
	public AStarNode(GenericNode<AStarInfo> n) {
		super(n.getStateObs());
		this.path = n.getPath();
		this.info = new AStarInfo();
	}

	
	@Override
	public String toString() {
		String s = "";
		s += String.format("g:%s | h:%s | f:%s | ", costs(), heuristic(), score() );
		s += super.toString();
		return s;
	}
	

	
	/*
	 * Getter and setter for the info object. Law of demeter!
	 */

	public double costs() {
		return getInfo().g;
	}
	
	public double heuristic() {
		return getInfo().h;
	}
	
	public double score() {
		return costs() + heuristic();
	}
	
	public void setHeuristic(double h) {
		getInfo().h = h;
	}
	
	public void setCosts(double g) {
		getInfo().g = g;
	}
	
}
