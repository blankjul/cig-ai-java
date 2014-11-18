import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import emergence_RL.GameResult;
import emergence_RL.uct.UCTSettings;
import emergence_RL.uct.UCTSettingsFactory;

public class PoolOneGame {

	public static String CONTROLLER = "emergence_RL.Agent";
	public static String GAME = "frogs";
	public static int NUM_LEVELS = 5;
	public static int POOL_SIZE = 50;

	
	public static ArrayList<UCTSettings> pool = new ArrayList<UCTSettings>();
	public static ArrayList<Future<GameResult>> poolResult = new ArrayList<Future<GameResult>>();

	public static Random r = new Random();

	public static void playOneGame(String game) {

		pool.clear();
		System.out.println(Configuration.dateFormat.format(new Date()));

		ArrayList<ArrayList<Future<GameResult>>> allResultList = new ArrayList<ArrayList<Future<GameResult>>>();

		for (int i = 0; i < POOL_SIZE; i++) {
			
			ArrayList<Future<GameResult>> gameResultList = new ArrayList<Future<GameResult>> ();
			
			UCTSettings settings = UCTSettingsFactory.random(r)	;
			for (int j = 0; j < NUM_LEVELS; j++) {
				ExecCallable e = new ExecCallable(CONTROLLER, game, j,
						settings.toString());
				Future<GameResult> future = Configuration.SCHEDULER.submit(e);
				gameResultList.add(future);
			}
			
			allResultList.add(gameResultList);
			pool.add(settings);
		}

		for (int i = 0; i < pool.size(); i++) {
			UCTSettings s = pool.get(i);
			ArrayList<Future<GameResult>> gameResultList = allResultList.get(i);
			System.out.println(getResult(gameResultList) + " -> " + s);
		}

		System.out.println(Configuration.dateFormat.format(new Date()));

	}

	public static String getResult(ArrayList<Future<GameResult>> resultList) {
		int size = resultList.size();
		double avgScore = 0;
		double wins = 0;
		for (Future<GameResult> f : resultList) {
			GameResult g;
			try {
				g = f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return "ERROR";
			} catch (ExecutionException e) {
				e.printStackTrace();
				return "ERROR";
			}
			avgScore += g.getScore();
			wins += g.getWin();
		}
		avgScore /= size;
		return String.format("wins:%s | avgScore:%s", wins, avgScore);
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
