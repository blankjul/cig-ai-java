package emergence.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import ontology.Types.ACTIONS;

import org.junit.BeforeClass;
import org.junit.Test;

import core.game.Game;
import core.game.StateObservation;
import emergence.Simulator;
import emergence.util.MapInfo;

public class SimulatorTest {

	
	@BeforeClass
	public static void setUp() {
		//Base.show(g);
	}

	/**
	 * Tests if there is a blocking sprite added
	 */
	@Test
	public void blockingSpriteTest() {
		Simulator sim = new Simulator();
		StateObservation myStateObs = Base.getTestGame("default", 1).getObservation();
		for (ACTIONS a : myStateObs.getAvailableActions()) {
			StateObservation tmpState = myStateObs.copy();
			sim.advance(tmpState, a);
		}
		// test for the blocking sprite
		assertTrue(sim.getBlockingSprites().contains(0));

	}

	@Test
	public void winSpriteTest() {
		Simulator sim = new Simulator();
		StateObservation myStateObs = Base.getTestGame("portal", 1).getObservation();
		for (ACTIONS a : myStateObs.getAvailableActions()) {
			StateObservation tmpState = myStateObs.copy();
			sim.advance(tmpState, a);
		}
		// test for the blocking sprite
		assertTrue(sim.getWinSprites().contains(14));
	}
	
	@Test
	public void looseSpriteTest() {
		Simulator sim = new Simulator();
		StateObservation myStateObs = Base.getTestGame("frogs", 0).getObservation();
		for (ACTIONS a : myStateObs.getAvailableActions()) {
			StateObservation tmpState = myStateObs.copy();
			sim.advance(tmpState, a);
		}
		// test for the blocking sprite
		assertTrue(sim.getLooseSprites().contains(12));
	}

	@Test
	public void scoreSpriteTest() {
		Simulator sim = new Simulator();
		StateObservation myStateObs = Base.getTestGame("zelda", 0).getObservation();
		for (ACTIONS a : myStateObs.getAvailableActions()) {
			StateObservation tmpState = myStateObs.copy();
			sim.advance(tmpState, a);
		}
		// test for the blocking sprite
		assertTrue(sim.getScoreSprites().contains(3));
	}
	
	@Test
	public void scoreSpriteButterfliesTest() {
		Simulator sim = new Simulator();
		StateObservation myStateObs = Base.getTestGame("butterflies", 0).getObservation();
		for (ACTIONS a : myStateObs.getAvailableActions()) {
			StateObservation tmpState = myStateObs.copy();
			sim.advance(tmpState, a);
		}
		// test for the blocking sprite
		assertTrue(sim.getScoreSprites().contains(4));
	}
	
	@Test
	public void isSafeTest() {
		Simulator sim = new Simulator();
		Game g = Base.getTestGame("zelda", 1);
		StateObservation myStateObs = g.getObservation();
		assertFalse(sim.isSafe(myStateObs, ACTIONS.ACTION_DOWN));
		assertTrue(sim.isSafe(myStateObs, ACTIONS.ACTION_UP));
		assertTrue(sim.isSafe(myStateObs, ACTIONS.ACTION_LEFT));
		assertFalse(sim.isSafe(myStateObs, ACTIONS.ACTION_RIGHT));
		assertFalse(sim.isSafe(myStateObs, ACTIONS.ACTION_USE));
	}
	
	@Test
	public void isSafeTestAliens() {
		Simulator sim = new Simulator();
		Game g = Base.getTestGame("aliens", 0);
		StateObservation myStateObs = g.getObservation();
		sim.advance(myStateObs, ACTIONS.ACTION_UP);
		assertTrue(sim.isSafe(myStateObs, ACTIONS.ACTION_USE));
	}
	
	
	@Test
	public void isSafeWithAdvancedTest() {
		
		Simulator sim = new Simulator();
		Game g = Base.getTestGame("zelda", 1);
		StateObservation myStateObs = g.getObservation();
		int n = 100;
		assertFalse(sim.isSafeWithAdvance(myStateObs, ACTIONS.ACTION_DOWN,n));
		assertTrue(sim.isSafeWithAdvance(myStateObs, ACTIONS.ACTION_UP,n));
		assertTrue(sim.isSafeWithAdvance(myStateObs, ACTIONS.ACTION_LEFT,n));
		//assertFalse(sim.isSafeWithAdvance(myStateObs, ACTIONS.ACTION_RIGHT,n));
		//assertFalse(sim.isSafeWithAdvance(myStateObs, ACTIONS.ACTION_USE,n));
	}
	
	
	
