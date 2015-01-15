package emergence.strategy;

import java.util.Set;

import ontology.Types.ACTIONS;
import core.game.StateObservation;
import emergence.strategy.astar.AStar;
import emergence.strategy.astar.AStarNode;
import emergence.targets.ATarget;
import emergence.targets.TargetFactory;
import emergence.util.ActionTimer;
import emergence.util.Helper;

/**
 * Expands nodes using the astar algorithm. Paths to different targets can be
 * generated.
 * 
 *
 */
public class ExplorerStrategy extends AStrategy {

	/** current target that is explored */
	private ATarget currentTarget = null;

	/** all targets that should be explored until refresh */
	private Set<ATarget> allTargets;

	/** the astar algorithm that is used for exploring */
	private AStar astar;

	/**
	 * how many states should be saved in the openlist of the astar algortimh
	 * (if zero there is no max
	 */
	private int maxStates = 20;

	/**
	 * Creates a ExplorerStrategy and store all targets.
	 * 
	 * @param stateObs
	 */
	public ExplorerStrategy(StateObservation stateObs) {
		allTargets = TargetFactory.getAllTargets(stateObs);
	}

	/**
	 * Expands a node using the astar Algorithm. Returns false if no path
	 * exists, true otherwise.
	 */
	@Override
	public boolean expand(StateObservation stateObs, ActionTimer timer) {

		boolean next = true;

		while (timer.isTimeLeft()) {

			if (currentTarget == null || !next) {
				currentTarget = getNextTarget(stateObs);

				// if there is no target to explore just return
				if (currentTarget == null)
					return false;

				// else set the current astar algorithm
				astar = new AStar(stateObs, currentTarget, maxStates);
			}

			AStarNode n = astar.expand();
			next = n != null;

			timer.addIteration();
		}
		return true;
	}

	/**
	 * search for a new target that could be explored
	 * 
	 * @param stateObs
	 * @return
	 */
	private ATarget getNextTarget(StateObservation stateObs) {
		// get new targets
		if (allTargets == null || allTargets.isEmpty())
			allTargets = TargetFactory.getAllTargets(stateObs);

		// if there are still no targets return.
		if (allTargets.isEmpty())
			return null;

		ATarget t = Helper.getRandomEntry(allTargets);
		allTargets.remove(t);
		return t;

	}

	/**
	 * Returns the action NIL.
	 */
	@Override
	public ACTIONS act() {
		return ACTIONS.ACTION_NIL;
	}

	/**
	 * Returns a String representation of all available targets and the actual
	 * one.
	 */
	@Override
	public String toString() {
		return String.format("EXPLORE Target: %s ALL: %s", currentTarget,
				Helper.toString(allTargets));
	}

	/**
	 * Returns the parameters, used for csv output.
	 * 
	 * @return
	 */
	public String toCSVString() {
		String par = "";
		par += Integer.toString(maxStates) + ",";

		// parameters from class Astar
		par += astar.toCSVString();

		return par;

	}
}
