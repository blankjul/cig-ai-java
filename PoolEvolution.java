import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import emergence_NI.Agent;
import emergence_NI.GameResult;
import emergence_NI.helper.Helper;
import emergence_NI.helper.Pair;

public class PoolEvolution {

	public String CONTROLLER = "emergence_NI.Agent";
	public int NUM_LEVELS = 5;
	public int POOL_SIZE = 6;
	public int NUM_FITTEST = 3;
	public int NUM_GENERATION = 10;

	public ArrayList<Pair<Agent, Integer>> pool = new ArrayList<Pair<Agent, Integer>>();

	public ArrayList<String> games = new ArrayList<String>(
			Arrays.asList(Configuration.training));
	
	//public ArrayList<String> games = new ArrayList<String>(
	//		Arrays.asList(new String[] {"aliens"}));

	public Random r = new Random();

	private Integer getWins(ArrayList<String> games, Agent agent) {
		Integer wins = playAllGames(games, agent);
		//Integer wins = r.nextInt();
		return wins;
	}

	public void start() {
		// print the time
		System.out.println(Configuration.dateFormat.format(new Date()));

		// initialize the pool
		for (int i = 0; i < POOL_SIZE; i++) {

			// generate random agent
			Agent a = new Agent();

			
			int wins = getWins(games, a);
			pool.add(new Pair<Agent, Integer>(a, wins));

		}

		for (int j = 0; j < NUM_GENERATION; j++) {

			// survival of the fittest
			Collections.sort(pool);

			// print the whole pool
			System.out.println("-----------------------------");
			System.out.println("GEN " + j);
			System.out.println("-----------------------------");
			for (Pair<Agent, Integer> entry : pool) {
				String s = String.format("[wins:%s] %s", entry.getSecond(),
						entry.getFirst().printToString());
				System.out.println(s);
			}

			ArrayList<Pair<Agent, Integer>> nextPool = new ArrayList<Pair<Agent, Integer>>();
			for (int i = 0; i < NUM_FITTEST; i++) {
				Agent s = pool.get(i).getFirst();
				Integer wins = getWins(games, s);
				nextPool.add(new Pair<Agent, Integer>(s, wins));
			}

			while (nextPool.size() < POOL_SIZE) {

				Agent selected = Helper.getRandomEntry(nextPool, r).getFirst();

				Agent entry = new Agent();
				
				// mutate
				if (r.nextDouble() < 0.6) {
	
					// crossover
				} else {

					// select a second one that is not the first!
					ArrayList<Pair<Agent, Integer>> tmp = new ArrayList<Pair<Agent, Integer>>();
					for (Pair<Agent, Integer> pair : nextPool) {
						if (pair.getFirst() != selected)
							tmp.add(pair);
					}

					Agent second = Helper.getRandomEntry(tmp, r)
							.getFirst();



				}
				Integer wins = getWins(games, entry);
				nextPool.add(new Pair<Agent, Integer>(entry, wins));
			}

			pool = nextPool;
		}
	}

	public Integer playAllGames(ArrayList<String> games, Agent agent) {

		ArrayList<Future<GameResult>> results = new ArrayList<Future<GameResult>>();
		for (String game : games) {
			for (int j = 0; j < NUM_LEVELS; j++) {
				ExecCallable e = new ExecCallable(CONTROLLER, game, j,
						agent.printToString());
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
