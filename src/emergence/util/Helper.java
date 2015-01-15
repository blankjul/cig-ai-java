package emergence.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ontology.Types.ACTIONS;
import tools.Vector2d;
import core.game.StateObservation;

/**
 * This class provides some useful functions which are be used by several
 * different classes.
 *
 */
public class Helper {

	/** object to get random values */
	private static Random r = new Random();

	/**
	 * Generate a hash value which is defined by the position of the avatar.
	 * 
	 * @param stateObs
	 * @return
	 */
	public static String hash(StateObservation stateObs) {
		Vector2d pos = stateObs.getAvatarPosition();
		return String.format("[%s,%s]", pos.x, pos.y);
	}

	/**
	 * Generates a hash value wich is defined by the position of the avatar and
	 * the given action.
	 * 
	 * @param stateObs
	 * @param lastAction
	 * @return
	 */
	public static String hash(StateObservation stateObs, ACTIONS lastAction) {
		Vector2d pos = stateObs.getAvatarPosition();
		String used = (lastAction == ACTIONS.ACTION_USE) ? "y" : "n";
		return String.format("[%s,%s,%s]", pos.x, pos.y, used);
	}

	/**
	 * Returns the distance between two vecors.
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static double distance(Vector2d from, Vector2d to) {
		return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
	}

	/**
	 * Returns the opposite action of the given one.
	 * 
	 * @param a
	 * @return
	 */
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

	/**
	 * Generates a String.
	 * 
	 * @param coll
	 * @return
	 */
	public static <T> String toString(Collection<T> coll) {
		StringBuffer sb = new StringBuffer();
		if (coll == null)
			return "";
		sb.append("[");
		for (T entry : coll) {
			sb.append(entry.toString());
			sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Return a random entry from a list.
	 */
	public static <T> T getRandomEntry(List<T> list) {
		if (list.isEmpty())
			return null;
		else if (list.size() == 1) {
			return list.get(0);
		} else {
			int index = r.nextInt(list.size());
			return list.get(index);
		}
	}

	/**
	 * Return a random entry from a set.
	 * 
	 * @param set
	 * @return
	 */
	public static <T> T getRandomEntry(Set<T> set) {
		if (set.isEmpty())
			return null;
		List<T> asList = new ArrayList<>(set);
		Collections.shuffle(asList);
		return asList.get(0);
	}

	/**
	 * Returns a available random action.
	 * 
	 * @param stateObs
	 * @return
	 */
	public static ACTIONS getRandomAction(StateObservation stateObs) {
		ArrayList<ACTIONS> actions = stateObs.getAvailableActions();
		return getRandomEntry(actions);
	}

	/**
	 * print parameters from one executed game, contains all parameters which
	 * can be extracted from the game
	 */
	public static void printParameter(String[] par) {
		String print = "";
		String err = "";
		String start = "[PARAMETER_START,";
		String end = "PARAMETER_ENDE]";

		print += start;

		for (int i = 0; i < par.length; i++) {
			if (par[i].contains(",")) {
				err = "[FATAL_CSV_ERROR]";
			}
			print += par[i] + ",";
		}

		print += end;
		print += err;

		System.out.println(print);
	}

	/**
	 * Concatenates two Arrays.
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	public static <T> T[] concatenate(T[] A, T[] B) {
		int aLen = A.length;
		int bLen = B.length;

		@SuppressWarnings("unchecked")
		T[] C = (T[]) Array.newInstance(A.getClass().getComponentType(), aLen
				+ bLen);
		System.arraycopy(A, 0, C, 0, aLen);
		System.arraycopy(B, 0, C, aLen, bLen);

		return C;
	}
}