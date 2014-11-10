package exec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import emergence_HR.heuristics.EquationStateHeuristic;
import emergence_HR.heuristics.GameResult;

public class Competition {

	public final static int NUM_THREADS = 4;

	public static ExecutorService SCHEDULER = Executors
			.newFixedThreadPool(NUM_THREADS);

	public static String CONTROLLER = "emergence_HR.StaticAgent";
	
	public static boolean playAllGames = true;
	public static String GAME = "camelRace";

	public static int NUM_LEVELS = 5;
	public static int POOL_SIZE = 10;

	public static ArrayList<EquationStateHeuristic> pool = new ArrayList<EquationStateHeuristic>();
	public static ArrayList<Future<GameResult>> poolResult = new ArrayList<Future<GameResult>>();

	public static String gamesAll[] = new String[] { "aliens", "boulderdash",
			"butterflies", "chase", "frogs", "missilecommand", "portals",
			"sokoban", "survivezombies", "zelda", "camelRace", "digdug",
			"firestorms", "infection", "firecaster", "overload", "pacman",
			"seaquest", "whackamole", "eggomania" };
	
	
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	
	

	public static void playOneGame(String game) {
	
		pool.clear();
		// print the date
		System.out.println(dateFormat.format(new Date()));
		
		for (int i = 0; i < POOL_SIZE; i++) {
			EquationStateHeuristic heuristic = EquationStateHeuristic
					.random();
			for (int j = 0; j < NUM_LEVELS; j++) {
				ExecCallable e = new ExecCallable(CONTROLLER, game, j, heuristic.toString());
				Future<GameResult> future = SCHEDULER.submit(e);
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

		System.out.println(dateFormat.format(new Date()));

	}

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {

		// compile the classes
		String out = Exec.compile();
		System.out.println(out);

		
		System.out.println("STARTING COMPETITION...");
		
		ArrayList<String> gamesToPlay = new ArrayList<String>();
		if (playAllGames) {
			gamesToPlay.addAll(Arrays.asList(gamesAll));
		} else {
			gamesToPlay.add(GAME);
		}

		for (String strGame : gamesToPlay) {
			System.out.println("------------------------");
			System.out.println(strGame);
			System.out.println("------------------------");

			playOneGame(strGame);
			
		}

		

		SCHEDULER.shutdown();
	}

}
