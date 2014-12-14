package emergence.strategy.astar;

import java.util.Comparator;

public class AStarNodeHeuristicComparator implements Comparator<AStarNode> {

	@Override
	public int compare(AStarNode o1, AStarNode o2) {
		Double score1 = o1.score();
		Double score2 = o2.score();
		int result = score1.compareTo(score2);
		return result;
	}

}
