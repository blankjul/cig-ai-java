package emergence_RL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_RL.helper.ActionTimer;
import emergence_RL.helper.LevelInfo;
import emergence_RL.helper.Pair;
import emergence_RL.heuristic.TargetHeuristic;
import emergence_RL.strategies.AStrategy;
import emergence_RL.strategies.uct.UCTSearch;
import emergence_RL.strategies.uct.UCTSettings;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class TestAgent extends AThreadablePlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = false;

	// finally the settings for the tree!
	private UCTSettings settings;

	// all heuristics that are possible!
	public static Set<TargetHeuristic> heuristics;

	// a pool of possible uct settings
	private ArrayList<Pair<UCTSearch, Double>> pool;

	public int POOL_SIZE = 5;

	public Random r;

	private int counter = 0;

	
	
	public TestAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		// initialize all the heuristics
		heuristics = new HashSet<TargetHeuristic>();
		for (int[] weights : TargetHeuristic.weights(stateObs)) {
			heuristics.add(new TargetHeuristic(weights));
		}
		
		// initialize the pool
		pool = new ArrayList<Pair<UCTSearch, Double>>();
		r = new Random();
		
		// add five random mcts tree
		ArrayList<UCTSettings> init = Evolution.initPool(r, POOL_SIZE);
		for (UCTSettings uctSettings : init) {
			Tree tree = new Tree(new Node(stateObs));
			pool.add(new Pair<UCTSearch, Double>(new UCTSearch(tree,
					uctSettings), 0d));
		}

		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 50;
		while (timer.isTimeLeft()) {
			Pair<UCTSearch, Double> pair = pool.get(counter % pool.size());
			++counter;
			pair.getFirst().expand();
			timer.addIteration();
		}
		
		
		for (Pair<UCTSearch, Double> p : pool) {
			double score = 0;
			for (Node child : p.getFirst().tree.root.getChildren()) {
				score += child.Q / child.visited;
			}
			p.setSecond(score);
		}
		
		Collections.sort(pool);
		settings = pool.get(0).getFirst().s;
		
		if (VERBOSE) {
			LevelInfo.print(stateObs);
			System.out.println(settings);
			if (settings.heuristic != null) System.out.println(Arrays.toString(settings.heuristic.weights));
		}

	}

	
	
	
	
	
	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {


		// create a tree with a root
		Tree tree = new Tree(new Node(stateObs));
		AStrategy uct = new UCTSearch(tree, settings);

		ActionTimer timer = new ActionTimer(elapsedTimer);
		while (timer.isTimeLeft()) {
			uct.expand();
			timer.addIteration();
		}

		// act as the uct search says
		Types.ACTIONS a = uct.act();
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
		//this.settings = UCTSettings.create(parameter);
	}

}
