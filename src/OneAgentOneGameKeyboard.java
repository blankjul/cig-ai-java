import emergence.Agent;




public class OneAgentOneGameKeyboard {

	public static void main(String[] args) {

		String controller = "emergence.Agent";
		//String controller = "controllers.human.Agent";
		
		String parameter = "";
		
		String gameStr = "aliens";
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
		
		Exec.execute(controller, game, level1, parameter, true, Agent.VERBOSE);

	}
}
