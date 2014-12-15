package emergence.strategy;

import ontology.Types.ACTIONS;
import core.game.StateObservation;
import emergence.safety.SafetyAdvance;
import emergence.strategy.astar.AStar;
import emergence.strategy.astar.AStarNode;
import emergence.targets.ATarget;
import emergence.util.ActionTimer;
import emergence.util.ObservationUtil;

public class AStarStrategy extends AStrategy {

	// print output or not
	public boolean verbose = false;

	// astar object that aims to find the path
	private AStar astar;

	// target to follow
	private ATarget target;

	// current best score
	private AStarNode bestNode;
	private double bestScore = Double.POSITIVE_INFINITY;
	
	// maximal number of astar iterations until it's really not reachable
	private int notFound = 0;
	private final int MAX_NOT_FOUND = 10;

	
	public AStarStrategy(ATarget target) {
		this.target = target;
	}


	@Override
	public boolean expand(StateObservation stateObs, ActionTimer timer) {

		bestScore = Double.POSITIVE_INFINITY;
		bestNode = null;
		
		// if there was no path to the target found or the next step is not safe
		this.astar = new AStar(stateObs, target, 15, new SafetyAdvance(5));
		
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
		
		// check how often the result was not found
		// if to often return false
		notFound = (astar.hasFound()) ? 0 : notFound + 1;
		return notFound < MAX_NOT_FOUND;
		

	}
	
	

	public boolean hasFound(StateObservation stateObs) {
		if (ObservationUtil.collisionLastStep(stateObs) == target.getItype()) {
			return true;
		}
		return false;
	}

	@Override
	public ACTIONS act() {
		if (bestNode == null)
			return ACTIONS.ACTION_NIL;
		else {
			return bestNode.getFirstAction();
		}
	}

	public AStar getAstar() {
		return astar;
	}

	public ATarget getTarget() {
		return target;
	}


}
