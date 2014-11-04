package emergence_HR;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.AHeuristic;
import emergence_HR.heuristics.SimpleStateHeuristic;

public class EnsembleAgent extends AbstractPlayer {

	// print out information. only DEBUG!
	final private boolean VERBOSE = true;

	// heuristic that is used
	AHeuristic heuristic = new SimpleStateHeuristic();

	public EnsembleAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		LevelInfo.print(stateObs);
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		HeuristicEnsemble he = HeuristicEnsemble.getInstance(stateObs);
		ActionTimer timer = new ActionTimer(elapsedTimer);
		he.calculate(timer);

		if (VERBOSE)
			System.out.println(he);
		System.out.println(timer.status());

		return Types.ACTIONS.ACTION_NIL;

	}
}
