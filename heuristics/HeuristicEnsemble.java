package emergence_HR.heuristics;

import java.util.ArrayList;

import core.game.StateObservation;
import emergence_HR.ActionTimer;
import emergence_HR.target.ATarget;
import emergence_HR.target.TargetFactory;
import emergence_HR.tree.ATree;
import emergence_HR.tree.HeuristicTreeLevelOrder;
import emergence_HR.tree.Node;

/**
 * This class is for the administration issue for the heuristics. It's a
 * singleton class!
 * 
 */
public class HeuristicEnsemble {

	public ArrayList<ATree> pool = new ArrayList<ATree>();

	// singleton instance
	private static HeuristicEnsemble instance = null;

	// index that should be expanded on this calcuation
	private int index = 0;

	StateObservation stateObs;

	// private constructor
	private HeuristicEnsemble(StateObservation stateObs) {
		this.stateObs = stateObs;
		reset();
	}

	public void reset() {
		ArrayList<ATarget> targets = TargetFactory.getAllTargets(stateObs);
		for (ATarget target : targets) {
			ATree tree = new HeuristicTreeLevelOrder(new Node(stateObs), new TargetHeuristic(target));
			pool.add(tree);
		}
	}

	public boolean calculate(ActionTimer timer) {
		if (pool.size() == 0) {
			reset();
			return false;
		}
		ATree tree = pool.get(index % pool.size());
		tree.expand(timer);
		return true;
	}

	public AHeuristic getTOP() {
		double maxScore = Double.MIN_VALUE;
		AHeuristic heur = null;

		for (ATree tree : pool) {
			if (tree.getScore() > maxScore) {
				maxScore = tree.getScore();
				heur = tree.heuristic;
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
		for (ATree tree : pool) {
			s += String.format("heuristic:%s -> %s \n", tree.heuristic,
					tree.getScore());
		}
		return s;
	}

}
