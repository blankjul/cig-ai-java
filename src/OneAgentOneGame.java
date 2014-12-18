import emergence.Agent;




public class OneAgentOneGame {

	public static void main(String[] args) {

		//String controller = "emergence.Agent";
		//String controller = "emergence.agents.StayAliveAgent";
		//String controller = "emergence.agents.MCTSHeuristicAgent";
		//String controller = "emergence.agents.EvolutionaryAgent";
		String controller = "emergence.agents.EvolutionaryHeuristicAgent";
		
		
		String gameStr = "butterflies";
		int levelIdx = 4;
		
		// "aliens", "boulderdash", "butterflies", "chase", "frogs",
		// "missilecommand", "portals", "sokoban", "survivezombies", "zelda",
		// "camelRace", "digdug", "firestorms", "infection", "firecaster",
		// "overload", "pacman", "seaquest", "whackamole", "eggomania"

		// Available games:
		String gamesPath = "examples/gridphysics/";

		// Game and level to play
		String game = gamesPath + gameStr + ".txt";
		String level1 = gamesPath + gameStr + "_lvl" + levelIdx + ".txt";

		Exec.execute(controller, game, level1, "", true, Agent.VERBOSE);

	}
}
