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
	
	private final int MAX_NOT_FOUND = 15;
	private int counter = 0;
	
	
	public AStarStrategy(ATarget target) {
		this.target = target;
	}


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

	public String toCSVString(){
		return Integer.toString(MAX_NOT_FOUND);
	}
}
