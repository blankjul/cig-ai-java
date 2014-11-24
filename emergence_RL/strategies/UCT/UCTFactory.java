package emergence_RL.strategies.UCT;

import java.util.ArrayList;
import java.util.Random;

import emergence_RL.helper.Helper;


/**
 * This factory return different settings of the uct search.
 * There are a lot of parameter that can differ. Each new settings
 * has advantages and disadvantages.
 *
 */
public class UCTFactory {

	


	public static int randomMaxDepth(Random r) {
		return r.nextInt((20 - 5) + 1) + 5;
	}



	public static double[] randomWeights(Random r) {
		double[] weights = new double[4];
		for (int i = 0; i < weights.length; i++) {
			weights[i] = UCTFactory.randomWeight(r);
		}
		return weights;
	}

	public static double randomWeight(Random r) {
			return r.nextDouble() * 3;
	}
	
	
	public static int[] randomTargetHeuristic(Random r) {
		ArrayList<int[]> list = new ArrayList<int[]>();
		list.add(new int[] {0,0,0,0,0,0,0,0,0,0,0,0});
		list.add(new int[] {1,0,0,0,0,0,0,0,0,0,0,0});
		list.add(new int[] {0,1,0,0,0,0,0,0,0,0,0,0});
		list.add(new int[] {0,0,1,0,0,0,0,0,0,0,0,0});
		list.add(new int[] {0,0,0,1,0,0,0,0,0,0,0,0});
		list.add(new int[] {0,0,0,0,1,0,0,0,0,0,0,0});
		list.add(new int[] {0,0,0,0,0,1,0,0,0,0,0,0});
		list.add(new int[] {0,0,0,0,0,0,1,0,0,0,0,0});
		list.add(new int[] {0,0,0,0,0,0,0,1,0,0,0,0});
		list.add(new int[] {0,0,0,0,0,0,0,0,1,0,0,0});
		list.add(new int[] {0,0,0,0,0,0,0,0,0,1,0,0});
		list.add(new int[] {0,0,0,0,0,0,0,0,0,0,1,0});
		list.add(new int[] {0,0,0,0,0,0,0,0,0,0,0,1});
		return Helper.getRandomEntry(list, r);	
	}

}
