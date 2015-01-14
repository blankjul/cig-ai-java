
import core.game.StateObservation;
import emergence.util.Configuration;
import emergence.util.LevelLoader;
import emergence.util.MapInfo;


/**
 * This class provides the printing of all hashes from all known games.
 *
 */
public class GameInfo {
	
	public static void main(String[] args) {

		for (String game : Configuration.allGames) {
			for (int level = 0; level < 5; level++) {
				StateObservation stateObs = LevelLoader.load(game, level).getObservation();
				String s = MapInfo.info(stateObs);
				System.out.printf("map.put(%s, new Pair<String,Integer>(\"%s\",%s));\n", s.hashCode(), game,level);
			}

		}

	}

}
