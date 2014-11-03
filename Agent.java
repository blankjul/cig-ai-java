package emergence_HR;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.HeuristicEnsemble;

public class Agent extends AbstractPlayer {

	final private boolean VERBOSE = true;

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		LevelInfo.print(stateObs);
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		HeuristicEnsemble he = HeuristicEnsemble.getInstance(stateObs);

		// initialize the values for the heuristic and the timer
		ActionTimer timer = new ActionTimer(elapsedTimer);

		// search for the best heuristic!
		he.calculate(timer);
		
		if (VERBOSE) {
			// LevelInfo.printNodes(tree.queue);
			System.out.println(he.toString());
			System.out.println(timer.status());
			System.out.println("------------------------------------------");
		}

		// The action we will finally be executed
		Types.ACTIONS action = Types.ACTIONS.ACTION_NIL;
		return action;

		/*
		 * ArrayList<Target> l = TargetFactory.getPortals(stateObs); Target t =
		 * l.get(0);
		 * 
		 * StateHeuristic heuristic = new TargetHeuristic(t);
		 * 
		 * Node root = new Node(stateObs); NodeTree tree = new NodeTree(root,
		 * heuristic);
		 * 
		 * action = tree.expand(timer);
		 */

	}
}
