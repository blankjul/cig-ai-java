

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import emergence_HR.heuristics.GameResult;

public class OneAgentAllGames {

	public static String CONTROLLER = "controllers.sampleRandom.Agent";
	public static String PARAMETER = "";
	public static int NUM_LEVELS = 5;

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
		gamesToPlay.addAll(Arrays.asList(Configuration.gamesAll));

		System.out.println(Configuration.dateFormat.format(new Date()));
		
		ArrayList<ArrayList<Future<GameResult>>> allResult = new ArrayList<ArrayList<Future<GameResult>>>();
		
		for (String strGame : gamesToPlay) {
			ArrayList<Future<GameResult>> gameResult = playOneGame(strGame);
			allResult.add(gameResult);
		}
		
		for (int i = 0; i < allResult.size(); i++) {
		    ArrayList<Future<GameResult>> gameResult = allResult.get(i);
			double gameScore = 0;
			double gameWins = 0;
			for (Future<GameResult> levelResult : gameResult) {
				GameResult g;
				g = levelResult.get();
				gameScore += g.getScore();
				gameWins += g.getWin();
			}
			gameScore /= NUM_LEVELS;
			gameWins /= NUM_LEVELS;
			double gameValue = gameWins * 100 + gameScore;
			System.out.printf("Game:%s , Win:%f, Score:%f --> %f \n", gamesToPlay.get(i), gameWins, gameScore, gameValue);
		}

		
		System.out.println(Configuration.dateFormat.format(new Date()));
		Configuration.SCHEDULER.shutdown();
	}

}
