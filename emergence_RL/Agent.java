package emergence_RL;

import java.util.ArrayList;
import java.util.Collections;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
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
	
	private UCTSearch uct = null;

	public int POOL_SIZE = 5;

	private int counter = 0;

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		// initialize the pool
		pool = new ArrayList<Pair<UCTSearch, Double>>();

		// add five random mcts tree
		ArrayList<UCTSearch> init = Evolution.initPool(UCTSearch.r, POOL_SIZE,
				stateObs);
		for (UCTSearch search : init) {
			search.tree = new Tree(new Node(stateObs));
			pool.add(new Pair<UCTSearch, Double>(search, 0d));
		}

		// simulate until there is no time left!
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 50;

		simulate(timer);
		uct = rankPool();

		if (VERBOSE) {
			LevelInfo.print(stateObs);
			System.out.println(uct);
		}

	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		// create a tree with a root
		uct.tree = new Tree(new Node(stateObs));

		ActionTimer timer = new ActionTimer(elapsedTimer);
		while (timer.isTimeLeft()) {
			uct.expand();
			timer.addIteration();
		}

		
		// act as the uct search says
		Types.ACTIONS a = uct.act();
		return a;

	}

	/**
	 * Simulate until there is no time left.
	 * @param timer
	 * @return
	 */
	private void simulate(ActionTimer timer) {
		// simulate until there is no time left
		while (timer.isTimeLeft()) {
			Pair<UCTSearch, Double> pair = pool.get(counter % pool.size());
			++counter;
			pair.getFirst().expand();
			timer.addIteration();
		}
	}

	
	/**
	 * Calculate the score of each uct search and return the best one!
	 * @return best uct search
	 */
	private UCTSearch rankPool() {
		// calculate the score of each uct search
		for (Pair<UCTSearch, Double> p : pool) {
			double score = 0;
			for (Node child : p.getFirst().tree.root.getChildren()) {
				score += child.Q / child.visited;
			}
			p.setSecond(score);
		}

		// sort and return the best one
		Collections.sort(pool);
		return pool.get(0).getFirst();
	}

	
	/*
	 * These two methods are need for multithreading simulation!
	 * It must be implemented by inherit from AThreadablePlayer
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

}
