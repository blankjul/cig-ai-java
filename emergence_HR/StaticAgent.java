package emergence_HR;

import java.util.ArrayList;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.Observation;
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
		heuristic = new EquationStateHeuristic(new double[] {-61.28855920277611,-56.53843161436507,-7.727502372130047,-7.825813073193544,-88.2193611040861,-32.97691123985298,81.61561937850556,69.319187706717,87.40684195794742,46.98539700279494});
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
