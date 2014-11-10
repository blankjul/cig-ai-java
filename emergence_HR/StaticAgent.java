package emergence_HR;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.EquationStateHeuristic;
import emergence_HR.strategy.AStrategy;
import emergence_HR.strategy.LevelOrderStrategy;
import emergence_HR.tree.Node;
import emergence_HR.tree.Tree;


public class StaticAgent extends AbstractPlayer {

	final private boolean VERBOSE = false;

	public EquationStateHeuristic heuristic;
	
	public StaticAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		heuristic = new EquationStateHeuristic(new double[] {22.411238839548915,-80.48724107173246,32.36064972660259,83.99446994734384,42.64481692521031,55.32231465005154,-55.487332283934144,10.71756675650282,47.99218288302822,-79.38576087271994});
	}

	
	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {
		
		Tree tree = new Tree(new Node(stateObs));
		AStrategy strategy = new LevelOrderStrategy(tree,heuristic);
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
