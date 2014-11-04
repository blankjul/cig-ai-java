package emergence_HR.tree;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import emergence_HR.ActionTimer;
import emergence_HR.heuristics.AHeuristic;

public class HeuristicTreeGreedy extends AHeuristicTree {

	public Queue<Node> queue;

	Set<String> closedList = new HashSet<String>();

	double bestHeuristic = Double.NEGATIVE_INFINITY;

	public HeuristicTreeGreedy(Node root, AHeuristic heuristic) {
		super(root, heuristic);
		queue = new PriorityQueue<Node>(11, new NodeComparator(heuristic));
		queue.add(root);
		closedList.add(root.hash());
	}

	public void expand(ActionTimer timer) {

		// check whether there is time and we've further tree nodes
		while (timer.isTimeLeft() && !queue.isEmpty()) {

			// just look for the head of the queue
			Node n = queue.poll();
			addScore(n);

			// if it is the best state until now save root as the best action
			double score = heuristic.evaluateState(n.stateObs);
			if (score > bestHeuristic) {
				bestHeuristic = score;
				action = n.getRootAction();
			}

			// add all children to the queue
			LinkedList<Node> children = getChildren(n);
			// add children to the queue
			for (Node child : children) {
				if (!closedList.contains(child.hash())) {
					queue.add(child);
					closedList.add(child.hash());
				}
			}

			timer.addIteration();
		}

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
