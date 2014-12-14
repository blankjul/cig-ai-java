package emergence.tests;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import ontology.Types.ACTIONS;

import org.junit.Test;

import core.game.StateObservation;
import emergence.Factory;
import emergence.Simulator;

public class EnvironmentUpdateTest {

	//System.out.println(MapInfo.info(myStateObs));
	//LevelLoader.show(Base.loadTestGame("scenario1.txt", "s1_nextToWall.txt"));
	
	@Test
	public void blockingSpriteTest() {
		Simulator sim = new Simulator();
		StateObservation myStateObs = Base.loadTestGame("scenario1.txt", "s1_nextToWall.txt").getObservation();
		sim.advance(myStateObs, ACTIONS.ACTION_UP);
		Set<Integer> result = Factory.getEnvironment().getBlockingSprites();
		assertTrue(result.contains(0));
	}
	
	
	@Test
	public void winSpriteTest() {
		Simulator sim = new Simulator();
		StateObservation myStateObs = Base.loadTestGame("scenario1.txt", "s1_nextToPortal.txt").getObservation();
		sim.advance(myStateObs, ACTIONS.ACTION_DOWN);
		Set<Integer> result = Factory.getEnvironment().getWinSprites();
		assertTrue(result.contains(2));
	}
	
	
	@Test
	public void looseSpriteTest() {
		Simulator sim = new Simulator();
		StateObservation myStateObs = Base.loadTestGame("scenario1.txt", "s1_nextToEnemy.txt").getObservation();
		sim.advance(myStateObs, ACTIONS.ACTION_RIGHT);
		Set<Integer> result = Factory.getEnvironment().getLooseSprites();
		assertTrue(result.contains(4));
	}
	
	@Test
	public void scoreSpriteTest() {
		Simulator sim = new Simulator();
		StateObservation myStateObs = Base.loadTestGame("scenario1.txt", "s1_nextToScore.txt").getObservation();
		sim.advance(myStateObs, ACTIONS.ACTION_LEFT);
		Set<Integer> result = Factory.getEnvironment().getScoreSprites();
		assertTrue(result.contains(3));
	}
	
	
	
	

}
