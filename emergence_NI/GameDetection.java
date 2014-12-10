package emergence_NI;

import java.util.ArrayList;
import java.util.HashMap;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;

/**
 * Class to detect the game which is played. Parameters from Agent and
 * PathComparator can be manipulated by this class
 */
public class GameDetection {

	// a known game was found
	public boolean detected = false;

	// the actual running game
	public String game;

	// the level of the actual running game
	public String level;

	// store all Hashvalues from known games
	public HashMap<Integer, String> mapOfGames = new HashMap<Integer, String>();

	/**
	 * put the hash values from all known games in the HashMap
	 */
	public GameDetection() {

		// training set

		// aliens
		this.mapOfGames.put(-1917948191, "aliens0");
		this.mapOfGames.put(-1817036224, "aliens1");
		this.mapOfGames.put(-1943070217, "aliens2");
		this.mapOfGames.put(1950253168, "aliens3");
		this.mapOfGames.put(418947104, "aliens4");

		// boulderdash
		this.mapOfGames.put(-169192837, "boulderdash0");
		this.mapOfGames.put(-1486613716, "boulderdash1");
		this.mapOfGames.put(1067671267, "boulderdash2");
		this.mapOfGames.put(-805437384, "boulderdash3");
		this.mapOfGames.put(-99948018, "boulderdash4");

		// butterflies
		this.mapOfGames.put(1508901076, "butterflies0");
		this.mapOfGames.put(952027884, "butterflies1");
		this.mapOfGames.put(1973548896, "butterflies2");
		this.mapOfGames.put(1661075748, "butterflies3");
		this.mapOfGames.put(1131092036, "butterflies4");

		// chase
		this.mapOfGames.put(1587237421, "chase0");
		this.mapOfGames.put(779558905, "chase1");
		this.mapOfGames.put(-977349230, "chase2");
		this.mapOfGames.put(-534057846, "chase3");
		this.mapOfGames.put(-1988433586, "chase4");

		// frogs
		this.mapOfGames.put(1354908589, "frogs0");
		this.mapOfGames.put(-1295712639, "frogs1");
		this.mapOfGames.put(-1820256825, "frogs2");
		this.mapOfGames.put(-1962037841, "frogs3");
		this.mapOfGames.put(-1056390376, "frogs4");

		// missilecommand
		this.mapOfGames.put(-1713062535, "missilecommand0");
		this.mapOfGames.put(1609035878, "missilecommand1");
		this.mapOfGames.put(-34413931, "missilecommand2");
		this.mapOfGames.put(1420884511, "missilecommand3");
		this.mapOfGames.put(-652348359, "missilecommand4");

		// portals
		this.mapOfGames.put(-1331136638, "portals0");
		this.mapOfGames.put(34767990, "portals1");
		this.mapOfGames.put(614086281, "portals2");
		this.mapOfGames.put(-2014442, "portals3");
		this.mapOfGames.put(-356955764, "portals4");

		// sokoban
		this.mapOfGames.put(1654623494, "sokoban0");
		this.mapOfGames.put(-1206918452, "sokoban1");
		this.mapOfGames.put(1300173019, "sokoban2");
		this.mapOfGames.put(226486866, "sokoban3");
		this.mapOfGames.put(-1394457046, "sokoban4");

		// survivezombies
		this.mapOfGames.put(1967671877, "survivezombies0");
		this.mapOfGames.put(258484599, "survivezombies1");
		this.mapOfGames.put(1280976598, "survivezombies2");
		this.mapOfGames.put(1056787899, "survivezombies3");
		this.mapOfGames.put(141715637, "survivezombies4");

		// zelda
		this.mapOfGames.put(2016990311, "zelda0");
		this.mapOfGames.put(-932032076, "zelda1");
		this.mapOfGames.put(-1551143695, "zelda2");
		this.mapOfGames.put(2081717478, "zelda3");
		this.mapOfGames.put(888667442, "zelda4");

		// validation set

		// camelRace
		this.mapOfGames.put(-396214556, "camelRace0");
		this.mapOfGames.put(559725574, "camelRace1");
		this.mapOfGames.put(784608658, "camelRace2");
		this.mapOfGames.put(963622747, "camelRace3");
		this.mapOfGames.put(-914698894, "camelRace4");

		// digdug
		this.mapOfGames.put(1977934966, "digdug0");
		this.mapOfGames.put(278438516, "digdug1");
		this.mapOfGames.put(1300713068, "digdug2");
		this.mapOfGames.put(342544874, "digdug3");
		this.mapOfGames.put(-1689160229, "digdug4");

		// firestorms
		this.mapOfGames.put(580484825, "firestorms0");
		this.mapOfGames.put(298009473, "firestorms1");
		this.mapOfGames.put(939290190, "firestorms2");
		this.mapOfGames.put(307354732, "firestorms3");
		this.mapOfGames.put(-256961983, "firestorms4");

		// infection
		this.mapOfGames.put(-1516535389, "infection0");
		this.mapOfGames.put(-1783524272, "infection1");
		this.mapOfGames.put(1222547927, "infection2");
		this.mapOfGames.put(-993906786, "infection3");
		this.mapOfGames.put(-1250452171, "infection4");

		// firecaster
		this.mapOfGames.put(581969080, "firecaster0");
		this.mapOfGames.put(-941055163, "firecaster1");
		this.mapOfGames.put(-1821840779, "firecaster2");
		this.mapOfGames.put(173708644, "firecaster3");
		this.mapOfGames.put(1339817026, "firecaster4");

		// overload
		this.mapOfGames.put(-2133840888, "overload0");
		this.mapOfGames.put(-552361057, "overload1");
		this.mapOfGames.put(716229086, "overload2");
		this.mapOfGames.put(-998278635, "overload3");
		this.mapOfGames.put(-629158782, "overload4");

		// pacman
		this.mapOfGames.put(-294253502, "pacman0");
		this.mapOfGames.put(-514944586, "pacman1");
		this.mapOfGames.put(18139474, "pacman2");
		this.mapOfGames.put(1093113126, "pacman3");
		this.mapOfGames.put(181313304, "pacman4");

		// seaquest
		this.mapOfGames.put(-1281170491, "seaquest0");
		this.mapOfGames.put(-1430840806, "seaquest1");
		this.mapOfGames.put(2076016502, "seaquest2");
		this.mapOfGames.put(803000527, "seaquest3");
		this.mapOfGames.put(591219213, "seaquest4");

		// whackamole
		this.mapOfGames.put(1610998694, "whackamole0");
		this.mapOfGames.put(-1710223012, "whackamole1");
		this.mapOfGames.put(-1600901340, "whackamole2");
		this.mapOfGames.put(279533742, "whackamole3");
		this.mapOfGames.put(1739388364, "whackamole4");

		// eggomania
		this.mapOfGames.put(1628042714, "eggomania0");
		this.mapOfGames.put(2938466, "eggomania1");
		this.mapOfGames.put(-1496400456, "eggomania2");
		this.mapOfGames.put(1851451429, "eggomania3");
		this.mapOfGames.put(1796776854, "eggomania4");
	}

