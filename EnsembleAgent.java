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

public class EnsembleAgent extends AbstractPlayer {

	// print out information. only DEBUG!
	final private boolean VERBOSE = true;

	// heuristic that is used
	AHeuristic heuristic;

	// tree iteration that will explore the states
	AHeuristicTree tree;

	
	public EnsembleAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		LevelInfo.print(stateObs);
		
		// simulate the heuristic and find out which is the best
		ActionTimer timer = new ActionTimer(elapsedTimer);
		HeuristicEnsemble he = HeuristicEnsemble.getInstance(stateObs);
		he.calculate(timer);
		
		heuristic = he.getTOP();
		if (heuristic == null) heuristic = new SimpleStateHeuristic();
		
		if (VERBOSE) {
			System.out.println(he);
			System.out.println("Using now: " + heuristic);
		}
		
		
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
