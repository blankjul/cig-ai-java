package emergence_HR;

import java.util.ArrayList;

import emergence_HR.heuristics.AHeuristic;
import emergence_HR.heuristics.SimpleStateHeuristic;
import emergence_HR.heuristics.TargetHeuristic;
import emergence_HR.target.ATarget;
import emergence_HR.target.TargetFactory;
import emergence_HR.tree.HeuristicTreeGreedy;

/**
 * This class is for the administration issue for the heuristics. It's a
 * singleton class!
 * 
 */
public class HeuristicEnsemble {

	public ArrayList<AHeuristic> pool = new ArrayList<AHeuristic>();

	// index that should be expanded on this calculation
	private int index = 0;

	// the tree that is used for iteration
	public HeuristicTreeGreedy tree;

	// private constructor for singleton pattern
	public HeuristicEnsemble(HeuristicTreeGreedy tree) {
		this.tree = tree;
		init();
	}

	public void init() {
		pool.clear();

		// add all target heuristics
		ArrayList<ATarget> targets = TargetFactory
				.getAllTargets(tree.root.stateObs);
		for (ATarget target : targets) {
			pool.add(new TargetHeuristic(target));
		}

		// add the simple state heuristic
		pool.add(new SimpleStateHeuristic());

	}

	public boolean calculate(ActionTimer timer) {
		if (pool.size() == 0) {
			init();
			return false;
		}
		AHeuristic heuristic = pool.get(index % pool.size());
		tree.expand(timer, heuristic);

		// System.out.println(tree);
		++index;
		return true;
	}
	

	public AHeuristic getTOP() {
		double maxScore = Double.NEGATIVE_INFINITY;
		AHeuristic heur = null;

		for (AHeuristic heuristic : pool) {
			if (heuristic.getScore() > maxScore) {
				maxScore = heuristic.getScore();
				heur = heuristic;
			}
		}
		return heur;
	}

	@Override
	public String toString() {
		String s = "---------------------------\n";
		s += "heuristic pool - size: " + pool.size() + "\n";
		s += "---------------------------\n";
		for (AHeuristic heuristic : pool) {
			s += String.format("heuristic:%s -> %s \n", heuristic,
					heuristic.getScore());
		}
		s += "---------------------------\n";
		return s;
	}

}
