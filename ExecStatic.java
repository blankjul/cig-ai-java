
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import tools.ElapsedCpuTimer;
import core.ArcadeMachine;
import core.VGDLFactory;
import core.VGDLParser;
import core.VGDLRegistry;
import core.competition.CompetitionParameters;
import core.game.Game;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.EquationStateHeuristic;

public class ExecStatic  {


	/**
	 * This class should be used as a command line tool.
	 * bash: java Exec <Agent> <Game> <Level> <Parameter>
	 */
	public static void main(String[] args) {
		String gamesPath = "examples/gridphysics/";
		String controller = args[0];
		String game = gamesPath + args[1] + ".txt";
		String level = gamesPath + args[1] + "_lvl" + args[2] + ".txt";
		String parameter = (args.length > 3) ? args[3] : null;
		execute(controller, game, level, parameter);
	}

	
	
	
	/**
	 * This is a static method to calculate the result of a game. 
	 * 
	 * @param controller
	 * @param game
	 * @param level
	 * @param parameter
	 */
	public static void execute(String controller, String game, String level,
			String parameter) {

		String actionFile = null;
		int randomSeed = new Random().nextInt();
		VGDLFactory.GetInstance().init();
		VGDLRegistry.GetInstance().init();

		System.out.println(" ** Playing game " + game + ", level " + level
				+ " **");

		// First, we create the game to be played..
		Game toPlay = new VGDLParser().parseGame(game);
		toPlay.buildLevel(level);

		// create the player
		ElapsedCpuTimer ect = new ElapsedCpuTimer(
				ElapsedCpuTimer.TimerType.CPU_TIME);
		ect.setMaxTimeMillis(CompetitionParameters.INITIALIZATION_TIME);

		AbstractPlayer player = null;

		
		/*
		 * special parsing of parameter of agent needs it!
		 */
		if (controller.equals("emergence_HR.StaticAgent")) {
			player = new emergence_HR.StaticAgentManuell(toPlay.getObservation(), ect);

			String[] heuristicArray = parameter.split(",");
			double[] weights = new double[heuristicArray.length];
			for (int i = 0; i < weights.length; i++) {
				weights[i] = Double.valueOf(heuristicArray[i]);
			}
			EquationStateHeuristic heuristic = new EquationStateHeuristic(
					weights);
			((emergence_HR.StaticAgentManuell) player).heuristic = heuristic;

			
		} else {
			player = ArcadeMachine.createPlayer(controller, actionFile,
					toPlay.getObservation(), randomSeed);
		}

		
		
		
		player.setup(actionFile, randomSeed);
		// Third, warm the game up.
		ArcadeMachine.warmUp(toPlay, CompetitionParameters.WARMUP_TIME);
		// Then, play the game.
		
		@SuppressWarnings("unused")
		double score = toPlay.runGame(player, randomSeed);
		
		// Finally, when the game is over, we need to tear the player down.
		ArcadeMachine.tearPlayerDown(player);
	}


	
	
	/**
	 * Returns from a process the string buffer that was printed.
	 * @param pr
	 * @return
	 * @throws IOException
	 */
	public static String stringFromProc(Process pr) throws IOException {
		StringBuffer sb = new StringBuffer();
		String line;
		BufferedReader in = new BufferedReader(new InputStreamReader(
				pr.getInputStream()));
		while ((line = in.readLine()) != null) {
			sb.append(line + "\n");
		}
		in.close();
		return sb.toString();
	}
	
	
	


}