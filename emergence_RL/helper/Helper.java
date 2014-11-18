package emergence_RL.helper;

import java.util.ArrayList;
import java.util.Arrays;
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

	public static void normalize(Double[] avgReward) {
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < avgReward.length; i++) {
			if (avgReward[i] >= max)
				max = avgReward[i];
			if (avgReward[i] <= min)
				min = avgReward[i];
		}
		if (min != max) {
			for (int i = 0; i < avgReward.length; i++) {
				double value = (avgReward[i] - min) / (max - min);
				avgReward[i] = value;
			}
		} else {
			for (int i = 0; i < avgReward.length; i++) {
				avgReward[i] = 1d;
			}
		}

	}

	@SuppressWarnings("unchecked")
	public static <T> T[] concatAll(T[] first, T[]... rest) {
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	
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
