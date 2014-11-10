package emergence_HR.strategy;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import emergence_HR.heuristics.AHeuristic;
import emergence_HR.tree.Node;
import emergence_HR.tree.Tree;

public class GreedyStrategy extends AStrategy {

	// the queue that is the storage for the nodes
	public Queue<Node> queue;

	/**
	 * Constructor for creating a strategy.
	 * 
	 * @param tree
	 *            to search for good nodes.
	 * @param heuristic
	 *            for expanding issues
	 */
	public GreedyStrategy(Tree tree, AHeuristic heuristic) {
		super(tree, heuristic);
		queue = new PriorityQueue<Node>(11, new NodeComparator(heuristic));
		queue.add(tree.root);
	}

	@Override
	public boolean expand() {
		if (queue.isEmpty())
			return false;
		// just look for the head of the queue
		Node n = queue.poll();
		heuristic.addScore(n);

		this.checkBest(n, heuristic);

		// add all children to the queue
		LinkedList<Node> children = n.getChildren();
		for (Node child : children) {
			queue.add(child);
		}
		return true;
	}

	@Override
	public String toString() {
		String s = "\n";
		s += heuristic.toString();
		s += "\n";
		final int MAX = 4;
		int i = 0;
		for (Node n : queue) {
			s += n.toString();
			s += String
					.format(" -> %s \n", heuristic.evaluateState(n.stateObs));
			if (i >= MAX)
				break;
			++i;
		}
		s += "size: " + queue.size();
		s += "\n-----------------------------";
		return s;
	}

}
