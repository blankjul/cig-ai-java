package emergence.tests;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.PriorityQueue;

import tools.JEasyFrame;
import tools.Vector2d;
import core.game.Game;
import core.game.StateObservation;
import emergence.strategy.astar.AStar;
import emergence.strategy.astar.AStarNode;
import emergence.util.LevelLoader;
import emergence.util.MapInfo;
import emergence.util.Pair;

public class AStarTest {

	private static Game g;
	private static StateObservation stateObs = null;
	private static ArrayList<AStarNode> closed = new ArrayList<>();

	@BeforeClass
	public static void setUp() {
		//g = Base.getTestGame("default", 3);
		g = LevelLoader.loadGame("portals", 0);
		stateObs = g.getObservation();
		System.out.println(MapInfo.info(stateObs));
	}

	@Test
	public void test() {
		
		
		AStar astar = new AStar(stateObs);
		JEasyFrame frame = LevelLoader.show(g);
		frame.blockSize = stateObs.getBlockSize();

		boolean next = true;

		while (next) {
			
			PriorityQueue<AStarNode> openList = astar.getOpenList();
			closed.add(astar.getBest());
			frame.markers.clear();
			int counter = 0;
			for (AStarNode node : closed) {
				Vector2d v = node.stateObs.getAvatarPosition();
				Point p = new Point((int)v.x, (int) v.y);
				frame.markers.add(new Pair<Point, Color>(p, Color.BLACK));
			}
			
			
			for (AStarNode node : openList) {
				Color c = (counter++ == 0) ? Color.RED : Color.YELLOW;  
				Vector2d v = node.stateObs.getAvatarPosition();
				Point p = new Point((int)v.x, (int) v.y);
				frame.markers.add(new Pair<Point, Color>(p, c));
			}
			
			
			frame.repaint();
			astar.printOpenList();
			next = astar.expand();
		}
		
		
		System.out.println("Press Enter to continue");
		try {
			System.in.read();
		} catch (Exception e) {
		}
	}

}
