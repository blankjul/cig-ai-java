package emergence_HR.strategy;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import emergence_HR.heuristics.AHeuristic;
import emergence_HR.tree.Node;
import emergence_HR.tree.Tree;

/**
 * This strategy always expand the node with the highest heuristic value. For
 * optimization there is a closed list to do not iterate over the same hash of
 * node twice.
 *
 */
public class GreedyStrategy extends AStrategy {

	// the queue that is the storage for the nodes
	public Queue<Node> queue;

	// closed list with hashes of nodes
	public HashSet<String> closed;

	
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
		tree.root.score = heuristic.evaluateState(tree.root.stateObs);
		// create the queue
		queue = new PriorityQueue<Node>(11, new NodeComparator());
		queue.add(tree.root);
		// create the closed set
		closed = new HashSet<String>();
		closed.add(tree.root.hash());
	}

	
	@Override
	public boolean expand() {
		if (queue.isEmpty())
			return false;
		// just look for the head of the queue
		Node n = queue.poll();
		
		checkBest(n, heuristic);
		heuristic.addScore(n);

		// add all children to the queue
		LinkedList<Node> children = n.getChildren();
		for (Node child : children) {
			String h = child.hash();
			if (!closed.contains(h)) {
				child.score = heuristic.evaluateState(child.stateObs);
				queue.add(child);
				closed.add(h);
			}
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
