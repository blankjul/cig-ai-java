package emergence_HR;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.AHeuristic;
import emergence_HR.heuristics.SimpleStateHeuristic;
import emergence_HR.tree.AHeuristicTree;
import emergence_HR.tree.HeuristicTreeGreedy;
import emergence_HR.tree.Node;

public class Agent extends AbstractPlayer {

	// print out information. only DEBUG!
	final private boolean VERBOSE = true;

	// heuristic that is used
	AHeuristic heuristic = new SimpleStateHeuristic();

	// tree iteration that will explore the states
	AHeuristicTree tree;

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		tree = new HeuristicTreeGreedy(new Node(stateObs), heuristic);

		ActionTimer timer = new ActionTimer(elapsedTimer);
		tree.expand(timer);
		Types.ACTIONS action = tree.action();

		if (VERBOSE)
			System.out.println(timer.status());

		return action;

	}
}
