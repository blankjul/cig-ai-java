package emergence_RL.helper;

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
	

}
