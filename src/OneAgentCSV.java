import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import emergence.util.Configuration;
import emergence.util.GameResult;
import emergence.util.Helper;

public class OneAgentCSV {

	//public static String CONTROLLER = "emergence.Agent";
	// public static String CONTROLLER = "emergence.agents.MCTSHeuristicAgent";
	public static String CONTROLLER = "emergence.agents.EvolutionaryHeuristicAgent";
	// public static String CONTROLLER = "emergence.agents.StayAliveAgent";
	//public static String CONTROLLER = "emergence.agents.EvolutionaryAgent";

	// CSV Format for EvolutionaryHeuristicAgent
	// date, game, level, win, score, timesteps, Agent, pessimistic, pathLegth,
	// populationsize, numFittest, updatePathLength, minGeneration

	public static int NUM_LEVELS = 5;

	public static int iterationsWholeGame = 10;

	public static String[] GAMES = Helper.concatenate(Configuration.training,
	Configuration.validation);
	// public static String[] GAMES = Configuration.training;

	//public static String[] GAMES = { "butterflies" };

	public static ArrayList<Future<GameResult>> playOneGame(String game) {
		ArrayList<Future<GameResult>> res = new ArrayList<Future<GameResult>>();
		for (int j = 0; j < NUM_LEVELS; j++) {
			ExecCallable e = new ExecCallable(CONTROLLER, game, j, "");
			Future<GameResult> future = Configuration.SCHEDULER.submit(e);
			res.add(future);
			// System.out.println("Wteeeeeeeeeeeeeeeeeeeeeeeeeeeese");
		}
		return res;
	}

	public static void main(String[] args) throws InterruptedException,
			ExecutionException, FileNotFoundException {

		System.out.println(Compile.start());
		System.out.println("START PLAYING...");

		ArrayList<String> gamesToPlay = new ArrayList<String>();
		gamesToPlay.addAll(Arrays.asList(GAMES));

		System.out.println(Configuration.dateFormat.format(new Date()));

		ArrayList<ArrayList<Future<GameResult>>> allResult = new ArrayList<ArrayList<Future<GameResult>>>();
		ArrayList<GameResult> allResultCSV = new ArrayList<GameResult>();

		for (int i = 0; i < iterationsWholeGame; i++) {
			for (String strGame : gamesToPlay) {
				ArrayList<Future<GameResult>> gameResult = playOneGame(strGame);
				allResult.add(gameResult);
			}
		}
		double allWins = 0;
		double allGames = gamesToPlay.size() * 5 * iterationsWholeGame;

		for (int i = 0; i < allResult.size(); i++) {
			ArrayList<Future<GameResult>> gameResult = allResult.get(i);
			int gameScore = 0;
			int gameWins = 0;

			for (Future<GameResult> levelResult : gameResult) {
				GameResult g;
				g = levelResult.get();
				gameScore += g.getScore();
				gameWins += g.getWin();
				allResultCSV.add(g);
			}
			allWins += gameWins;
			double gameValue = (gameWins / NUM_LEVELS) * 100
					+ (gameScore / NUM_LEVELS);
			System.out.printf("[%d]Game:%s , Win:%d, Score:%d --> %f \n", i,
					gamesToPlay.get(i % gamesToPlay.size()), gameWins, (gameScore / NUM_LEVELS),
					gameValue);
		}
		System.out.println("-----------------------------------");
		System.out.printf("Overall wins:%s Overall Games:%s \n", allWins, allGames);

		System.out.println(Configuration.dateFormat.format(new Date()));
		
		String controllerAsName = (CONTROLLER.contains(".")) ? CONTROLLER.split("\\.")[CONTROLLER.split("\\.").length - 1] : CONTROLLER;
		String outputPath = String.format("output/%s_%s.csv", controllerAsName, Configuration.dateformatCSV.format(new Date()));
		PrintStream out = new PrintStream(new FileOutputStream(outputPath));
		System.setOut(out);

		System.out.println("timestamp,id,agent,execTime,game,level,win,score,timesteps,parameters");
		int id = 0;
		for (GameResult csvResult : allResultCSV) {
	        long timestamp=  System.currentTimeMillis() / 1000L;
			String entry = String.format("%s,%s,%s,%s", timestamp, id++, controllerAsName, csvResult.toCSVString());
			System.out.println( entry );
		}
		out.close();

		Configuration.SCHEDULER.shutdown();
	}

}
