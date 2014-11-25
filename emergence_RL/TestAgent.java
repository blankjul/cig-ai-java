package emergence_RL;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_RL.helper.ActionMap;
import emergence_RL.helper.ActionTimer;
import emergence_RL.helper.LevelInfo;
import emergence_RL.strategies.UCT.UCTSearch;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class TestAgent extends AbstractPlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = true;

	// the current uct search that is used for acting
	private UCTSearch uct;


	public TestAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		uct = new UCTSearch();
		Agent.map = new ActionMap(stateObs.getAvailableActions());
		LevelInfo.print(stateObs);

	}

	
	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		uct.tree = new Tree(new Node(stateObs));

		// get the next best action that will be executed
		ActionTimer timer = new ActionTimer(elapsedTimer);
		while (timer.isTimeLeft()) {
			uct.expand();
			timer.addIteration();
		}
		Types.ACTIONS a = uct.act();

		if (VERBOSE) {
			System.out.println("----------------------");
			System.out.println(uct);
			System.out.println(uct.status());
			System.out.println("SELECT: " + a);
			System.out.println("----------------------");
		}

		// act as the uct search says
		return a;

	}


}
