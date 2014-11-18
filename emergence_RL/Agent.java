package emergence_RL;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_RL.helper.ActionTimer;
import emergence_RL.helper.Helper;
import emergence_RL.helper.LevelInfo;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;
import emergence_RL.uct.UCTFactory;
import emergence_RL.uct.UCTSearch;
import emergence_RL.uct.UCTSettings;

public class Agent extends AThreadablePlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = true;

	// finally the settings for the tree!
	private UCTSettings settings = UCTFactory.createHeuristic();


	
	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		if (VERBOSE) LevelInfo.print(stateObs);
		
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {
		
		// create a tree with a root
		Tree tree = new Tree(new Node(stateObs));
		UCTSearch uct = new UCTSearch(tree, settings);
		
		// set up the action timer.
		boolean hasNext = true;
		
		ActionTimer timer = new ActionTimer(elapsedTimer);
		while (timer.isTimeLeft() && hasNext) {
			uct.expand();
			timer.addIteration();
		}
		
		// act as the uct search says
		Types.ACTIONS a = uct.act();

		
		// print debug output
		if (VERBOSE) {
			System.out.println(uct);
			System.out.println("ACTION: " + a);
			
			if (settings.heuristic != null) {
				System.out.println("target: " + Helper.listToString(settings.heuristic.names));
				System.out.println("distance: " + Helper.listToString(settings.heuristic.distances));
				System.out.println("used: " + Helper.listToString(settings.heuristic.used));
				System.out.println("weights: " + Helper.listToString(settings.heuristic.weights));
				System.out.println("result: " + Helper.listToString(settings.heuristic.result));
			}
			System.out.println("--------------------------");
		}

		return a;

	}

	
	
	
	/*
	 * These two methods are need for multithreading simulation!
	 * It must be implemented by inherit from AThreadablePlayer
	 */
	
	@Override
	public String setToString() {
		return settings.toString();
	}
	
	
	@Override
	public void initFromString(String parameter) {
		this.settings = UCTSettings.create(parameter);
	}

}
