package emergence.tests;

import static org.junit.Assert.assertTrue;

import java.util.Scanner;
import java.util.Set;

import ontology.Types.ACTIONS;

import org.junit.Test;

import tools.JEasyFrame;
import core.game.StateObservation;
import emergence.Factory;
import emergence.Simulator;
import emergence.util.LevelLoader;

public class SafeTest {

	//System.out.println(MapInfo.info(myStateObs));
	//LevelLoader.show(Base.loadTestGame("scenario1.txt", "s1_nextToWall.txt"));
	
	@Test
	public void blockingSpriteTest() {
		Simulator sim = new Simulator();
		StateObservation myStateObs = Base.loadTestGame("scenario1.txt", "s3_safe.txt").getObservation();
		
		JEasyFrame frame = LevelLoader.show(Base.loadTestGame("scenario1.txt", "s3_safe.txt"));
		frame.show();
		System.out.println("Press Enter to continue");
		try {
			System.in.read();
		} catch (Exception e) {
		}
		
		LevelLoader.show(Base.loadTestGame("scenario1.txt", "s3_safe.txt"));
		sim.advance(myStateObs, ACTIONS.ACTION_UP);
		Set<Integer> result = Factory.getEnvironment().getBlockingSprites();
		assertTrue(result.contains(0));
	}
	
	

}
