package emergence_RL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import emergence_RL.heuristic.AHeuristic;
import emergence_RL.heuristic.TargetHeuristic;
import emergence_RL.strategies.uct.UCTFactory;
import emergence_RL.strategies.uct.UCTSettings;

public class Evolution {

	public static ArrayList<UCTSettings> initPool(Random r, int count) {
		ArrayList<UCTSettings> pool = new ArrayList<UCTSettings>();
		for (int i = 0; i < count; i++) {
			UCTSettings settings = UCTFactory.createHeuristic();
			settings.weights = UCTFactory.randomWeights(r);
			settings.maxDepth = UCTFactory.randomMaxDepth(r);

			if (TestAgent.heuristics.size() > 0) {
				List<TargetHeuristic> asList = new ArrayList<TargetHeuristic>(
						TestAgent.heuristics);
				Collections.shuffle(asList);
				settings.heuristic = (TargetHeuristic) asList.get(0);
			} else {
				settings.heuristic = null;
			}
			pool.add(settings);

		}
		return pool;
	}

	public static UCTSettings mutate(Random r, UCTSettings settings) {
		UCTSettings entry = (UCTSettings) settings.clone();
		if (r.nextDouble() < 0.2)
			entry.weights[0] = UCTFactory.randomWeight(r);
		if (r.nextDouble() < 0.2)
			entry.weights[1] = UCTFactory.randomWeight(r);
		if (r.nextDouble() < 0.2)
			entry.weights[2] = UCTFactory.randomWeight(r);
		if (r.nextDouble() < 0.2)
			entry.weights[3] = UCTFactory.randomWeight(r);
		if (r.nextDouble() < 0.2)
			entry.maxDepth = UCTFactory.randomMaxDepth(r);
		if (r.nextDouble() < 0.2) {
			List<AHeuristic> asList = new ArrayList<AHeuristic>(
					TestAgent.heuristics);
			Collections.shuffle(asList);
			entry.heuristic = (TargetHeuristic) asList.get(0);
		}
		return entry;
	}

	public static UCTSettings crossover(Random r, UCTSettings s1, UCTSettings s2) {
		UCTSettings entry = (UCTSettings) s1.clone();
		entry.weights[0] = (r.nextDouble() < 0.5) ? s2.weights[0]
				: s1.weights[0];
		entry.weights[1] = (r.nextDouble() < 0.5) ? s2.weights[1]
				: s1.weights[1];
		entry.weights[2] = (r.nextDouble() < 0.5) ? s2.weights[2]
				: s1.weights[2];
		entry.weights[3] = (r.nextDouble() < 0.5) ? s2.weights[3]
				: s1.weights[3];
		entry.maxDepth = (r.nextDouble() < 0.5) ? s2.maxDepth : s1.maxDepth;
		entry.heuristic = (r.nextDouble() < 0.5) ? s2.heuristic : s1.heuristic;
		return entry;
	}

}
