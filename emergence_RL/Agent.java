package emergence_RL;

import java.util.ArrayList;
import java.util.Collections;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_RL.helper.ActionTimer;
import emergence_RL.helper.LevelInfo;
import emergence_RL.heuristic.TargetHeuristic;
import emergence_RL.strategies.AEvolutionaryStrategy;
import emergence_RL.strategies.AStarStrategy;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class Agent extends AThreadablePlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = false;

	// a pool of possible uct settings
	private ArrayList<AEvolutionaryStrategy> pool;

	// the current uct search that is used for acting
	private AEvolutionaryStrategy strategy = null;

	
	public int EVO_GAME_TICK = 200;
	public int POOL_SIZE = 12;
	public int POOL_FITTEST = 4;
	public int TIME_FOR_EVOLUTION = 22;
	
	private int counter = 0;

	public Agent() {};

	
	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		// initialize the static objects
		FieldTracker.reset();
		TargetHeuristic.createAll(stateObs);
		

		// initialize the pool
		pool = new ArrayList<AEvolutionaryStrategy>();
		for (AEvolutionaryStrategy strategy : Evolution.initPool(POOL_SIZE, stateObs)) {
			strategy.tree = new Tree(new Node(stateObs));
			pool.add(strategy);
		}

		// simulate until there is no time left!
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 50;
		double schedule = timer.getRemaining() - 40;
		while (timer.isTimeLeft()) {
			AEvolutionaryStrategy evo = pool.get(counter % pool.size());
			evo.expand();
			timer.addIteration();
			if (timer.getRemaining() < schedule) {
				++counter;
				schedule = timer.getRemaining() - 40;
			}
		}
		

		Collections.sort(pool);
		strategy = pool.get(0);
		if (strategy.getScore() <= 0) {
			strategy = new AStarStrategy(new Tree(new Node(stateObs)), new TargetHeuristic(new int[] {0,0,0,1,0,0,0,0,0,0,0,0}));
		}

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
		strategy.tree = new Tree(new Node(stateObs));;
		
		// Astar needs a reset
		if (strategy instanceof AStarStrategy) {
			((AStarStrategy)strategy).init(new Tree(new Node(stateObs))); 
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

		
		timer = new ActionTimer(elapsedTimer);
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
			ArrayList<AEvolutionaryStrategy> nextPool = Evolution.createNextGeneration(stateObs, pool, POOL_FITTEST,
					POOL_SIZE, 0.7);
			pool.clear();
			pool = nextPool;
			counter = 0;

		} else {
			
			// simulate the pool for the next best strategy
			timer = new ActionTimer(elapsedTimer);
			timer.timeRemainingLimit = 5;
			while (timer.isTimeLeft()) {
				AEvolutionaryStrategy evo = pool.get(counter % pool.size());
				evo.expand();
				timer.addIteration();
				++counter;
			}

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
