package emergence.util;

import java.util.Random;

import tools.JEasyFrame;
import core.VGDLFactory;
import core.VGDLParser;
import core.VGDLRegistry;
import core.VGDLViewer;
import core.game.Game;
import core.game.StateObservation;
import emergence.Agent;

/**
 * This level loader allows to load one level for test cases.
 */
public class LevelLoader {

	public static Game loadGame(String game, int level) {
		String currentDir = System.getProperty("user.dir");
		String pathToGame = String.format("%s/%s/%s/%s.txt", currentDir, "examples", "gridphysics", game);
		String pathToLevel = String.format("%s/%s/%s/%s_lvl%s.txt", currentDir, "examples", "gridphysics", game, level);
		Game g = loadGameByPath(pathToGame, pathToLevel);
		return g;
	}
	
	public static Game loadGameByPath(String pathToGame, String pathToLevel) {
		VGDLFactory.GetInstance().init();
		VGDLRegistry.GetInstance().init();
		
		// First, we create the game to be played..
		Game toPlay = new VGDLParser().parseGame(pathToGame);
		toPlay.buildLevel(String.valueOf(pathToLevel));
		toPlay.initForwardModel();
		toPlay.prepareGame(new Agent(), new Random().nextInt());
		return toPlay;
	}
	
	

	public static StateObservation load(String game, int level) {
		return loadGame(game, level).getObservation();
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
