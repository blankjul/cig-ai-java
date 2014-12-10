package emergence.util;

import java.util.HashMap;
import java.util.Map;

import core.game.StateObservation;

/**
 * Class to detect the game which is played. Parameters from Agent and
 * PathComparator can be manipulated by this class
 */
public class GameDetection {


	// store all hashes from known games
	private Map<Integer, Pair<String,Integer>> map = new HashMap<Integer, Pair<String,Integer>>();


	/**
	 * put the hash values from all known games in the HashMap
	 */
	public GameDetection() {
		map.put(-1589003964, new Pair<String,Integer>("aliens",0));
		map.put(-1803833117, new Pair<String,Integer>("aliens",1));
		map.put(2095796398, new Pair<String,Integer>("aliens",2));
		map.put(-1256887739, new Pair<String,Integer>("aliens",3));
		map.put(1311113363, new Pair<String,Integer>("aliens",4));
		map.put(-492739753, new Pair<String,Integer>("boulderdash",0));
		map.put(1860468792, new Pair<String,Integer>("boulderdash",1));
		map.put(974625713, new Pair<String,Integer>("boulderdash",2));
		map.put(-1900313918, new Pair<String,Integer>("boulderdash",3));
		map.put(-1093547202, new Pair<String,Integer>("boulderdash",4));
		map.put(-945871125, new Pair<String,Integer>("butterflies",0));
		map.put(1053042461, new Pair<String,Integer>("butterflies",1));
		map.put(-1496199109, new Pair<String,Integer>("butterflies",2));
		map.put(928962891, new Pair<String,Integer>("butterflies",3));
		map.put(-1968124097, new Pair<String,Integer>("butterflies",4));
		map.put(1810804399, new Pair<String,Integer>("chase",0));
		map.put(1661956669, new Pair<String,Integer>("chase",1));
		map.put(264086346, new Pair<String,Integer>("chase",2));
		map.put(-2091926140, new Pair<String,Integer>("chase",3));
		map.put(1381121068, new Pair<String,Integer>("chase",4));
		map.put(723151987, new Pair<String,Integer>("frogs",0));
		map.put(-1665868012, new Pair<String,Integer>("frogs",1));
		map.put(1752863699, new Pair<String,Integer>("frogs",2));
		map.put(-1395786683, new Pair<String,Integer>("frogs",3));
		map.put(163930264, new Pair<String,Integer>("frogs",4));
		map.put(1283352747, new Pair<String,Integer>("missilecommand",0));
		map.put(-2028543506, new Pair<String,Integer>("missilecommand",1));
		map.put(2093918484, new Pair<String,Integer>("missilecommand",2));
		map.put(1258832973, new Pair<String,Integer>("missilecommand",3));
		map.put(-1079185408, new Pair<String,Integer>("missilecommand",4));
		map.put(865829990, new Pair<String,Integer>("portals",0));
		map.put(-802679259, new Pair<String,Integer>("portals",1));
		map.put(232127760, new Pair<String,Integer>("portals",2));
		map.put(-1212228926, new Pair<String,Integer>("portals",3));
		map.put(-2022010523, new Pair<String,Integer>("portals",4));
		map.put(314633660, new Pair<String,Integer>("sokoban",0));
		map.put(-238767992, new Pair<String,Integer>("sokoban",1));
		map.put(357862833, new Pair<String,Integer>("sokoban",2));
		map.put(-1639536334, new Pair<String,Integer>("sokoban",3));
		map.put(-1132779540, new Pair<String,Integer>("sokoban",4));
		map.put(-324836995, new Pair<String,Integer>("survivezombies",0));
		map.put(-823239386, new Pair<String,Integer>("survivezombies",1));
		map.put(-1057797247, new Pair<String,Integer>("survivezombies",2));
		map.put(1901522168, new Pair<String,Integer>("survivezombies",3));
		map.put(410493911, new Pair<String,Integer>("survivezombies",4));
		map.put(420295946, new Pair<String,Integer>("zelda",0));
		map.put(-1985046589, new Pair<String,Integer>("zelda",1));
		map.put(1829382978, new Pair<String,Integer>("zelda",2));
		map.put(-2092648457, new Pair<String,Integer>("zelda",3));
		map.put(-923310914, new Pair<String,Integer>("zelda",4));
		map.put(-306485887, new Pair<String,Integer>("camelRace",0));
		map.put(-2101771765, new Pair<String,Integer>("camelRace",1));
		map.put(1789551347, new Pair<String,Integer>("camelRace",2));
		map.put(-1795050602, new Pair<String,Integer>("camelRace",3));
		map.put(2082974398, new Pair<String,Integer>("camelRace",4));
		map.put(748227958, new Pair<String,Integer>("digdug",0));
		map.put(-1141384002, new Pair<String,Integer>("digdug",1));
		map.put(-941447364, new Pair<String,Integer>("digdug",2));
		map.put(-2031287468, new Pair<String,Integer>("digdug",3));
		map.put(1410960841, new Pair<String,Integer>("digdug",4));
		map.put(-515732816, new Pair<String,Integer>("firestorms",0));
		map.put(294741794, new Pair<String,Integer>("firestorms",1));
		map.put(-1910609633, new Pair<String,Integer>("firestorms",2));
		map.put(204521955, new Pair<String,Integer>("firestorms",3));
		map.put(1023667940, new Pair<String,Integer>("firestorms",4));
		map.put(158150940, new Pair<String,Integer>("infection",0));
		map.put(471760405, new Pair<String,Integer>("infection",1));
		map.put(1626019, new Pair<String,Integer>("infection",2));
		map.put(-1150500687, new Pair<String,Integer>("infection",3));
		map.put(-1816833434, new Pair<String,Integer>("infection",4));
		map.put(-626610289, new Pair<String,Integer>("firecaster",0));
		map.put(-181001514, new Pair<String,Integer>("firecaster",1));
		map.put(-1456914160, new Pair<String,Integer>("firecaster",2));
		map.put(202544107, new Pair<String,Integer>("firecaster",3));
		map.put(1750547273, new Pair<String,Integer>("firecaster",4));
		map.put(-1767151429, new Pair<String,Integer>("overload",0));
		map.put(-2131067132, new Pair<String,Integer>("overload",1));
		map.put(-1743433693, new Pair<String,Integer>("overload",2));
		map.put(234808544, new Pair<String,Integer>("overload",3));
		map.put(409785251, new Pair<String,Integer>("overload",4));
		map.put(-1175640376, new Pair<String,Integer>("pacman",0));
		map.put(-1031266522, new Pair<String,Integer>("pacman",1));
		map.put(1420200426, new Pair<String,Integer>("pacman",2));
		map.put(-1346865639, new Pair<String,Integer>("pacman",3));
		map.put(-1357848652, new Pair<String,Integer>("pacman",4));
		map.put(1360270273, new Pair<String,Integer>("seaquest",0));
		map.put(2078663919, new Pair<String,Integer>("seaquest",1));
		map.put(509414624, new Pair<String,Integer>("seaquest",2));
		map.put(138123904, new Pair<String,Integer>("seaquest",3));
		map.put(772274210, new Pair<String,Integer>("seaquest",4));
		map.put(-1475543155, new Pair<String,Integer>("whackamole",0));
		map.put(-203273193, new Pair<String,Integer>("whackamole",1));
		map.put(404667625, new Pair<String,Integer>("whackamole",2));
		map.put(498447091, new Pair<String,Integer>("whackamole",3));
		map.put(-1358180783, new Pair<String,Integer>("whackamole",4));
		map.put(2102379144, new Pair<String,Integer>("eggomania",0));
		map.put(-864441344, new Pair<String,Integer>("eggomania",1));
		map.put(445934074, new Pair<String,Integer>("eggomania",2));
		map.put(-1631249033, new Pair<String,Integer>("eggomania",3));
		map.put(1975687605, new Pair<String,Integer>("eggomania",4));


	}

	
	
	
	/**
	 * Detects the current game by looking at the hashes.
	 * @param stateObs
	 * @return the detected game or null if no game was found
	 */
	public Pair<String,Integer> detect(StateObservation stateObs) {
		Integer hash = MapInfo.info(stateObs).hashCode();
		return map.get(hash);
	}
	
	
	


}
