import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import emergence_RL.Agent;
import emergence_RL.GameResult;
import emergence_RL.helper.Helper;
import emergence_RL.helper.Pair;

public class PoolEvolution {

	public String CONTROLLER = "emergence_RL.Agent";
	public int NUM_LEVELS = 5;
	public int POOL_SIZE = 12;
	public int NUM_FITTEST = 4;
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
			a.EVO_GAME_TICK = r.nextInt(500 - 20) + 20;
			a.POOL_SIZE = r.nextInt(50 - 4) + 4;
			a.POOL_FITTEST = r.nextInt(a.POOL_SIZE - 3) + 3;
			a.TIME_FOR_EVOLUTION = r.nextInt(34 - 5) + 5;
			
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
						entry.getFirst().setToString());
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
					if (r.nextDouble() < 0.2)
						entry.EVO_GAME_TICK = r.nextInt(500 - 20) + 20;
					else entry.EVO_GAME_TICK = selected.EVO_GAME_TICK;
					
					if (r.nextDouble() < 0.2)
						entry.POOL_SIZE = r.nextInt(50 - 4) + 4;
					else entry.POOL_SIZE = selected.POOL_SIZE;
					
					if (r.nextDouble() < 0.2)
						entry.POOL_FITTEST =  r.nextInt(entry.POOL_SIZE - 3) + 3;
					else entry.POOL_FITTEST = selected.POOL_FITTEST;
					
					if (r.nextDouble() < 0.2)
						entry.TIME_FOR_EVOLUTION = r.nextInt(34 - 5) + 5;
					else entry.TIME_FOR_EVOLUTION = selected.TIME_FOR_EVOLUTION;

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

					entry.EVO_GAME_TICK = (r.nextDouble() < 0.5) ? second.EVO_GAME_TICK
							: selected.EVO_GAME_TICK;
					entry.POOL_SIZE = (r.nextDouble() < 0.5) ? second.POOL_SIZE
							: selected.POOL_SIZE;
					entry.POOL_FITTEST = (r.nextDouble() < 0.5) ? second.POOL_FITTEST
							: selected.POOL_FITTEST;
					entry.TIME_FOR_EVOLUTION = (r.nextDouble() < 0.5) ? second.TIME_FOR_EVOLUTION
							: selected.TIME_FOR_EVOLUTION;


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
						agent.setToString());
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
