import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import emergence_HR.heuristics.EquationStateHeuristic;
import emergence_HR.heuristics.GameResult;

public class PoolOneGames {

	public static String CONTROLLER = "emergence_HR.StaticAgent";
	public static String GAME = "camelRace";
	public static int NUM_LEVELS = 5;
	public static int POOL_SIZE = 10;

	public static ArrayList<EquationStateHeuristic> pool = new ArrayList<EquationStateHeuristic>();
	public static ArrayList<Future<GameResult>> poolResult = new ArrayList<Future<GameResult>>();

	public static void playOneGame(String game) {

		pool.clear();
		System.out.println(Configuration.dateFormat.format(new Date()));
		for (int i = 0; i < POOL_SIZE; i++) {
			EquationStateHeuristic heuristic = EquationStateHeuristic.random();
			for (int j = 0; j < NUM_LEVELS; j++) {
				ExecCallable e = new ExecCallable(CONTROLLER, game, j,
						heuristic.parameter());
				Future<GameResult> future = Configuration.SCHEDULER.submit(e);
				heuristic.resultList.add(future);
			}
			pool.add(heuristic);
		}

		for (int i = 0; i < pool.size(); i++) {
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
		System.out.println("------------------------");
		System.out.println(GAME);
		System.out.println("------------------------");

		playOneGame(GAME);

		Configuration.SCHEDULER.shutdown();
	}

}