	/**
	 * called when a game starts, checks if the game is known and sets the
	 * depending settings
	 * 
	 * @param stateObs
	 * @param agent
	 * @return
	 */
	public boolean detect(StateObservation stateObs, Agent agent) {

		Vector2d avatarPosition = stateObs.getAvatarPosition();

		// generate the String for the hash value
		String strToHash = "GAMEDETECT ----START----\n";
		strToHash += "Avatar Pos: " + stateObs.getAvatarPosition() + "\n";
		strToHash += "Avatar Orientation: " + stateObs.getAvatarOrientation()
				+ "\n";
		strToHash += "Blocksize: " + stateObs.getBlockSize() + "\n";
		strToHash += "NPC: "
				+ genString(stateObs.getNPCPositions(avatarPosition));
		strToHash += "Portals: "
				+ genString(stateObs.getPortalsPositions(avatarPosition));
		strToHash += "Movable: "
				+ genString(stateObs.getMovablePositions(avatarPosition));
		strToHash += "Immovalble: "
				+ genString(stateObs.getImmovablePositions(avatarPosition));
		strToHash += "Resources: "
				+ genString(stateObs.getResourcesPositions(avatarPosition));
		strToHash += "GAMEDETECT ----END----";

		// DEBUG
		// System.out.println(strToHash);
		// System.out.println("Hash: " + strToHash.hashCode());

		// get the actual game
		String actualgame = mapOfGames.get(strToHash.hashCode());

		if (actualgame != null) { // game found

			// set the values of this gameDetect instance
			this.game = actualgame.substring(0, actualgame.length() - 1);
			this.level = actualgame.substring(actualgame.length() - 1);
			this.detected = true;

			// set the settings of the player and the evolution
			this.setSettings(agent);

			// DEBUG
			System.out.println("detectedGame: " + this.game + this.level);

			return true;

		} else { // game not found

			// just set the standard settings
			this.game = "";
			this.level = "";
			this.detected = false;
			agent.valModifiedByGameDetect = false;
			return false;
		}
	}

