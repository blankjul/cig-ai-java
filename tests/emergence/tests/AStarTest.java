package emergence.tests;

import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Point;
import java.util.PriorityQueue;

import org.junit.BeforeClass;
import org.junit.Test;

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

	@BeforeClass
	public static void setUp() {
		//g = Base.getTestGame("default", 3);
		g = LevelLoader.loadGame("camelRace", 3);
		stateObs = g.getObservation();
		System.out.println(MapInfo.info(stateObs));
	}
	
	
	@Test
	public void maxSizeTest() {
		
	    StateObservation myStateObs = stateObs.copy(); 
		
		int maxSize = 5;
		AStar astar = new AStar(myStateObs, maxSize);
		boolean lowerThan = true;
		
		boolean next = true;

		while (next) {
			next = astar.expand();
			lowerThan = astar.getOpenList().size() < maxSize;
		}
		assertTrue("open list always lower than 5", lowerThan);
		
	}

	
	
	@Test
	public void test() {
		StateObservation myStateObs = stateObs.copy(); 
		AStar astar = new AStar(myStateObs, 10);
		JEasyFrame frame = LevelLoader.show(g);
		frame.blockSize = myStateObs.getBlockSize();

		boolean next = true;

		while (next) {
			
			PriorityQueue<AStarNode> openList = astar.getOpenList();
			frame.markers.clear();
			int counter = 0;
			for (Vector2d v : astar.getClosedSet()) {
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
