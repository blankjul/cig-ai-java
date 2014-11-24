package emergence_RL.strategies.UCT;

import java.util.Random;


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
	
	


}
