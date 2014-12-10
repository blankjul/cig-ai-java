package emergence.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import ontology.Types.ACTIONS;

import org.junit.BeforeClass;
import org.junit.Test;

import core.game.StateObservation;
import emergence.Simulator;
import emergence.util.LevelLoader;



public class SimulatorTest {

	private static StateObservation stateObs = null;

	@BeforeClass
	public static void setUp() {
		stateObs = LevelLoader.load("firestorms", 5);
	}

	/**
	 * Tests if there is a blocking sprite added
	 */
	@Test
	public void blockingSpriteTest() {
		Simulator sim = new Simulator();
		StateObservation myStateObs = stateObs.copy();
		for (ACTIONS a : myStateObs.getAvailableActions()) {
			sim.advance(myStateObs, a);
		}
		// test for the blocking sprite
		assertTrue(sim.getBlockingSprites().contains(0));

	}

	@Test
	public void recommendedActionsTest() {
		// test for the recommended actions
		Simulator sim = new Simulator();
		Set<ACTIONS> correctNextActions = new HashSet<ACTIONS>();
		StateObservation myStateObs = stateObs.copy();
		for (ACTIONS a : myStateObs.getAvailableActions()) {
			sim.advance(myStateObs, a);
		}
		for (ACTIONS a : myStateObs.getAvailableActions()) {
			if (a != ACTIONS.ACTION_UP && a != ACTIONS.ACTION_DOWN) {
				correctNextActions.add(a);
			}
		}
		
		Set<ACTIONS> simNextActions = sim.getRecommendActions(myStateObs);
		assertEquals(simNextActions, correctNextActions);
	}
	
	@Test
	public void recommendedActionsScratchTest() {
		StateObservation myStateObs = stateObs.copy();

		// test for the recommended actions
		Simulator sim = new Simulator();
		Set<ACTIONS> correctNextActions = new HashSet<ACTIONS>();
		for (ACTIONS a : myStateObs.getAvailableActions()) {
				correctNextActions.add(a);
		}
		Set<ACTIONS> simNextActions = sim.getRecommendActions(myStateObs);
		assertEquals(simNextActions, correctNextActions);
	}

}
