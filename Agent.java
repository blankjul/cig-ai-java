package emergence_HR;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.SimpleStateHeuristic;
import emergence_HR.heuristics.StateHeuristic;
import emergence_HR.tree.HeuristicTreeOneStep;
import emergence_HR.tree.Node;

public class Agent extends AbstractPlayer {

	// print out information. only DEBUG!
	final private boolean VERBOSE = true;

	// heuristic that is used
	final StateHeuristic heuristic = new SimpleStateHeuristic();
	
	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		HeuristicTreeOneStep gameTree = new HeuristicTreeOneStep(new Node(stateObs), heuristic);
		
		ActionTimer timer = new ActionTimer(elapsedTimer);
		gameTree.expand(timer);
		
		Types.ACTIONS action = gameTree.action();
		return action;
		
	}
}
