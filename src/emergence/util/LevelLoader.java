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

	private static Pair<String,Integer> parseGameAndLevel(String s) {
		String game = s.substring(0, s.length() - 1);
		int level = Integer.valueOf(s.substring(s.length() - 1, s.length()));
		return new Pair<String,Integer>(game, level);
	}
	
	
	public static Game load(String pathToFolder, String gameAndLevel) {
		Pair<String,Integer> pair = parseGameAndLevel(gameAndLevel);
		
		String pathToGame = String.format("%s%s.txt", pathToFolder, pair._1());
		String pathToLevel = String.format("%s%s_lvl%s.txt", pathToFolder, pair._1(), pair._2());
		
		return loadByPath(pathToGame, pathToLevel);
				
	}
	
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

	
	public static Game load(String gameAndLevel) {
		String projectPath = System.getProperty("user.dir");
		String pathToFolder = String.format("%s/%s/%s/", projectPath, "examples", "gridphysics");
		return load(pathToFolder,gameAndLevel);
	}

	
	public static Game load(String game, int level) {
		return load(game + String.valueOf(level));
	}



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
