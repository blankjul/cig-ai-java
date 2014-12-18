package emergence.strategy.mcts;

import java.util.Comparator;


public class BestHeuristicNodeComparator implements Comparator<MCTSNode> {

	@Override
	public int compare(MCTSNode o1, MCTSNode o2) {
		return ((Double)o1.heuristicValue).compareTo(o2.heuristicValue);
	}

}
