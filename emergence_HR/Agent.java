package emergence_HR;

import java.util.Stack;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.helper.ActionTimer;
import emergence_HR.helper.LevelInfo;
import emergence_HR.heuristics.AHeuristic;
import emergence_HR.strategy.AStrategy;
import emergence_HR.strategy.EnsembleStrategy;
import emergence_HR.strategy.LevelOrderStrategy;
import emergence_HR.tree.Node;
import emergence_HR.tree.Tree;

public class Agent extends AbstractPlayer {

	// print out information. only DEBUG!
	final private boolean VERBOSE = false;

	// heuristic that is used
	AHeuristic heuristic;

	// path that is found by the heuristic
	Stack<Types.ACTIONS> path = new Stack<Types.ACTIONS>();

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		Tree tree = new Tree(new Node(stateObs));
		EnsembleStrategy ensemble = new EnsembleStrategy(tree);

		boolean hasNext = true;
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 150;
		while (timer.isTimeLeft() && hasNext) {
			hasNext = ensemble.expand();
			timer.addIteration();
		}

		heuristic = ensemble.top();
		
		System.out.println(ensemble);
		System.out.println(heuristic);

		if (VERBOSE) {
			LevelInfo.print(stateObs);
			System.out.println(ensemble);
			System.out.println("Using now: " + heuristic);
			System.out.println(timer.status());
		}
	}
	

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		Tree tree = new Tree(new Node(stateObs));
		AStrategy strategy = new LevelOrderStrategy(tree, heuristic);

		boolean hasNext = true;
		ActionTimer timer = new ActionTimer(elapsedTimer);
		while (timer.isTimeLeft() && hasNext) {
			hasNext = strategy.expand();
			timer.addIteration();
		}

		Types.ACTIONS action = strategy.bestNode.rootAction;

		if (VERBOSE)
			System.out.println(timer.status());

		return action;
	}

}
