package emergence_RL;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_RL.helper.ActionTimer;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;
import emergence_RL.uct.UCTFactory;
import emergence_RL.uct.UCTSearch;
import emergence_RL.uct.UCTSettings;

public class Agent extends AThreadablePlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = true;


	// finally the settings for the tree!
	private UCTSettings settings = UCTFactory.createDefault();


	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		Tree tree = new Tree(new Node(stateObs));
		UCTSearch uct = new UCTSearch(tree, settings);

		boolean hasNext = true;
		ActionTimer timer = new ActionTimer(elapsedTimer);
		while (timer.isTimeLeft() && hasNext) {
			uct.expand();
			timer.addIteration();
		}
		Types.ACTIONS a = uct.act();

		if (VERBOSE) {
			System.out.println(uct);
			System.out.println("ACTION: " + a);
			System.out.println("--------------------------");
		}

		return a;

	}

	
	@Override
	public String setToString() {
		return settings.toString();
	}
	
	
	@Override
	public void initFromString(String parameter) {
		this.settings = UCTSettings.create(parameter);
	}

}
