import java.util.Arrays;


/**
 * Executes one agent in one game
 * 
 * @author spakken
 *
 */
public class OneAgentOneGame {

	/**
	 * executes one agent in one game and displays the result in the console
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		//String controller = "emergence.Agent";
		// String controller = "emergence.agents.StayAliveAgent";
		// String controller = "emergence.agents.EvolutionaryAgent";
		// String controller = "emergence.agents.MCTSHeuristicAgent";
		String controller = "emergence.agents.HeuristicAgent";
		// String controller = "emergence.agents.EvolutionaryAgent";
		// String controller = "emergence.agents.EvolutionaryHeuristicAgent";

		/*
		String gameStr = "butterflies";
		int levelIdx = 2;
		*/
		
		
		/** game to be played */
		String gameStr = args[0];
		/** level to be played */
		int levelIdx = Integer.valueOf(args[1]);
		
		
		// "aliens", "boulderdash", "butterflies", "chase", "frogs",
		// "missilecommand", "portals", "sokoban", "survivezombies", "zelda",
		// "camelRace", "digdug", "firestorms", "infection", "firecaster",
		// "overload", "pacman", "seaquest", "whackamole", "eggomania"

		// Available games:
		String gamesPath = "examples/gridphysics/";

		// Game and level to play
		String game = gamesPath + gameStr + ".txt";
		String level1 = gamesPath + gameStr + "_lvl" + levelIdx + ".txt";

		Exec.execute(controller, game, level1, "", true, false);
		System.exit(0);

	}
}
