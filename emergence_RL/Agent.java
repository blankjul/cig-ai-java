package emergence_RL;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_RL.helper.ActionTimer;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class Agent extends AbstractPlayer {

	// print out information. only DEBUG!
	final private boolean VERBOSE = false;


	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

	}
	

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		Tree tree = new Tree(new Node(stateObs));
		
		
		
		

		boolean hasNext = true;
		ActionTimer timer = new ActionTimer(elapsedTimer);
		while (timer.isTimeLeft() && hasNext) {
			timer.addIteration();
		}
		
		Types.ACTIONS action = Types.ACTIONS.ACTION_NIL;

		return action;
	}

}
