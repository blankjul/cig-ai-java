package emergence.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ontology.Types.ACTIONS;
import tools.Vector2d;
import core.game.StateObservation;

public class Helper {
	
	private static Random r = new Random();
	
	public static String hash(StateObservation stateObs) {
		Vector2d pos = stateObs.getAvatarPosition();
		return String.format("[%s,%s]", pos.x, pos.y);
	}
	
	public static String hash(StateObservation stateObs, ACTIONS lastAction) {
		Vector2d pos = stateObs.getAvatarPosition();
		String used = (lastAction == ACTIONS.ACTION_USE) ? "y" : "n";
		return String.format("[%s,%s,%s]", pos.x, pos.y, used);
	}
	
	public static double distance(Vector2d from, Vector2d to) {
		return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
	}
	
	public static ACTIONS getOppositeAction(ACTIONS a) {
		if (a == ACTIONS.ACTION_DOWN) {
			return ACTIONS.ACTION_UP;
		} else if (a == ACTIONS.ACTION_LEFT) {
			return ACTIONS.ACTION_RIGHT;
		} else if (a == ACTIONS.ACTION_UP) {
			return ACTIONS.ACTION_DOWN;
		} else if (a == ACTIONS.ACTION_RIGHT) {
			return ACTIONS.ACTION_LEFT;
		} else {
			return ACTIONS.ACTION_NIL;
		}
		
	}

	public static <T> String toString(Collection<T> coll) {
		StringBuffer sb = new StringBuffer();
		if (coll == null) return "";
		sb.append("[");
		for (T entry : coll) {
			sb.append(entry.toString());
			sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}
	
	
	
	/**
	 * Return a random entry from a list
	 */
	public static <T> T getRandomEntry(List<T> list) {
		if (list.isEmpty()) return null;
		else if (list.size() == 1) {
			return list.get(0);
		} else {
			int index = r.nextInt(list.size());
			return list.get(index);
		}
	}
	
	public static <T> T getRandomEntry(Set<T> set) {
		if (set.isEmpty()) return null;
		List<T> asList = new ArrayList<>(set);
		Collections.shuffle(asList);
		return asList.get(0);
	}
	
	public static ACTIONS getRandomAction(StateObservation stateObs) {
		ArrayList<ACTIONS> actions = stateObs.getAvailableActions();
		return getRandomEntry(actions);
	}
	
	
	

}
