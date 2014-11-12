package emergence_RL;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_RL.helper.ActionTimer;
import emergence_RL.strategy.AStrategy;
import emergence_RL.strategy.FlatMonteCarlo;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class Agent extends AThreadablePlayer {

	// print out information. only DEBUG!
	//final private boolean VERBOSE = false;

	private int nSteps = 15;

	
	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
	}
	

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		Tree tree = new Tree(new Node(stateObs));
		AStrategy strategy = new FlatMonteCarlo(tree, nSteps);
		
		
		boolean hasNext = true;
		ActionTimer timer = new ActionTimer(elapsedTimer);
		while (timer.isTimeLeft() && hasNext) {
			strategy.expand();
			timer.addIteration();
		}
		

		return strategy.act();
	}


	
	@Override
	public
	void initFromString(String parameter) {
		this.nSteps = Integer.valueOf(parameter);
	}


	@Override
	public
	String setToString() {
		return String.valueOf(nSteps);
	}

}
