package emergence_HR;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.EquationStateHeuristic;
import emergence_HR.strategy.AStrategy;
import emergence_HR.strategy.GreedyStrategy;
import emergence_HR.strategy.LevelOrderStrategy;
import emergence_HR.tree.Node;
import emergence_HR.tree.Tree;


public class StaticAgent extends AbstractPlayer {

	final private boolean VERBOSE = false;

	public EquationStateHeuristic heuristic;
	
	public StaticAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		heuristic = new EquationStateHeuristic(new double[] {19.37619596057891, -79.73664702906483, -96.25329543520054, -57.41270457857273, -23.11472539195603, 75.38677000001712, -75.8471732997867, 85.55967681256107, 47.99694570131024, 82.46257047984727 });
	}

	
	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {
		
		Tree tree = new Tree(new Node(stateObs));
		AStrategy strategy = new GreedyStrategy(tree,heuristic);
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
