
public class OneAgentOneFullGame {

	public static void main(String[] args) {

		String controller = "emergence_NI.Agent";
		String parameter = "";

		String gameStr = "survivezombies";

		// "aliens", "boulderdash", "buterflies", "chase", "frogs",
		// "missilecommand", "portals", "sokoban", "survivezombies", "zelda",
		// "camelRace", "digdug", "firestorms", "infection", "firecaster",
		// "overload", "pacman", "seaquest", "whackamole", "eggomania"

		// Available games:
		String gamesPath = "examples/gridphysics/";

		// Game and level to play
		String game = gamesPath + gameStr + ".txt";

		for (int i = 0; i < 5; i++) {
			String level1 = gamesPath + gameStr + "_lvl" + i + ".txt";
			Exec.execute(controller, game, level1, parameter, true, true);
		}

	}
}
