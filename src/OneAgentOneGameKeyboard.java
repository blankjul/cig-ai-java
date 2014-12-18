import java.util.Random;

import core.ArcadeMachine;
import core.game.Game;
import core.player.AbstractPlayer;

public class OneAgentOneGameKeyboard {

	public static void main(String[] args) {

		//String controller = "emergence.Agent";
		String controller = "controllers.human.Agent";

		// "aliens", "boulderdash", "butterflies", "chase", "frogs",
		// "missilecommand", "portals", "sokoban", "survivezombies", "zelda",
		// "camelRace", "digdug", "firestorms", "infection", "firecaster",
		// "overload", "pacman", "seaquest", "whackamole", "eggomania"

		String testDir = String.format("%s/examples/tests/", System.getProperty("user.dir"));
		Game toPlay  = LevelLoader.loadByPath(testDir + "scenario1.txt", testDir + "s1_nextToScore.txt");
		
		int seed = new Random().nextInt();
		AbstractPlayer player = ArcadeMachine.createPlayer(controller, null,
				toPlay.getObservation(), seed);
		
		toPlay.playGame(player, seed);
		
		//Exec.execute(controller, game, level1, parameter, true, Agent.VERBOSE);

	}
}
