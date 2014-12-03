
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import emergence_NI.GameResult;

public class OneAgentAllGames {

	public static String CONTROLLER = "emergence_NI.Agent";

	public static String PARAMETER = "";
	
	public static int NUM_LEVELS = 5;

	//public static String[] GAMES = Helper.concat(Configuration.training, Configuration.validation);
	public static String[] GAMES = Configuration.training;

	public static ArrayList<Future<GameResult>> playOneGame(String game) {
		ArrayList<Future<GameResult>> res = new ArrayList<Future<GameResult>>();
		for (int j = 0; j < NUM_LEVELS; j++) {
			ExecCallable e = new ExecCallable(CONTROLLER, game, j, PARAMETER);
			Future<GameResult> future = Configuration.SCHEDULER.submit(e);
			res.add(future);
		}
		return res;
	}

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {

		
		System.out.println(Compile.start());
		System.out.println("START PLAYING...");

		ArrayList<String> gamesToPlay = new ArrayList<String>();
		gamesToPlay.addAll(Arrays.asList(GAMES));

		System.out.println(Configuration.dateFormat.format(new Date()));

		ArrayList<ArrayList<Future<GameResult>>> allResult = new ArrayList<ArrayList<Future<GameResult>>>();

		for (String strGame : gamesToPlay) {
			ArrayList<Future<GameResult>> gameResult = playOneGame(strGame);
			allResult.add(gameResult);
		}

		double allWins = 0;

		for (int i = 0; i < allResult.size(); i++) {
			ArrayList<Future<GameResult>> gameResult = allResult.get(i);
			int gameScore = 0;
			int gameWins = 0;
			for (Future<GameResult> levelResult : gameResult) {
				GameResult g;
				g = levelResult.get();
				gameScore += g.getScore();
				gameWins += g.getWin();
			}
			allWins += gameWins;
			double gameValue = (gameWins / NUM_LEVELS) * 100
					+ (gameScore / NUM_LEVELS);
			System.out.printf("[%d]Game:%s , Win:%d, Score:%d --> %f \n", i,
					gamesToPlay.get(i), gameWins, (gameScore / NUM_LEVELS),
					gameValue);
		}
		System.out.println("-----------------------------------");
		System.out.printf("Overall wins:%s \n", allWins);

		System.out.println(Configuration.dateFormat.format(new Date()));
		Configuration.SCHEDULER.shutdown();
	}

}
