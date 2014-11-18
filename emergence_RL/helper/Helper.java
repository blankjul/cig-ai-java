package emergence_RL.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Helper {

	/**
	 * Return a random entry from a list
	 */
	public static <T> T getRandomEntry(List<T> list, Random r) {
		if (list.size() == 1) {
			return list.get(0);
		} else {
			int index = r.nextInt(list.size());
			return list.get(index);
		}

	}


	/**
	 * Get a String form a list!
	 */
	public static <T> String listToString(ArrayList<T> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (T t : list) {
			sb.append(t.toString() + ",");
		}
		sb.append("]");
		return sb.toString();
	}

}