	/**
	 * to set the values of the agent, and maybe the static variables of the
	 * PathComparator. when changing settings, add agent.valModifiedByGameDetect
	 * = true; to the case
	 * 
	 * @param agent
	 */
	public void setSettings(Agent agent) {

		if (!this.detected) {
			System.out.println("setSettigns fehler");
			return;
		}

		// Only set the Settings which are set in the method
		// setStandardSettings !!!!!!!!!!!
		switch (this.game) {
		// start trainig set
		case "aliens":
			break;
		case "boulderdash":
			agent.pessimistic = 10;
			agent.pathLength = 5;
			agent.populationSize = 20;
			agent.updatePathLength = false;
			break;
		case "butterflies":
			PathComparator.TYPE = 2;
			break;
		case "chase"://victorys: 4/5
			agent.pathLength = 5;
			PathComparator.TYPE = 2;
			agent.valModifiedByGameDetect = true;
			break;
		case "frogs":
			PathComparator.TYPE = 1;
			agent.pessimistic = 5;
			agent.minGeneration = 4;
			agent.pathLength = 5;
			agent.populationSize = 12;
			agent.numFittest = 4;
			agent.switchHeuristic = 0;
			agent.valModifiedByGameDetect = true;
			break;
		case "missilecommand":
			break;
		case "portals":
			PathComparator.TYPE = 1;
			agent.pathLength = 10;
			//agent.populationSize = 20;
			agent.switchHeuristic = 2000;
			agent.valModifiedByGameDetect = true;
			//System.out.println("modiefied portals");
			break;
		case "sokoban":
			break;
		case "survivezombies": //victories 3/5
			agent.pessimistic = 7;
			PathComparator.TYPE = 2;
			break;
		case "zelda":
			agent.pessimistic = 15;
			agent.pathLength = 12;
			break;
		// start validation set
		case "camelRace":
			PathComparator.TYPE = 1;
			agent.pathLength = 2;
			agent.populationSize = 50;
			agent.switchHeuristic = 2000;
			agent.valModifiedByGameDetect = true;
			//System.out.println("modiefied camelrace");
			break;
		case "digdug":
			PathComparator.TYPE = 2;
			break;
		case "firestorms":
			PathComparator.TYPE = 3;
			agent.pessimistic = 20;
			//agent.switchHeuristic = 200;d
			break;
		case "infection":
			break;
		case "firecaster":
			break;
		case "overload":
			break;
		case "pacman":
			break;
		case "seaquest":
			break;
		case "whackamole":
			break;
		case "eggomania":
			agent.pathLength = 80;
			PathComparator.TYPE = 2;
			agent.valModifiedByGameDetect = true;
			agent.updatePathLength = true;
			break;
		default:
			System.out.println("set settings fehler, kein game gefunden");
			break;
		}
		
		System.out.println(agent.printToString());
	}

	/**
	 * modified version from the Helper-class, to store, and don't print the
	 * String
	 * @param list
	 * @return
	 */
	public String genString(ArrayList<Observation>[] list) {
		String listStr = "";

		// if we have no immovable objects there can not be rules
		if (list == null || list.length == 0)
			return "emptyList\n";

		for (int i = 0; i < list.length; i++) {
			ArrayList<Observation> lObs = list[i];
			// all observation of this list have the same group
			// so just take the nearest that means the first!
			if (!lObs.isEmpty())
				listStr += ("Category: " + lObs.get(0).itype + " -> ");
			for (int j = 0; j < lObs.size(); j++) {
				Observation obs = lObs.get(j);
				listStr += ("[" + obs.position.x + "," + obs.position.y + "]");
			}
			listStr += "\n";
		}
		return listStr;
	}
}
