package emergence.tests;

import LevelLoader;
import core.game.Game;


public class Base {

	public static String testDir = String.format("%s/examples/tests/", System.getProperty("user.dir"));
	

	public static Game loadTestGame(String strGame, String strLevel) {
		return LevelLoader.loadByPath(Base.testDir + strGame, Base.testDir + strLevel);
	}


}
