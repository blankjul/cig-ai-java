package emergence.util;

import java.util.ArrayList;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;

/**
 * This class is used to get information about a state observation. It can print
 * the information to the console or save it in a String.
 *
 */
public class MapInfo {

	/**
	 * print information about the state observation.
	 * 
	 * @param stateObs
	 * @return
	 */
	public static String info(StateObservation stateObs) {
		Vector2d avatarPosition = stateObs.getAvatarPosition();

		StringBuilder sb = new StringBuilder();

		sb.append("---------------------\n");
		sb.append("ME\n");
		sb.append("---------------------\n");
		sb.append(String
				.format("[%s,%s]\n", avatarPosition.x, avatarPosition.y) + '\n');

		sb.append("---------------------\n");
		sb.append("NPC\n");
		sb.append("---------------------\n");
		sb.append(MapInfo.getStringOfObseravtions(stateObs
				.getNPCPositions(avatarPosition)));

		sb.append("---------------------\n");
		sb.append("Portals\n");
		sb.append("---------------------\n");
		sb.append(MapInfo.getStringOfObseravtions(stateObs
				.getPortalsPositions(avatarPosition)));

		sb.append("---------------------\n");
		sb.append("Immovable\n");
		sb.append("---------------------\n");
		sb.append(MapInfo.getStringOfObseravtions(stateObs
				.getImmovablePositions(avatarPosition)));

		sb.append("---------------------\n");
		sb.append("Resources\n");
		sb.append("---------------------\n");
		sb.append(MapInfo.getStringOfObseravtions(stateObs
				.getResourcesPositions(avatarPosition)));

		sb.append("---------------------\n");
		sb.append("Movable\n");
		sb.append("---------------------\n");
		sb.append(MapInfo.getStringOfObseravtions(stateObs
				.getMovablePositions(avatarPosition)));

		return sb.toString();

	}

	/**
	 * Returns a String with the information about the state observation.
	 * 
	 * @param list
	 * @return
	 */
	public static String getStringOfObseravtions(ArrayList<Observation>[] list) {
		// if we have no immovable objects there can not be rules
		if (list == null || list.length == 0)
			return "";

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < list.length; i++) {
			ArrayList<Observation> lObs = list[i];
			// all observation of this list have the same group
			// so just take the nearest that means the first!
			if (!lObs.isEmpty())
				builder.append("itype: " + lObs.get(0).itype + " -> ");
			for (int j = 0; j < lObs.size(); j++) {
				Observation obs = lObs.get(j);
				builder.append(String.format("[%s,%s], ", obs.position.x,
						obs.position.y));
			}
			builder.append('\n');
		}
		builder.append('\n');
		return builder.toString();
	}
}
