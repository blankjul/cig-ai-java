package emergence_RL;

import java.util.ArrayList;
import java.util.Collections;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_RL.helper.ActionMap;
import emergence_RL.helper.ActionTimer;
import emergence_RL.helper.LevelInfo;
import emergence_RL.heuristic.TargetHeuristic;
import emergence_RL.strategies.AEvolutionaryStrategy;
import emergence_RL.strategies.AStarStrategy;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class Agent extends AThreadablePlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = true;

	// a pool of possible uct settings
	private ArrayList<AEvolutionaryStrategy> pool;

	// map from action to int and arround.
	public static ActionMap map;

	// the current uct search that is used for acting
	private AEvolutionaryStrategy strategy = null;

	public int EVO_GAME_TICK = 200;
	public int POOL_SIZE = 20;
	public int POOL_FITTEST = 6;
	public int TIME_FOR_EVOLUTION = 12;
	

	private int counter = 0;

	public Agent() {
	};

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		// initialize the static objects
		TargetHeuristic.createAll(stateObs);
		map = new ActionMap(stateObs.getAvailableActions());

		// initialize the pool
		pool = new ArrayList<AEvolutionaryStrategy>();
		for (AEvolutionaryStrategy strategy : Evolution.initPool(POOL_SIZE, stateObs)) {
			strategy.tree = new Tree(new Node(stateObs));
			pool.add(strategy);
		}

		// simulate until there is no time left!
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 50;
		double schedule = timer.getRemaining() - 20;
		while (timer.isTimeLeft()) {
			AEvolutionaryStrategy evo = pool.get(counter % pool.size());
			evo.expand();
			timer.addIteration();
			if (timer.getRemaining() < schedule) {
				++counter;
				schedule = timer.getRemaining() - 20;
			}
		}
		

		Collections.sort(pool);
		strategy = pool.get(0);

		printPool(stateObs.getGameTick());

		if (VERBOSE) {
			LevelInfo.print(stateObs);
			System.out.println(strategy);
		}

	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		// track the current field and create a tree with a root
		FieldTracker.track(stateObs);
		TargetHeuristic.createAll(stateObs);
		map = new ActionMap(stateObs.getAvailableActions());
		strategy.tree = new Tree(new Node(stateObs));
		
		// Astar needs a reset
		if (strategy instanceof AStarStrategy) {
			strategy = new AStarStrategy(strategy.tree, ((AStarStrategy) strategy).heuristic);
		}

		// get the next best action that will be executed
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = TIME_FOR_EVOLUTION;
		while (timer.isTimeLeft()) {
			strategy.expand();
			timer.addIteration();
		}
		Types.ACTIONS a = strategy.act();
		FieldTracker.lastAction = a;

		// normally just simulate
		if (stateObs.getGameTick() != 0
				&& stateObs.getGameTick() % EVO_GAME_TICK == 0) {

			// rank the pool and print it
			Collections.sort(pool);
			strategy = pool.get(0);
			
			// if even the best strategy has zero score
			if (strategy.getScore() <= 0) {
				strategy = new AStarStrategy(new Tree(new Node(stateObs)), TargetHeuristic.createRandom());
			}
			
			printPool(stateObs.getGameTick());
			

			// create a new generation
			pool = Evolution.createNextGeneration(stateObs, pool, POOL_FITTEST,
					POOL_SIZE, 0.7);
			
			counter = 0;

		} else {
			// simulate the pool for the next best strategy
			timer = new ActionTimer(elapsedTimer);
			while (timer.isTimeLeft()) {
				AEvolutionaryStrategy evo = pool.get(counter % pool.size());
				evo.expand();
				timer.addIteration();
			}
			++counter;

		}

		if (VERBOSE) {
			System.out.println("----------------------");
			System.out.println(strategy);
			System.out.println("SELECT: " + a);
			System.out.println("----------------------");
		}

		// act as the uct search says
		return a;

	}

	private void printPool(int gameTick) {
		System.out.println("------------------");
		System.out.println(gameTick);
		System.out.println("------------------");
		int counter = 0;
		for (AEvolutionaryStrategy evo : pool) {
			System.out.println(counter + " " + evo.getScore() + "->" + evo);
			++counter;
		}
		System.out.println("ACTING: " + strategy);
		System.out.println("------------------");
	}



	
	@Override
	public String printToString() {
		String s = String.format(
				"evo_tick:%s pool_size:%s pool_fittest:%s evo_time:%s",
				EVO_GAME_TICK, POOL_SIZE, POOL_FITTEST, TIME_FOR_EVOLUTION);
		return s;
	}

	@Override
	public void createFromString(String parameter) {
		// set the correct actor
		if (parameter == null || parameter.equals(""))
			return;
		String[] array = parameter.split(" ");
		for (String s : array) {
			String key = s.split(":")[0];
			String value = s.split(":")[1];
			if (key.equals("evo_tick")) {
				this.EVO_GAME_TICK = Integer.valueOf(value);
			} else if (key.equals("pool_size")) {
				this.POOL_SIZE = Integer.valueOf(value);
			} else if (key.equals("pool_fittest")) {
				this.POOL_FITTEST = Integer.valueOf(value);
			} else if (key.equals("evo_time")) {
				this.TIME_FOR_EVOLUTION = Integer.valueOf(value);
			}
		}

	}

}
