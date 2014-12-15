package emergence.strategy;

import java.util.ArrayList;

import ontology.Types.ACTIONS;
import core.game.StateObservation;
import emergence.heuristics.DistanceHeuristic;
import emergence.safety.SafetyIntelligent;
import emergence.strategy.astar.AStar;
import emergence.strategy.astar.AStarNode;
import emergence.targets.ATarget;
import emergence.util.ActionTimer;
import emergence.util.Helper;
import emergence.util.ObservationUtil;

public class CopyOfAStarStrategy extends AStrategy {

	// print output or not
	public boolean verbose = false;

	// astar object that aims to find the path
	private AStar astar;

	// if target was found this is the path to the target
	private ArrayList<ACTIONS> path = new ArrayList<>();

	// target to follow
	private ATarget target;

	// current best score
	private AStarNode bestNode;
	private double bestScore = Double.POSITIVE_INFINITY;

	
	public CopyOfAStarStrategy(StateObservation stateObs, ActionTimer timer, ATarget target) {
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
		if (path.isEmpty() || new SafetyIntelligent(5).isSafe(stateObs, path.get(0))) {

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

			// if the best node is found save the whole path
			// else just get the first action
			if (astar.hasFound()) {
				this.path = bestNode.getPath();
				if (verbose)
					System.out.println("me " + stateObs.getAvatarPosition().toString() + " | Found path to the target " + target + " | " + Helper.toString(path) + " Collision " + hasFound());
			} else {
				if (bestNode != null) {
					if (verbose)System.out.println("Best Node: " + bestNode);
					path.add(bestNode.getFirstAction());
				}
			}
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
		if (path.isEmpty())
			return ACTIONS.ACTION_NIL;
		else {
			ACTIONS a = path.get(0);
			path.remove(0);
			return a;
		}
	}

	public AStar getAstar() {
		return astar;
	}

	public ArrayList<ACTIONS> getPath() {
		return path;
	}

	public ATarget getTarget() {
		return target;
	}

	public AStarNode getBestNode() {
		return bestNode;
	}

}
