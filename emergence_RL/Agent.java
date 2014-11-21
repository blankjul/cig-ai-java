package emergence_RL;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_RL.helper.ActionTimer;
import emergence_RL.helper.Helper;
import emergence_RL.helper.LevelInfo;
import emergence_RL.heuristic.AHeuristic;
import emergence_RL.strategies.AStrategy;
import emergence_RL.strategies.AStar.AStarStrategy;
import emergence_RL.strategies.uct.UCTFactory;
import emergence_RL.strategies.uct.UCTSearch;
import emergence_RL.strategies.uct.UCTSettings;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class Agent extends AThreadablePlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = true;

	// finally the settings for the tree!
	private UCTSettings settings = UCTFactory.createHeuristic();
	
	// heuristic that should be used if constructor found a good solution!
	private AHeuristic heuristic = null;

	
	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		
		if (VERBOSE) LevelInfo.print(stateObs);

		/*
		Tree tree = new Tree(new Node(stateObs));
		
		ArrayList<AStarStrategy> pool = new ArrayList<AStarStrategy>();
		for (AHeuristic heuristic : EquationStateHeuristic.create(tree)) {
			pool.add(new AStarStrategy(tree, heuristic));
		}
		
		ActionTimer timer = new ActionTimer(elapsedTimer);
		boolean hasNext = true;
		while (timer.isTimeLeft() && hasNext) {
			for (AStarStrategy strategy : pool) {
				strategy.expand();
			}
			timer.addIteration();
		}
		
		for (AStarStrategy s : pool) {
			double d =  s.bestNode.stateObs.getGameScore();
			System.out.println(d);
			if (s.bestNode.stateObs.getGameWinner() == WINNER.PLAYER_WINS) {
				heuristic = s.heuristic;
				break;
			}
		}
		
		
		if (heuristic == null) System.out.println("USE UCTSearch"); 
		else System.out.println("USE AStarStrategy with Heuristic");
		 */
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {
		
		// create a tree with a root
		Tree tree = new Tree(new Node(stateObs));
		
		AStrategy uct = (heuristic == null) ? new UCTSearch(tree, settings) : new AStarStrategy(tree, heuristic);
		
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
			System.out.println(settings);
			if (settings.heuristic != null) {
				System.out.println("distance: " + Helper.listToString(settings.heuristic.distances));
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
