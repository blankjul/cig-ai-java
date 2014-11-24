


public class OneAgentOneGame {

	public static void main(String[] args) {

		String controller = "emergence_RL.Agent";
		//String controller = "controllers.human.Agent";
		// controller = "controllers.sampleMCTS.Agent";
		//String parameter = "actor:emergence_RL.uct.actor.MostVisitedAdvanced tree:emergence_RL.uct.treePolicy.HeuristicTreePolicy default:emergence_RL.uct.defaultPoliciy.RandomDeltaPolicy back:emergence_RL.uct.backpropagation.Backpropagation depth:10 c:1.4142135623730951 gamma:0.9326211765422769 weight[0]:2.089510418050092 weight[1]:2.0665150182207026 weight[2]:1.8947589131807137 weight[3]:1.9357444209255685";
		//String parameter = UCTFactory.createHeuristic().toString();
		String parameter = "";
		
		String gameStr = "camelRace";
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
