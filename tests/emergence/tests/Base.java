package emergence.tests;

import tools.JEasyFrame;
import core.game.Game;
import emergence.util.LevelLoader;

public class Base {

	public static Game getTestGame(String game, int level) {
		String currentDir = System.getProperty("user.dir");
		String pathToGame = String.format("%s/tests/maps/%s.txt", currentDir, game);
		String pathToLevel = String.format("%s/tests/maps/%s_lvl%s.txt", currentDir, game, level);
		return LevelLoader.loadGameByPath(pathToGame, pathToLevel);
	}

	
	public static void show(Game g) {
		JEasyFrame frame = LevelLoader.show(g);
		frame.setVisible(true);

		System.out.println("Press Enter to continue");
		try {
			System.in.read();
		} catch (Exception e) {
		}
	}

}
