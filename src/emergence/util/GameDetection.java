package emergence.util;

import java.util.HashMap;
import java.util.Map;

import core.game.StateObservation;
import emergence.util.pair.SortedPair;

/**
 * Class to detect the game which is played. Parameters from Agent and
 * PathComparator can be manipulated by this class
 */
public class GameDetection {

	/** store all hashes from known games */
	private Map<Integer, SortedPair<String, Integer>> map = new HashMap<Integer, SortedPair<String, Integer>>();

	/**
	 * put the hash values from all known games in the HashMap
	 */
	public GameDetection() {
		map.put(-1589003964, new SortedPair<String, Integer>("aliens", 0));
		map.put(-1803833117, new SortedPair<String, Integer>("aliens", 1));
		map.put(2095796398, new SortedPair<String, Integer>("aliens", 2));
		map.put(-1256887739, new SortedPair<String, Integer>("aliens", 3));
		map.put(1311113363, new SortedPair<String, Integer>("aliens", 4));
		map.put(-492739753, new SortedPair<String, Integer>("boulderdash", 0));
		map.put(1860468792, new SortedPair<String, Integer>("boulderdash", 1));
		map.put(974625713, new SortedPair<String, Integer>("boulderdash", 2));
		map.put(-1900313918, new SortedPair<String, Integer>("boulderdash", 3));
		map.put(-1093547202, new SortedPair<String, Integer>("boulderdash", 4));
		map.put(-945871125, new SortedPair<String, Integer>("butterflies", 0));
		map.put(1053042461, new SortedPair<String, Integer>("butterflies", 1));
		map.put(-1496199109, new SortedPair<String, Integer>("butterflies", 2));
		map.put(928962891, new SortedPair<String, Integer>("butterflies", 3));
		map.put(-1968124097, new SortedPair<String, Integer>("butterflies", 4));
		map.put(1810804399, new SortedPair<String, Integer>("chase", 0));
		map.put(1661956669, new SortedPair<String, Integer>("chase", 1));
		map.put(264086346, new SortedPair<String, Integer>("chase", 2));
		map.put(-2091926140, new SortedPair<String, Integer>("chase", 3));
		map.put(1381121068, new SortedPair<String, Integer>("chase", 4));
		map.put(723151987, new SortedPair<String, Integer>("frogs", 0));
		map.put(-1665868012, new SortedPair<String, Integer>("frogs", 1));
		map.put(1752863699, new SortedPair<String, Integer>("frogs", 2));
		map.put(-1395786683, new SortedPair<String, Integer>("frogs", 3));
		map.put(163930264, new SortedPair<String, Integer>("frogs", 4));
		map.put(1283352747,
				new SortedPair<String, Integer>("missilecommand", 0));
		map.put(-2028543506, new SortedPair<String, Integer>("missilecommand",
				1));
		map.put(2093918484,
				new SortedPair<String, Integer>("missilecommand", 2));
		map.put(1258832973,
				new SortedPair<String, Integer>("missilecommand", 3));
		map.put(-1079185408, new SortedPair<String, Integer>("missilecommand",
				4));
		map.put(865829990, new SortedPair<String, Integer>("portals", 0));
		map.put(-802679259, new SortedPair<String, Integer>("portals", 1));
		map.put(232127760, new SortedPair<String, Integer>("portals", 2));
		map.put(-1212228926, new SortedPair<String, Integer>("portals", 3));
		map.put(-2022010523, new SortedPair<String, Integer>("portals", 4));
		map.put(314633660, new SortedPair<String, Integer>("sokoban", 0));
		map.put(-238767992, new SortedPair<String, Integer>("sokoban", 1));
		map.put(357862833, new SortedPair<String, Integer>("sokoban", 2));
		map.put(-1639536334, new SortedPair<String, Integer>("sokoban", 3));
		map.put(-1132779540, new SortedPair<String, Integer>("sokoban", 4));
		map.put(-324836995,
				new SortedPair<String, Integer>("survivezombies", 0));
		map.put(-823239386,
				new SortedPair<String, Integer>("survivezombies", 1));
		map.put(-1057797247, new SortedPair<String, Integer>("survivezombies",
				2));
		map.put(1901522168,
				new SortedPair<String, Integer>("survivezombies", 3));
		map.put(410493911, new SortedPair<String, Integer>("survivezombies", 4));
		map.put(420295946, new SortedPair<String, Integer>("zelda", 0));
		map.put(-1985046589, new SortedPair<String, Integer>("zelda", 1));
		map.put(1829382978, new SortedPair<String, Integer>("zelda", 2));
		map.put(-2092648457, new SortedPair<String, Integer>("zelda", 3));
		map.put(-923310914, new SortedPair<String, Integer>("zelda", 4));
		map.put(-306485887, new SortedPair<String, Integer>("camelRace", 0));
		map.put(-2101771765, new SortedPair<String, Integer>("camelRace", 1));
		map.put(1789551347, new SortedPair<String, Integer>("camelRace", 2));
		map.put(-1795050602, new SortedPair<String, Integer>("camelRace", 3));
		map.put(2082974398, new SortedPair<String, Integer>("camelRace", 4));
		map.put(748227958, new SortedPair<String, Integer>("digdug", 0));
		map.put(-1141384002, new SortedPair<String, Integer>("digdug", 1));
		map.put(-941447364, new SortedPair<String, Integer>("digdug", 2));
		map.put(-2031287468, new SortedPair<String, Integer>("digdug", 3));
		map.put(1410960841, new SortedPair<String, Integer>("digdug", 4));
		map.put(-515732816, new SortedPair<String, Integer>("firestorms", 0));
		map.put(294741794, new SortedPair<String, Integer>("firestorms", 1));
		map.put(-1910609633, new SortedPair<String, Integer>("firestorms", 2));
		map.put(204521955, new SortedPair<String, Integer>("firestorms", 3));
		map.put(1023667940, new SortedPair<String, Integer>("firestorms", 4));
		map.put(158150940, new SortedPair<String, Integer>("infection", 0));
		map.put(471760405, new SortedPair<String, Integer>("infection", 1));
		map.put(1626019, new SortedPair<String, Integer>("infection", 2));
		map.put(-1150500687, new SortedPair<String, Integer>("infection", 3));
		map.put(-1816833434, new SortedPair<String, Integer>("infection", 4));
		map.put(-626610289, new SortedPair<String, Integer>("firecaster", 0));
		map.put(-181001514, new SortedPair<String, Integer>("firecaster", 1));
		map.put(-1456914160, new SortedPair<String, Integer>("firecaster", 2));
		map.put(202544107, new SortedPair<String, Integer>("firecaster", 3));
		map.put(1750547273, new SortedPair<String, Integer>("firecaster", 4));
		map.put(-1767151429, new SortedPair<String, Integer>("overload", 0));
		map.put(-2131067132, new SortedPair<String, Integer>("overload", 1));
		map.put(-1743433693, new SortedPair<String, Integer>("overload", 2));
		map.put(234808544, new SortedPair<String, Integer>("overload", 3));
		map.put(409785251, new SortedPair<String, Integer>("overload", 4));
		map.put(-1175640376, new SortedPair<String, Integer>("pacman", 0));
		map.put(-1031266522, new SortedPair<String, Integer>("pacman", 1));
		map.put(1420200426, new SortedPair<String, Integer>("pacman", 2));
		map.put(-1346865639, new SortedPair<String, Integer>("pacman", 3));
		map.put(-1357848652, new SortedPair<String, Integer>("pacman", 4));
		map.put(1360270273, new SortedPair<String, Integer>("seaquest", 0));
		map.put(2078663919, new SortedPair<String, Integer>("seaquest", 1));
		map.put(509414624, new SortedPair<String, Integer>("seaquest", 2));
		map.put(138123904, new SortedPair<String, Integer>("seaquest", 3));
		map.put(772274210, new SortedPair<String, Integer>("seaquest", 4));
		map.put(-1475543155, new SortedPair<String, Integer>("whackamole", 0));
		map.put(-203273193, new SortedPair<String, Integer>("whackamole", 1));
		map.put(404667625, new SortedPair<String, Integer>("whackamole", 2));
		map.put(498447091, new SortedPair<String, Integer>("whackamole", 3));
		map.put(-1358180783, new SortedPair<String, Integer>("whackamole", 4));
		map.put(2102379144, new SortedPair<String, Integer>("eggomania", 0));
		map.put(-864441344, new SortedPair<String, Integer>("eggomania", 1));
		map.put(445934074, new SortedPair<String, Integer>("eggomania", 2));
		map.put(-1631249033, new SortedPair<String, Integer>("eggomania", 3));
		map.put(1975687605, new SortedPair<String, Integer>("eggomania", 4));
	}

	/**
	 * Detects the current game by looking at the hashes.
	 * 
	 * @param stateObs
	 * @return the detected game or null if no game was found
	 */
	public SortedPair<String, Integer> detect(StateObservation stateObs) {
		Integer hash = MapInfo.info(stateObs).hashCode();
		return map.get(hash);
	}
}