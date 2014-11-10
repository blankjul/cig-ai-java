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
		heuristic = new EquationStateHeuristic(new double[] {71.51606955238063,-0.10874248901326666,1.46935755801519,58.91949024357237,-46.09021025115321,-57.43379973569722,57.57362881912201,-73.6456264953129,-31.50978515374345,-52.41586298782184});
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
