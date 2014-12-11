package emergence.tests;

import core.game.Game;
import emergence.util.LevelLoader;

public class Base {
	
	
	public static Game getTestGame(String game, int level) {
		String currentDir = System.getProperty("user.dir");
		String pathToGame = String.format("%s/src/emergence/tests/maps/%s.txt", currentDir, game);
		String pathToLevel = String.format("%s/src/emergence/tests/maps/%s_lvl%s.txt", currentDir, game, level);
		return LevelLoader.loadGameByPath(pathToGame, pathToLevel);
	}

}
