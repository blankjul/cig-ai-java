package emergence_HR.strategy;

import java.util.ArrayList;

import emergence_HR.heuristics.AHeuristic;
import emergence_HR.heuristics.SimpleStateHeuristic;
import emergence_HR.heuristics.TargetHeuristic;
import emergence_HR.target.ATarget;
import emergence_HR.target.TargetFactory;
import emergence_HR.tree.Tree;

/**
 * This class is for the administration issue for the heuristics. It's a
 * singleton class!
 * 
 */
public class EnsembleStrategy {

	// all the different strategies
	public ArrayList<AStrategy> pool = new ArrayList<AStrategy>();

	// index that should be expanded on this calculation
	private int index = 0;

	// the tree that is used for iteration
	public Tree tree;

	// private constructor for singleton pattern
	public EnsembleStrategy(Tree tree) {
		this.tree = tree;
		init();
	}

	public void init() {
		pool.clear();

		// add all target heuristics
		ArrayList<ATarget> targets = TargetFactory
				.getAllTargets(tree.root.stateObs);
		for (ATarget target : targets) {
			AStrategy strategy = new GreedyStrategy(tree, new TargetHeuristic(
					target));
			pool.add(strategy);
		}

		// add the simple state heuristic
		pool.add(new GreedyStrategy(tree, new SimpleStateHeuristic()));

	}

	public boolean expand() {
		AStrategy strategy = pool.get(index % pool.size());
		strategy.expand();
		++index;
		return true;
	}

	public AHeuristic top() {
		double maxScore = Double.NEGATIVE_INFINITY;
		AHeuristic heur = null;

		for (AStrategy strategy : pool) {
			if (strategy.heuristic.getScore() > maxScore) {
				maxScore = strategy.heuristic.getScore();
				heur = strategy.heuristic;
			}
		}
		return heur;
	}

	@Override
	public String toString() {
		String s = "---------------------------\n";
		s += "heuristic pool - size: " + pool.size() + "\n";
		s += "---------------------------\n";
		for (AStrategy strategy : pool) {
			AHeuristic heuristic = strategy.heuristic;
			s += String.format("heuristic:%s -> %s \n", heuristic,
					heuristic.getScore());
		}
		s += "---------------------------\n";
		return s;
	}

}
