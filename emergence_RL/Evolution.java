package emergence_RL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import core.game.StateObservation;
import emergence_RL.helper.Helper;
import emergence_RL.strategies.AEvolutionaryStrategy;
import emergence_RL.strategies.UCTSearch;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class Evolution {

	public static ArrayList<AEvolutionaryStrategy> initPool(int count,
			StateObservation stateObs) {
		ArrayList<AEvolutionaryStrategy> pool = new ArrayList<AEvolutionaryStrategy>();
		for (int i = 0; i < count - 1; i++) {
			AEvolutionaryStrategy search = new UCTSearch().random();
			search.tree = new Tree(new Node(stateObs));
			pool.add(search);
		}
		return pool;
	}

	
	public static ArrayList<AEvolutionaryStrategy> createNextGeneration(
			StateObservation stateObs, ArrayList<AEvolutionaryStrategy> pool,
			int numFittest, int poolSize, double mutateProbability) {

		// survival of the fittest
		Collections.sort(pool);

		ArrayList<AEvolutionaryStrategy> nextPool = new ArrayList<AEvolutionaryStrategy>();

		for (int i = 0; i < numFittest && i < pool.size(); i++) {
			AEvolutionaryStrategy evo = pool.get(i);
			evo.tree = new Tree(new Node(stateObs));
			nextPool.add(evo);
		}

		while (nextPool.size() < poolSize) {

			Random r = UCTSearch.r;
			AEvolutionaryStrategy selected = Helper.getRandomEntry(nextPool, r);

			// result that will be returned
			AEvolutionaryStrategy result = null;

			// mutate
			if (r.nextDouble() < mutateProbability) {
				result = selected.mutate();

				// crossover
			} else {

				// select a second one that is not the first!
				ArrayList<AEvolutionaryStrategy> tmp = new ArrayList<AEvolutionaryStrategy>();
				for (AEvolutionaryStrategy candidate : nextPool) {
					if (candidate != selected)
						tmp.add(candidate);
				}
				AEvolutionaryStrategy second = Helper.getRandomEntry(tmp, r);
				result = selected.crossover(second);
			}
			result.tree = new Tree(new Node(stateObs));
			nextPool.add(result);
		}

		return nextPool;
	}

}
