package emergence_RL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_RL.helper.ActionMap;
import emergence_RL.helper.ActionTimer;
import emergence_RL.helper.LevelInfo;
import emergence_RL.helper.Pair;
import emergence_RL.strategies.UCT.UCTSearch;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class Agent extends AThreadablePlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = false;

	// a pool of possible uct settings
	private ArrayList<Pair<UCTSearch, Double>> pool;

	// map from action to int and arround.
	public static ActionMap map;

	// track the visited fields until yet!
	public static HashMap<String, Integer> fieldVisits = new HashMap<String, Integer>();
	public static int maxVisitedField = 0;
	public static Types.ACTIONS lastAction = Types.ACTIONS.ACTION_NIL;

	// the current uct search that is used for acting
	private UCTSearch uct = null;

	public int EVO_GAME_TICK = 200;
	public int POOL_SIZE = 20;
	public int POOL_FITTEST = 4;
	public int TIME_FOR_EVOLUTION = 22;

	private int counter = 0;

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		map = new ActionMap(stateObs.getAvailableActions());

		// initialize the pool and add five random mcts tree
		pool = new ArrayList<Pair<UCTSearch, Double>>();
		ArrayList<UCTSearch> init = Evolution.initPool(UCTSearch.r, POOL_SIZE,
				stateObs);
		for (UCTSearch search : init) {
			search.tree = new Tree(new Node(stateObs));
			pool.add(new Pair<UCTSearch, Double>(search, 0d));
		}

		// simulate until there is no time left!
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 50;
		while (timer.isTimeLeft()) {
			simulate();
			timer.addIteration();
		}
		uct = rankPool();
		//printPool(stateObs.getGameTick());

		if (VERBOSE) {
			LevelInfo.print(stateObs);
			System.out.println(uct);
		}

	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		// track the current field and create a tree with a root
		trackFields(stateObs, lastAction);
		uct.tree = new Tree(new Node(stateObs));

		// get the next best action that will be executed
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = TIME_FOR_EVOLUTION;
		while (timer.isTimeLeft()) {
			uct.expand();
			timer.addIteration();
		}
		lastAction = uct.act();

		// normally just simulate
		if (stateObs.getGameTick() != 0 && stateObs.getGameTick() % EVO_GAME_TICK == 0) {

			uct = rankPool();
			printPool(stateObs.getGameTick());


			// create a new generation
			// System.out.println(uct);
			pool = Evolution.createNextGeneration(stateObs, pool, POOL_FITTEST,
					POOL_SIZE, 0.7);

		} else {
			// simulate the pool for the next best mcts
			while (timer.isTimeLeft()) {
				simulate();
				timer.addIteration();
			}

		}
/*
		if (false) {
			System.out.println("----------------------");
			System.out.println(uct);
			System.out.println(uct.status());
			System.out.println("----------------------");
		}
		*/

		// act as the uct search says
		return lastAction;

	}

	private void printPool(int gameTick) {
		System.out.println("------------------");
		System.out.println(gameTick);
		System.out.println("------------------");
		int counter = 0;
		for (Pair<UCTSearch, Double> pair : pool) {
			System.out.println(counter + " " + pair.getSecond() + "->"
					+ pair.getFirst());
			++counter;
		}
		System.out.println("------------------");
	}
	
	/**
	 * Simulate until there is no time left.
	 */
	private void simulate() {
		Pair<UCTSearch, Double> pair = pool.get(counter % pool.size());
		++counter;
		pair.getFirst().expand();
	}

	/**
	 * Calculate the score of each uct search and return the best one!
	 * 
	 * @return best uct search
	 */
	private UCTSearch rankPool() {
		// calculate the score of each uct search
		for (Pair<UCTSearch, Double> p : pool) {
			UCTSearch search = p.getFirst();
			search.act();
			Node n = search.bestNode;
			double score = (n != null || n.visited != 0) ? n.Q / n.visited : 0;
			//System.out.println(score);
			p.setSecond(score);
		}

		// sort and return the best one
		Collections.sort(pool);
		return pool.get(0).getFirst();
	}

	/*
	 * These two methods are need for multithreading simulation! It must be
	 * implemented by inherit from AThreadablePlayer
	 */

	@Override
	public String setToString() {
		return "";
		// return settings.toString();
	}

	@Override
	public void initFromString(String parameter) {
		// this.settings = UCTSettings.create(parameter);
	}

	public void trackFields(StateObservation stateObs, Types.ACTIONS action) {
		// track the statistic of each field!
		String fieldHash = Node.hash(stateObs, action);
		boolean visited = fieldVisits.containsKey(fieldHash);
		if (visited) {
			int value = fieldVisits.get(fieldHash) + 1;
			if (value > maxVisitedField)
				value = maxVisitedField;
			fieldVisits.put(fieldHash, value);
		} else {
			if (1 > maxVisitedField)
				maxVisitedField = 1;
			fieldVisits.put(fieldHash, 1);
		}
	}

}
