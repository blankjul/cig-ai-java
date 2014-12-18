package emergence.strategy.mcts;

import java.util.Comparator;


public class MostVisitedNodeComparator implements Comparator<MCTSNode> {

	@Override
	public int compare(MCTSNode o1, MCTSNode o2) {
		return ((Integer)o2.getVisited()).compareTo(o1.getVisited());
	}

}
