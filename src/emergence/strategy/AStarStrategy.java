package emergence.strategy;

import ontology.Types.ACTIONS;
import core.game.StateObservation;
import emergence.safety.SafetyAdvance;
import emergence.strategy.astar.AStar;
import emergence.strategy.astar.AStarNode;
import emergence.targets.ATarget;
import emergence.util.ActionTimer;
import emergence.util.ObservationUtil;

/**
 * This class uses the astar algorithm to find a path to a target.
 * 
 *
 */
public class AStarStrategy extends AStrategy {

	/** print output or not */
	public boolean verbose = false;

	/** astar object that aims to find the path */
	private AStar astar;

	/** target to follow */
	private ATarget target;

	/** current best Node / score */
	private AStarNode bestNode;

	/** actual heuristic score of the astar node */
	private double bestScore = Double.POSITIVE_INFINITY;

	/** upper bound for the number of failures of the astar algorithm */
	private final int MAX_NOT_FOUND = 15;

	/** counts the cases in which the astar object can not find a path */
	private int counter = 0;

	/**
	 * Create a AStarStrategy by defining a goal-target.
	 * 
	 * @param target
	 */
	public AStarStrategy(ATarget target) {
		this.target = target;
	}

	/**
	 * Returns true if the astar algorithm has found a path before reaching
	 * MAX_NOT_FOUND. The astar algorithm is executed while some time is
	 * remaining
	 */
	@Override
	public boolean expand(StateObservation stateObs, ActionTimer timer) {

		bestScore = Double.POSITIVE_INFINITY;
		bestNode = null;

		// if there was no path to the target found or the next step is not safe
		this.astar = new AStar(stateObs, target, 100, new SafetyAdvance(5));

		// expanding the astar algorithm while there is time
		while (timer.isTimeLeft()) {
			AStarNode n = astar.expand();

			// if astar finished break
			if (n == null)
				break;

			if (n.heuristic() < bestScore) {
				bestNode = n;
				bestScore = n.heuristic();
			}
			timer.addIteration();
		}

		// look if the astar algorithm was successfully
		counter = (astar.hasFound()) ? 0 : counter + 1;
		return counter < MAX_NOT_FOUND;

	}

	/**
	 * Returns true if the AStarStrategy has found a path to the target.
	 * 
	 * @param stateObs
	 * @return
	 */
	public boolean hasFound(StateObservation stateObs) {
		if (ObservationUtil.collisionLastStep(stateObs) == target.getItype()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the first action of the path.
	 */
	@Override
	public ACTIONS act() {
		if (bestNode == null)
			return ACTIONS.ACTION_NIL;
		else {
			return bestNode.getFirstAction();
		}
	}

	/**
	 * Returns the astar object.
	 * 
	 * @return
	 */
	public AStar getAstar() {
		return astar;
	}

	/**
	 * Returns the target.
	 * 
	 * @return
	 */
	public ATarget getTarget() {
		return target;
	}

	/**
	 * Returns the parameters, used for csv output.
	 * 
	 * @return
	 */
	public String toCSVString() {
		return Integer.toString(MAX_NOT_FOUND);
	}
}
