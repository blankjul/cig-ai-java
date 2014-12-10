package emergence.tests;

import java.awt.Point;

import org.junit.BeforeClass;
import org.junit.Test;

import tools.JEasyFrame;
import tools.Vector2d;
import core.game.Game;
import core.game.StateObservation;
import emergence.strategy.astar.AStar;
import emergence.strategy.astar.AStarNode;
import emergence.util.LevelLoader;

public class AStarTest {

	private static Game g;
	private static StateObservation stateObs = null;

	@BeforeClass
	public static void setUp() {
		g = LevelLoader.loadGame("camelRace", 0);
		//g = LevelLoader.loadGame("firestorms", 5);
		stateObs = g.getObservation();
	}

	@Test
	public void test() {
		AStar astar = new AStar(stateObs);
		JEasyFrame frame = LevelLoader.show(g);

		boolean next = true;

		while (next) {
			next = astar.expand();
			AStarNode n = astar.getBest();
			Vector2d v = n.stateObs.getAvatarPosition();
			frame.markers.add(new Point((int)v.x, (int) v.y));
			frame.repaint();
		}
		
		
		System.out.println("Press Enter to continue");
		try {
			System.in.read();
		} catch (Exception e) {
		}
	}

}
