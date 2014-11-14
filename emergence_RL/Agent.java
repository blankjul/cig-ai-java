package emergence_RL;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_RL.helper.ActionTimer;
import emergence_RL.strategy.AStrategy;
import emergence_RL.strategy.UCTSearch;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class Agent extends AThreadablePlayer {

	// print out information. only DEBUG!
	final private boolean VERBOSE = true;

	private int maxDepth = 10;
	private double C = 0.7;

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		Tree tree = new Tree(new Node(stateObs));
		AStrategy strategy = new UCTSearch(tree, maxDepth, C);

		boolean hasNext = true;
		ActionTimer timer = new ActionTimer(elapsedTimer);
		while (timer.isTimeLeft() && hasNext) {
			strategy.expand();
			timer.addIteration();
		}
		if (VERBOSE) {
			System.out.println(strategy);
			System.out.println("--------------------------");
		}
		
		Types.ACTIONS a = strategy.act();
		return a;
		
	}

	@Override
	public void initFromString(String parameter) {
		//this.maxDepth = Integer.valueOf(parameter);
	}

	@Override
	public String setToString() {
		return String.valueOf(maxDepth);
	}

}
