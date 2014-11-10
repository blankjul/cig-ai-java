package emergence_HR;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.helper.ActionTimer;
import emergence_HR.heuristics.EquationStateHeuristic;
import emergence_HR.strategy.AStarStrategy;
import emergence_HR.strategy.AStrategy;
import emergence_HR.tree.Node;
import emergence_HR.tree.Tree;


public class StaticAgent extends AbstractPlayer {

	final private boolean VERBOSE = false;

	public EquationStateHeuristic heuristic;
	
	public StaticAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		heuristic = new EquationStateHeuristic(new double[] {58.16800694126627,88.91126359194271,-37.68401345063439,26.269820076414476,55.54802996674292,-78.22020666492267,-62.40026810581452,76.82439310673325,-66.19284453128458,-42.320904969668696 });
	}

	
	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {
		
		Tree tree = new Tree(new Node(stateObs));
		AStrategy strategy = new AStarStrategy(tree,heuristic);
		boolean hasNext = true;
		ActionTimer timer = new ActionTimer(elapsedTimer);
		
		while (timer.isTimeLeft() && hasNext) hasNext = strategy.expand();

		Types.ACTIONS action = strategy.bestNode.rootAction;

		if (VERBOSE)
			System.out.println(timer.status());

		return action;

	}
	
	@Override
	public String toString() {
		return heuristic.toString();
	}
	
	
}
