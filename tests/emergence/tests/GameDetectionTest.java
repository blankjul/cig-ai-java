package emergence.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import core.game.Game;
import core.game.StateObservation;
import emergence.util.Configuration;
import emergence.util.GameDetection;
import emergence.util.LevelLoader;
import emergence.util.pair.SortedPair;

@RunWith(Parameterized.class)
public class GameDetectionTest {

	private String game;
	private Integer level;
	private StateObservation stateObs;

	public GameDetectionTest(String game, int level, StateObservation stateObs) {
		super();
		this.game = game;
		this.level = level;
		this.stateObs = stateObs;
	}

	@Test
	public void testInfoString() {
		GameDetection d = new GameDetection();
		
		SortedPair<String, Integer> pair = d.detect(stateObs);

		assertEquals(String.format("Game shoud be %s = %s", game, pair._1()), pair._1(), game);
		assertEquals(String.format("Level shoud be %s = %s", level, pair._2()), pair._2(), level);

	}

	@Parameters
	public static List<Object[]> data() {
		List<Object[]> data = new ArrayList<Object[]>();
		for (String game : Configuration.allGames) {
			for (int level = 0; level < 5; level++) {
				// First, we create the game to be played..
				Game toPlay = LevelLoader.load(game, level);
				data.add(new Object[] { game, level, toPlay.getObservation() });
			}
		}

		return data;
	}

}
