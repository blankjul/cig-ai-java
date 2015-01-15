package emergence.util;

import java.util.Random;

import tools.JEasyFrame;
import core.VGDLFactory;
import core.VGDLParser;
import core.VGDLRegistry;
import core.VGDLViewer;
import core.game.Game;
import emergence.Agent;
import emergence.util.pair.Pair;

/**
 * This level loader allows to load one level for test cases.
 */
public class LevelLoader {

	/**
	 * Generates a pair object, consisting of the game and the level.
	 * 
	 * @param s
	 * @return
	 */
	private static Pair<String, Integer> parseGameAndLevel(String s) {
		String game = s.substring(0, s.length() - 1);
		int level = Integer.valueOf(s.substring(s.length() - 1, s.length()));
		return new Pair<String, Integer>(game, level);
	}

	/**
	 * Load one game, given the path and the game and level as Strings.
	 * 
	 * @param pathToFolder
	 * @param gameAndLevel
	 * @return
	 */
	public static Game load(String pathToFolder, String gameAndLevel) {
		Pair<String, Integer> pair = parseGameAndLevel(gameAndLevel);

		String pathToGame = String.format("%s%s.txt", pathToFolder, pair._1());
		String pathToLevel = String.format("%s%s_lvl%s.txt", pathToFolder,
				pair._1(), pair._2());

		return loadByPath(pathToGame, pathToLevel);

	}

	/**
	 * Returns a game which is ready to be played.
	 * 
	 * @param pathToGame
	 * @param pathToLevel
	 * @return
	 */
	public static Game loadByPath(String pathToGame, String pathToLevel) {

		VGDLFactory.GetInstance().init();
		VGDLRegistry.GetInstance().init();

		// First, we create the game to be played..
		Game toPlay = new VGDLParser().parseGame(pathToGame);
		toPlay.buildLevel(String.valueOf(pathToLevel));
		toPlay.initForwardModel();
		toPlay.prepareGame(new Agent(), new Random().nextInt());
		return toPlay;
	}

	/**
	 * Loads a game from the default path.
	 * 
	 * @param gameAndLevel
	 * @return
	 */
	public static Game load(String gameAndLevel) {
		String projectPath = System.getProperty("user.dir");
		String pathToFolder = String.format("%s/%s/%s/", projectPath,
				"examples", "gridphysics");
		return load(pathToFolder, gameAndLevel);
	}

	/**
	 * Loads a game given the level as an Integer.
	 * 
	 * @param game
	 * @param level
	 * @return
	 */
	public static Game load(String game, int level) {
		return load(game + String.valueOf(level));
	}

	/**
	 * Returns a JEasyFrame to display the game in an own window.
	 * 
	 * @param game
	 * @return
	 */
	public static JEasyFrame show(Game game) {
		game.prepareGame(new Agent(), new Random().nextInt());
		// Create and initialize the panel for the graphics.
		VGDLViewer view = new VGDLViewer(game, new Agent());
		JEasyFrame frame;
		frame = new JEasyFrame(view, "Java-VGDL");

		// Draw all sprites in the panel.
		view.paint(game.spriteGroups);

		return frame;
	}
}
