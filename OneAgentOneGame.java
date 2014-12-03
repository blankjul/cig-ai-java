


public class OneAgentOneGame {

	public static void main(String[] args) {

		String controller = "emergence_NI.Agent";
		String parameter = "";
		
		String gameStr = "portals";
		int levelIdx = 0;

		// "aliens", "boulderdash", "butterflies", "chase", "frogs",
		// "missilecommand", "portals", "sokoban", "survivezombies", "zelda",
		// "camelRace", "digdug", "firestorms", "infection", "firecaster",
		// "overload", "pacman", "seaquest", "whackamole", "eggomania"
		

		// Available games:
		String gamesPath = "examples/gridphysics/";

		// Game and level to play
		String game = gamesPath + gameStr + ".txt";
		String level1 = gamesPath + gameStr + "_lvl" + levelIdx + ".txt";
		
		Exec.execute(controller, game, level1, parameter, true, true);

	}
}
