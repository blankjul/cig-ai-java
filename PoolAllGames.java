
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import emergence_HR.heuristics.EquationStateHeuristic;
import emergence_HR.heuristics.GameResult;

public class PoolAllGames {


	public static String CONTROLLER = "emergence_HR.StaticAgent";
	
	public static boolean playAllGames = true;
	public static String GAME = "camelRace";

	public static int NUM_LEVELS = 5;
	public static int POOL_SIZE = 10;

	public static ArrayList<EquationStateHeuristic> pool = new ArrayList<EquationStateHeuristic>();
	public static ArrayList<Future<GameResult>> poolResult = new ArrayList<Future<GameResult>>();

	

	public static void playOneGame(String game) {
	
		pool.clear();
		
		System.out.println(Configuration.dateFormat.format(new Date()));
		
		for (int i = 0; i < POOL_SIZE; i++) {
			EquationStateHeuristic heuristic = EquationStateHeuristic
					.random();
			for (int j = 0; j < NUM_LEVELS; j++) {
				ExecCallable e = new ExecCallable(CONTROLLER, game, j, heuristic.toString());
				Future<GameResult> future = Configuration.SCHEDULER.submit(e);
				heuristic.resultList.add(future);
			}
			pool.add(heuristic);
		}
		
		for (EquationStateHeuristic h : pool) {
			h.fitness = h.getResult();
		}

		Collections.sort(pool, new Comparator<EquationStateHeuristic>() {
			@Override
			public int compare(EquationStateHeuristic o1,
					EquationStateHeuristic o2) {
				double heurFirst = o1.fitness;
				double heurSecond = o2.fitness;
				if (heurFirst < heurSecond) {
					return 1;
				} else if (heurFirst > heurSecond) {
					return -1;
				} else {
					return 0;
				}
			}
		});

		for (int i = 0; i < pool.size() && i < 3; i++) {
			EquationStateHeuristic h = pool.get(i);
			System.out.println(h + " --> " + h.getResult());
		}

		System.out.println(Configuration.dateFormat.format(new Date()));

	}

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {

		// compile the classes
		String out = Compile.start();
		System.out.println(out);

		
		System.out.println("STARTING COMPETITION...");
		
		ArrayList<String> gamesToPlay = new ArrayList<String>();
		if (playAllGames) {
			gamesToPlay.addAll(Arrays.asList(Configuration.gamesAll));
		} else {
			gamesToPlay.add(GAME);
		}

		for (String strGame : gamesToPlay) {
			System.out.println("------------------------");
			System.out.println(strGame);
			System.out.println("------------------------");

			playOneGame(strGame);
			
		}

		

		Configuration.SCHEDULER.shutdown();
	}

}
