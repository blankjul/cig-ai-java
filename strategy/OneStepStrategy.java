package emergence_HR.strategy;

import java.util.LinkedList;
import java.util.Queue;

import emergence_HR.heuristics.AHeuristic;
import emergence_HR.tree.Node;
import emergence_HR.tree.Tree;

public class OneStepStrategy extends AStrategy {

	// the queue that is the storage for the nodes
	public Queue<Node> queue = null;

	/**
	 * Constructor for creating a strategy.
	 * 
	 * @param tree
	 *            to search for good nodes.
	 * @param heuristic
	 *            for expanding issues
	 */
	public OneStepStrategy(Tree tree, AHeuristic heuristic) {
		super(tree, heuristic);
		queue = new LinkedList<Node>();
		queue.add(tree.root);
	}

	@Override
	public boolean expand() {
		if (queue.isEmpty()) return false;
		// just look for the head of the queue
		Node n = queue.poll();
		heuristic.addScore(n);
		this.checkBest(n, heuristic);
		
		queue.addAll(n.getChildren());

		return true;
	}

}
