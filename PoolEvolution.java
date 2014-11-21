import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import emergence_RL.GameResult;
import emergence_RL.helper.Helper;
import emergence_RL.helper.Pair;
import emergence_RL.strategies.uct.UCTSettings;
import emergence_RL.strategies.uct.UCTFactory;

public class PoolEvolution {

	public String CONTROLLER = "emergence_RL.Agent";
	public int NUM_LEVELS = 5;
	public int POOL_SIZE = 12;
	public int NUM_FITTEST = 4;
	public int NUM_GENERATION = 10;

	public ArrayList<Pair<UCTSettings, Integer>> pool = new ArrayList<Pair<UCTSettings, Integer>>();

	public ArrayList<String> games = new ArrayList<String>(Arrays.asList(Configuration.training));

	public Random r = new Random();

	
	private Integer getWins(ArrayList<String> games, UCTSettings settings) {
		Integer wins = playAllGames(games, settings);
		//Integer wins = r.nextInt();
		return wins;
	}
	
	
	public void start() {
		// print the time
		System.out.println(Configuration.dateFormat.format(new Date()));

		// initialize the pool
		for (int i = 0; i < POOL_SIZE; i++) {
			UCTSettings settings = UCTFactory.random(r);
			
			/*
			settings.weights = UCTFactory.randomWeights(r);
			settings.maxDepth = UCTFactory.randomMaxDepth(r);
			settings.gamma = UCTFactory.randomGamma(r);
			*/
			Integer wins = getWins(games, settings);
			pool.add(new Pair<UCTSettings, Integer>(settings, wins));
		}

		for (int j = 0; j < NUM_GENERATION; j++) {

			
			// survival of the fittest
			Collections.sort(pool);
			
			// print the whole pool
			System.out.println("-----------------------------");
			System.out.println("GEN " + j);
			System.out.println("-----------------------------");
			for (Pair<UCTSettings, Integer> entry : pool) {
				String s= String.format("[wins:%s] %s", entry.getSecond(), entry.getFirst());
				System.out.println(s);
			}
			
			ArrayList<Pair<UCTSettings, Integer>> nextPool = new ArrayList<Pair<UCTSettings, Integer>>();
			for (int i = 0; i < NUM_FITTEST; i++) {
				UCTSettings s = pool.get(i).getFirst();
				Integer wins = getWins(games, s);
				nextPool.add(new Pair<UCTSettings, Integer>(s, wins));
			}

			while (nextPool.size() < POOL_SIZE) {

				UCTSettings selected = Helper.getRandomEntry(nextPool, r)
						.getFirst();
				UCTSettings entry = UCTSettings.create(selected.toString());

				// mutate
				if (r.nextDouble() < 0.4) {
					if (r.nextDouble() < 0.2)
						entry.weights[0] = UCTFactory.randomWeight(r);
					if (r.nextDouble() < 0.2)
						entry.weights[1] = UCTFactory.randomWeight(r);
					if (r.nextDouble() < 0.2)
						entry.weights[2] = UCTFactory.randomWeight(r);
					if (r.nextDouble() < 0.2)
						entry.weights[3] = UCTFactory.randomWeight(r);
					if (r.nextDouble() < 0.2)
						entry.maxDepth = UCTFactory.randomMaxDepth(r);
					if (r.nextDouble() < 0.2)
						entry.gamma = UCTFactory.randomGamma(r);
					
					if (r.nextDouble() < 0.2)
						entry.defaultPolicy = UCTFactory.randomDefaultPolicy(r);
					if (r.nextDouble() < 0.2)
						entry.treePolicy = UCTFactory.randomTreePolicy(r);
					if (r.nextDouble() < 0.2)
						entry.actor = UCTFactory.randomActor(r);

					
					// crossover
				} else {

					// select a second one that is not the first!
					ArrayList<Pair<UCTSettings, Integer>> tmp = new ArrayList<Pair<UCTSettings, Integer>>();
					for (Pair<UCTSettings, Integer> pair : nextPool) {
						if (pair.getFirst() != selected) tmp.add(pair);
					}
					
					UCTSettings second = Helper.getRandomEntry(tmp, r)
							.getFirst();
					entry.weights[0] = (r.nextDouble() < 0.5) ? selected.weights[0]
							: second.weights[0];
					entry.weights[1] = (r.nextDouble() < 0.5) ? selected.weights[1]
							: second.weights[1];
					entry.weights[2] = (r.nextDouble() < 0.5) ? selected.weights[2]
							: second.weights[2];
					entry.weights[3] = (r.nextDouble() < 0.5) ? selected.weights[3]
							: second.weights[3];
					entry.maxDepth = (r.nextDouble() < 0.5) ? selected.maxDepth
							: second.maxDepth;
					entry.gamma = (r.nextDouble() < 0.5) ? selected.maxDepth
							: second.maxDepth;
					entry.treePolicy = (r.nextDouble() < 0.5) ? selected.treePolicy
							: second.treePolicy;
					entry.defaultPolicy = (r.nextDouble() < 0.5) ? selected.defaultPolicy
							: second.defaultPolicy;
					entry.actor = (r.nextDouble() < 0.5) ? selected.actor
							: second.actor;
					
				}
				Integer wins = getWins(games, entry);
				nextPool.add(new Pair<UCTSettings, Integer>(entry, wins));
			}

			pool = nextPool;
		}

		System.out.println(Configuration.dateFormat.format(new Date()));
	}

	public Integer playAllGames(ArrayList<String> games, UCTSettings settings) {

		ArrayList<Future<GameResult>> results = new ArrayList<Future<GameResult>>();
		for (String game : games) {
			for (int j = 0; j < NUM_LEVELS; j++) {
				ExecCallable e = new ExecCallable(CONTROLLER, game, j,
						settings.toString());
				Future<GameResult> future = Configuration.SCHEDULER.submit(e);
				results.add(future);
			}
		}

		Integer wins = 0;
		for (Future<GameResult> f : results) {
			GameResult g;
			try {
				g = f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return -1;
			} catch (ExecutionException e) {
				e.printStackTrace();
				return -1;
			}
			wins += g.getWin();
		}

		return wins;

	}

	public static void main(String[] args) {

		PoolEvolution evo = new PoolEvolution();
		evo.start();

	}

}
