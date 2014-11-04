package emergence_HR;

import java.util.ArrayList;

import core.game.StateObservation;
import emergence_HR.heuristics.AHeuristic;
import emergence_HR.heuristics.TargetHeuristic;
import emergence_HR.target.ATarget;
import emergence_HR.target.TargetFactory;
import emergence_HR.tree.AHeuristicTree;
import emergence_HR.tree.HeuristicTreeGreedy;
import emergence_HR.tree.Node;

/**
 * This class is for the administration issue for the heuristics. It's a
 * singleton class!
 * 
 */
public class HeuristicEnsemble {

	public ArrayList<AHeuristicTree> pool = new ArrayList<AHeuristicTree>();

	// singleton instance
	private static HeuristicEnsemble instance = null;

	// index that should be expanded on this calculation
	private int index = 0;

	StateObservation stateObs;

	// private constructor
	private HeuristicEnsemble(StateObservation stateObs) {
		this.stateObs = stateObs;
		reset();
	}

	public void reset() {
		pool.clear();
		ArrayList<ATarget> targets = TargetFactory.getAllTargets(stateObs);
		for (ATarget target : targets) {
			HeuristicTreeGreedy tree = new HeuristicTreeGreedy(new Node(stateObs),
					new TargetHeuristic(target));
			pool.add(tree);
		}
	}

	public boolean calculate(ActionTimer timer) {
		if (pool.size() == 0) {
			reset();
			return false;
		}
		HeuristicTreeGreedy tree = (HeuristicTreeGreedy) pool.get(index % pool.size());
		tree.expand(timer);
		
		//System.out.println(tree);
		++index;
		return true;
	}

	public AHeuristic getTOP() {
		double maxScore = Double.MIN_VALUE;
		AHeuristic heur = null;

		for (AHeuristicTree tree : pool) {
			if (tree.getScore() > maxScore) {
				maxScore = tree.getScore();
				heur = tree.getHeuristic();
			}
		}
		return heur;
	}

	/**
	 * Factory method
	 * 
	 * @return instance of HeuristicEnsemble
	 */
	public static HeuristicEnsemble getInstance(StateObservation stateObs) {
		if (instance == null) {
			instance = new HeuristicEnsemble(stateObs);
		}
		return instance;
	}

	@Override
	public String toString() {
		String s = "";
		for (AHeuristicTree tree : pool) {
			s += String.format("heuristic:%s -> %s \n", tree.getHeuristic(),
					tree.getScore());
		}
		return s;
	}

}
