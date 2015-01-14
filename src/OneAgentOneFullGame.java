


/**
 * One agent is executed in one full game
 * @author spakken
 *
 */
public class OneAgentOneFullGame {

	/**
	 * Executes one agent in all levels of one game, displays the result in the
	 * console
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		/** cntroller which will be executed */
		String controller = "emergence_NI.Agent";
		String parameter = "";

		/** game which will be played */
		String gameStr = "survivezombies";

		// "aliens", "boulderdash", "buterflies", "chase", "frogs",
		// "missilecommand", "portals", "sokoban", "survivezombies", "zelda",
		// "camelRace", "digdug", "firestorms", "infection", "firecaster",
		// "overload", "pacman", "seaquest", "whackamole", "eggomania"

		/** available games */
		String gamesPath = "examples/gridphysics/";

		// Game and level to play
		String game = gamesPath + gameStr + ".txt";

		for (int i = 0; i < 5; i++) {
			String level1 = gamesPath + gameStr + "_lvl" + i + ".txt";
			Exec.execute(controller, game, level1, parameter, true, true);
		}

	}
}
