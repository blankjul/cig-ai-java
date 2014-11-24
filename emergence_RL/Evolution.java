package emergence_RL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import core.game.StateObservation;
import emergence_RL.helper.Helper;
import emergence_RL.helper.Pair;
import emergence_RL.heuristic.TargetHeuristic;
import emergence_RL.strategies.UCT.UCTFactory;
import emergence_RL.strategies.UCT.UCTSearch;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class Evolution {

	public static ArrayList<UCTSearch> initPool(Random r, int count,
			StateObservation stateObs) {
		ArrayList<UCTSearch> pool = new ArrayList<UCTSearch>();
		for (int i = 0; i < count; i++) {
			UCTSearch search = new UCTSearch();
			search.weights = UCTFactory.randomWeights(r);
			search.maxDepth = UCTFactory.randomMaxDepth(r);
			search.heuristic = TargetHeuristic.createRandom(stateObs);
			pool.add(search);
		}
		return pool;
	}

	public static UCTSearch mutate(Random r, UCTSearch search,
			StateObservation stateObs) {
		UCTSearch entry = search.copy();
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
		if (r.nextDouble() < 0.6) {
			entry.heuristic = TargetHeuristic.createRandom(stateObs);
		}
		return entry;
	}

	public static UCTSearch crossover(Random r, UCTSearch s1, UCTSearch s2) {
		UCTSearch entry = s1.copy();
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

	public static ArrayList<Pair<UCTSearch, Double>> createNextGeneration(
			StateObservation stateObs, ArrayList<Pair<UCTSearch, Double>> pool,
			int numFittest, int poolSize, double mutateProbability) {

		// survival of the fittest
		Collections.sort(pool);

		ArrayList<Pair<UCTSearch, Double>> nextPool = new ArrayList<Pair<UCTSearch, Double>>();

		for (int i = 0; i < numFittest; i++) {
			UCTSearch s = pool.get(i).getFirst();
			s.tree = new Tree(new Node(stateObs));
			nextPool.add(new Pair<UCTSearch, Double>(s, 0d));
		}

		while (nextPool.size() < poolSize) {

			Random r = UCTSearch.r;
			UCTSearch selected = Helper.getRandomEntry(nextPool, r).getFirst();

			UCTSearch entry = null;

			// mutate
			if (r.nextDouble() < mutateProbability) {
				entry = Evolution.mutate(r, selected, stateObs);

				// crossover
			} else {

				// select a second one that is not the first!
				ArrayList<Pair<UCTSearch, Double>> tmp = new ArrayList<Pair<UCTSearch, Double>>();
				for (Pair<UCTSearch, Double> pair : nextPool) {
					if (pair.getFirst() != selected)
						tmp.add(pair);
				}
				UCTSearch second = Helper.getRandomEntry(tmp, r).getFirst();
				entry = Evolution.crossover(r, selected, second);
			}
			entry.tree = new Tree(new Node(stateObs));
			nextPool.add(new Pair<UCTSearch, Double>(entry, 0d));
		}

		return nextPool;
	}

}
