package emergence_HR;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.HeuristicEnsemble;
import emergence_HR.heuristics.StateHeuristic;
import emergence_HR.nodes.Node;
import emergence_HR.nodes.NodeTree;

public class Agent extends AbstractPlayer {

	/**
	 * if we should print information or not.
	 */
	final private boolean VERBOSE = true;

	/**
	 * This variable defines how often we should simulate heuristics until we
	 * are getting started!
	 */
	final private int WAIT_TICKS = 20;

	/**
	 * this is the heuristic that is applied if we found it!
	 */
	StateHeuristic heuristic = null;

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
	}
	
	

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		HeuristicEnsemble he = HeuristicEnsemble.getInstance(stateObs);

		if (stateObs.getGameTick() == WAIT_TICKS) {
			heuristic = he.getTOP();
			if (VERBOSE) {
				System.out.printf("Best heuristic found: %s", heuristic);
			}
		}

		// initialize the values for the heuristic and the timer
		ActionTimer timer = new ActionTimer(elapsedTimer);

		if (heuristic == null) {

			// search for the best heuristic!
			he.calculate(timer);
			return Types.ACTIONS.ACTION_NIL;

		} else {

			// apply the best found heuristic
			Types.ACTIONS action = Types.ACTIONS.ACTION_NIL;

			NodeTree tree = new NodeTree(new Node(stateObs), heuristic);
			action = tree.expand(timer);
			return action;
		}


	}
}
