package emergence.tests;

import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Scanner;

import org.junit.Test;

import tools.JEasyFrame;
import tools.Vector2d;
import core.game.Game;
import core.game.StateObservation;
import emergence.Factory;
import emergence.heuristics.DistanceHeuristic;
import emergence.strategy.astar.AStar;
import emergence.strategy.astar.AStarNode;
import emergence.targets.ATarget;
import emergence.targets.ATarget.TYPE;
import emergence.targets.ImmovableTarget;
import emergence.util.LevelLoader;
import emergence.util.MapInfo;
import emergence.util.pair.Pair;

public class AStarTest {

	@Test
	public void maxSizeTest() {
		StateObservation myStateObs = Base.loadTestGame("scenario1.txt", "s1_nextToWall.txt").getObservation();
		DistanceHeuristic heuristic = new DistanceHeuristic(new ImmovableTarget(TYPE.Immovable, 2, new Vector2d(728.0,
				252.0)));
		int maxSize = 5;
		AStar astar = new AStar(myStateObs, heuristic, maxSize);
		boolean lowerThan = true;
		boolean next = true;
		while (next) {
			next = astar.expand() != null;
			lowerThan = astar.getOpenList().size() < maxSize;
		}
		assertTrue("open list always lower than 5", lowerThan);

	}

	@Test
	public void portal1Test() {
		Game game = Base.loadTestGame("scenario1.txt", "s1_nextToWall.txt");
		ATarget t = new ImmovableTarget(TYPE.Immovable, 2, new Vector2d(728.0,
				252.0));
		//visualTest(game, t);
	}
	
	
	@Test
	public void portal2Test() {
		Game game = Base.loadTestGame("scenario2.txt", "s2_searchPortal.txt");
		ATarget t = new ImmovableTarget(TYPE.Immovable, 2, new Vector2d(728.0,
				252.0));
		//visualTest(game, t);
	}
	
	@Test
	public void portalLifeTest() {
		Game game = Base.loadTestGame("scenario2.txt", "s2_searchPortal.txt");
		ATarget t = new ImmovableTarget(TYPE.Immovable, 2, new Vector2d(728.0,
				252.0));
		visualTest(game, t);
	}
	
	
	public void visualTest(Game game, ATarget t) {
		
		System.out.println(MapInfo.info(game.getObservation()));
		
		StateObservation myStateObs = game.getObservation();
		DistanceHeuristic heuristic = new DistanceHeuristic(t);
		
		boolean interactive = false;
		AStar astar = new AStar(myStateObs, heuristic);
		JEasyFrame frame = LevelLoader.show(game);
		frame.astar = astar;
		
		boolean next = true;
		while (next) {
			
			frame.repaint();
			astar.printOpenList();
			
			if ( interactive) {
		        new Scanner(System.in).nextLine();
			}
			
			next = astar.expand() != null;
			
		}
		
		frame.repaint();
		System.out.println(Factory.getEnvironment());
		System.out.println("Press Enter to continue");
		try {
			System.in.read();
		} catch (Exception e) {
		}
	}

}
