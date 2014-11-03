package emergence_HR.heuristics;

import java.util.ArrayList;

import core.game.StateObservation;
import emergence_HR.ActionTimer;
import emergence_HR.nodes.Node;
import emergence_HR.nodes.NodeTree;

/**
 * This class is for the administration issue for the heuristics.
 * It's a singleton class!
 *
 */
public class HeuristicEnsemble {

	// singleton instance
	private static HeuristicEnsemble instance = null;
	
	ArrayList<NodeTree> pool = new ArrayList<NodeTree>();
	
	private int index = 0;
	
	// private constructor
	private HeuristicEnsemble(StateObservation stateObs) {
		ArrayList<Target> targets = TargetFactory.getAllTargets(stateObs);
		for (Target target : targets) {
			NodeTree tree = new NodeTree(new Node(stateObs), new TargetHeuristic(target));
			pool.add(tree);
		}
	}
	
	public void calculate(ActionTimer timer) {
		NodeTree tree = pool.get(index % pool.size());
		tree.expand(timer);
	}

	
	/**
	 * Factory method
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
		for (NodeTree tree : pool) {
			s += String.format("heuristic:%s -> %s \n", tree.heuristic, tree.getScore());
		}
		return s;
	}
	
}