	@Test
	public void isSafeTest2() {
		Simulator sim = new Simulator();
		Game g = Base.getTestGame("zelda", 2);
		StateObservation myStateObs = g.getObservation();
		StateObservation tmp = myStateObs.copy();
		sim.advance(tmp, ACTIONS.ACTION_DOWN);
		sim.advance(tmp, ACTIONS.ACTION_DOWN);
		assertTrue(sim.isSafe(myStateObs, ACTIONS.ACTION_DOWN));
		assertTrue(sim.isSafe(myStateObs, ACTIONS.ACTION_UP));
		assertFalse(sim.isSafe(myStateObs, ACTIONS.ACTION_LEFT));
		assertTrue(sim.isSafe(myStateObs, ACTIONS.ACTION_RIGHT));
		assertTrue(sim.isSafe(myStateObs, ACTIONS.ACTION_USE));
	}
	
	
	@Test
	public void getSafeActionsTest() {
		
		Simulator sim = new Simulator();
		Game g = Base.getTestGame("zelda", 1);
		StateObservation myStateObs = g.getObservation();
		System.out.println(MapInfo.info(myStateObs));
		
		Set<ACTIONS> correct = new HashSet<>();
		for(ACTIONS a : myStateObs.getAvailableActions()) {
			if (!a.equals(ACTIONS.ACTION_DOWN) && !a.equals(ACTIONS.ACTION_RIGHT) && !a.equals(ACTIONS.ACTION_USE)) {
				correct.add(a);
			}
		}
		Set<ACTIONS> result = sim.getSafeActions(myStateObs);
		assertEquals(correct, result);
	}
	
	
	@Test
	public void setAgentTypeTest() {
		
		Simulator sim = new Simulator();
		Game g = Base.getTestGame("zelda", 1);
		StateObservation myStateObs = g.getObservation();
		
		sim.advance(myStateObs, ACTIONS.ACTION_NIL);
		
		Field field;
		try {
			field = Simulator.class.getDeclaredField("myType");
			field.setAccessible(true);
			Integer myType = field.getInt(sim);
			assertTrue(myType != -1);
			
			
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}

		
	}
	
	

	@Test
	public void recommendedActionsTest() {
		// test for the recommended actions
		Simulator sim = new Simulator();
		Set<ACTIONS> correctNextActions = new HashSet<ACTIONS>();
		StateObservation myStateObs = Base.getTestGame("default", 1).getObservation();
		for (ACTIONS a : myStateObs.getAvailableActions()) {
			StateObservation tmpState = myStateObs.copy();
			sim.advance(tmpState, a);
		}
		for (ACTIONS a : myStateObs.getAvailableActions()) {
			if (a != ACTIONS.ACTION_UP && a != ACTIONS.ACTION_DOWN) {
				correctNextActions.add(a);
			}
		}

		Set<ACTIONS> simNextActions = sim.getMoveActions(myStateObs);
		assertEquals(simNextActions, correctNextActions);
	}

	@Test
	public void recommendedActionsScratchTest() {
		StateObservation myStateObs = Base.getTestGame("default", 1).getObservation();
		
		// test for the recommended actions
		Simulator sim = new Simulator();
		Set<ACTIONS> correctNextActions = new HashSet<ACTIONS>();
		for (ACTIONS a : myStateObs.getAvailableActions()) {
			correctNextActions.add(a);
		}
		Set<ACTIONS> simNextActions = sim.getMoveActions(myStateObs);
		assertEquals(simNextActions, correctNextActions);
	}

}
