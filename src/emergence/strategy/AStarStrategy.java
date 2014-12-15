package emergence.strategy;

import ontology.Types.ACTIONS;
import core.game.StateObservation;
import emergence.heuristics.DistanceHeuristic;
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

	
	public AStarStrategy(StateObservation stateObs, ActionTimer timer, ATarget target) {
		super(stateObs, timer);
		this.target = target;
		this.astar = new AStar(stateObs, new DistanceHeuristic(target));
	}

	@Override
	public void reset(StateObservation stateObs, ActionTimer timer) {
		super.reset(stateObs, timer);
		bestScore = Double.POSITIVE_INFINITY;
		bestNode = null;
	}

	@Override
	public void expand() {

		// if there was no path to the target found or the next step is not safe

			this.astar = new AStar(stateObs, new DistanceHeuristic(target));

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

	}
	
	public boolean hasFound() {
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

	public AStarNode getBestNode() {
		return bestNode;
	}

}
