package emergence.strategy.astar;

/**
 * Stores the g (costs) and h (heuristic) value of an astar node.
 *
 */
public class AStarInfo {

	/** costs until now */
	public double g = Double.POSITIVE_INFINITY;

	/** heuristic value */
	public double h = Double.POSITIVE_INFINITY;

}
