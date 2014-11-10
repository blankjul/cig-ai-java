
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import emergence_HR.heuristics.EquationStateHeuristic;
import emergence_HR.heuristics.GameResult;

public class PoolAllGames {


	public static String CONTROLLER = "emergence_HR.StaticAgent";
	public static String GAME = "camelRace";
	public static int NUM_LEVELS = 5;
	public static int POOL_SIZE = 10;

	public static ArrayList<EquationStateHeuristic> pool = new ArrayList<EquationStateHeuristic>();
	public static ArrayList<Future<GameResult>> poolResult = new ArrayList<Future<GameResult>>();


	public static void main(String[] args) throws InterruptedException,
			ExecutionException {

		// compile the classes
		String out = Compile.start();
		System.out.println(out);
		
		System.out.println("STARTING COMPETITION...");
		

		for (String strGame : Configuration.gamesAll) {
			System.out.println("------------------------");
			System.out.println(strGame);
			System.out.println("------------------------");
			PoolOneGames.playOneGame(strGame);
			
		}

		Configuration.SCHEDULER.shutdown();
	}

}
