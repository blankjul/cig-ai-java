
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

		String controller = "emergence.Agent";
		// String controller = "emergence.agents.StayAliveAgent";
		// String controller = "emergence.agents.EvolutionaryAgent";
		// String controller = "emergence.agents.MCTSHeuristicAgent";
		//String controller = "emergence.agents.HeuristicAgent";
		// String controller = "emergence.agents.EvolutionaryAgent";
		// String controller = "emergence.agents.EvolutionaryHeuristicAgent";

		/** game to be played */
		String gameStr = "butterflies";
		/** level to be played */
		int levelIdx = 2;

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

	}
}
