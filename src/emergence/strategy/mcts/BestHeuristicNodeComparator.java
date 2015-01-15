package emergence.strategy.mcts;

import java.util.Comparator;

/**
 * This comparator uses the heuristic value of an MCTS node to compare two of
 * them.
 * 
 *
 */
public class BestHeuristicNodeComparator implements Comparator<MCTSNode> {

	/**
	 * Compares the heuristic value.
	 */
	@Override
	public int compare(MCTSNode o1, MCTSNode o2) {
		return ((Double) o1.heuristicValue).compareTo(o2.heuristicValue);
	}

}
