import emergence_RL.uct.UCTFactory;


public class OneAgentOneGame {

	public static void main(String[] args) {

		String controller = "emergence_RL.Agent";
		// controller = "controllers.sampleMCTS.Agent";
		//String parameter = "actor:emergence_RL.uct.actor.MostVisitedAdvanced tree:emergence_RL.heuristic.HeuristicTreePolicy default:emergence_RL.uct.defaultPoliciy.RandomDeltaPolicy back:emergence_RL.uct.backpropagation.Backpropagation depth:7 c:1.4142135623730951 gamma:0.9834985014608854 weight[0]:1.0132829408740718 weight[1]:0.2511097534265957 weight[2]:1.1717280309045348 weight[3]:1.2052372180089392";
		String parameter = UCTFactory.createHeuristic().toString();
		
		String gameStr = "zelda";
		int levelIdx = 3;

		// "aliens", "boulderdash", "butterflies", "chase", "frogs",
		// "missilecommand", "portals", "sokoban", "survivezombies", "zelda",
		// "camelRace", "digdug", "firestorms", "infection", "firecaster",
		// "overload", "pacman", "seaquest", "whackamole", "eggomania"
		

		// Available games:
		String gamesPath = "examples/gridphysics/";

		// Other settings
		boolean visuals = true;

		// Game and level to play
		String game = gamesPath + gameStr + ".txt";
		String level1 = gamesPath + gameStr + "_lvl" + levelIdx + ".txt";
		
		Exec.execute(controller, game, level1, parameter, visuals);

	}
}
