package controllers.cig;


import ontology.Types;
import tools.ElapsedCpuTimer;
import controllers.cig.heuristics.SimpleStateHeuristic;
import core.game.StateObservation;
import core.player.AbstractPlayer;

public class Agent extends AbstractPlayer {


	protected ActionTimer timer;

	public Agent(StateObservation so, ElapsedCpuTimer elapsedTimer) {}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		// Initialize the timer for all iterations that are made
		timer = new ActionTimer(elapsedTimer);

		// The action we will finally be executed
		Types.ACTIONS bestAction = null;

		// copy of our state object
		StateObservation stCopy = null;

		// initialize the values for the heuristic
		double maxQ = Double.NEGATIVE_INFINITY;
		SimpleStateHeuristic heuristic = new SimpleStateHeuristic(stateObs);

		// search for the next best action
		for (Types.ACTIONS action : stateObs.getAvailableActions()) {

			// check whether we've enough time for a next iteration.
			if (timer.isTimeLeft()) {
				timer.start();
				
				stCopy = stateObs.copy();
				stCopy.advance(action);
				double Q = heuristic.evaluateState(stCopy);

				if (Q > maxQ) {
					maxQ = Q;
					bestAction = action;
				}

				
				timer.stop();
			}

		}

		System.out.println(timer.status());
		return bestAction;

	}

}
