package emergence_HR.nodes;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import ontology.Types;
import emergence_HR.ActionTimer;
import emergence_HR.LevelInfo;
import emergence_HR.heuristics.StateHeuristic;

/**
 * This is a tree of nodes that looks for different states of father and
 * children!
 * 
 */
public class NodeTree {

	public Node root;

	public Queue<Node> queue;

	public StateHeuristic heuristic;

	private int score = 0;

	public NodeTree(Node root, StateHeuristic heuristic) {
		this.heuristic = heuristic;
		this.root = root;
		this.root.setHeuristic(heuristic);
		this.root.level = 0;
		queue = new PriorityQueue<Node>(11, new NodeComparator());
		queue.add(root);
	}

	public Types.ACTIONS expand(ActionTimer timer) {

		// The action we will finally be executed
		Types.ACTIONS action = Types.ACTIONS.ACTION_NIL;

		double bestHeuristic = Double.MIN_VALUE;

		// check whether there is time and we've further tree nodes
		while (timer.isTimeLeft() && !queue.isEmpty()) {

			//LevelInfo.printNodes(queue);

			// get the first node
			Node node = queue.poll();

			// look if we have a subtree with a really good heuristic
			if (node.getHeuristic() > bestHeuristic) {
				bestHeuristic = node.getHeuristic();
				action = node.getRootAction();
			}

			LinkedList<Node> children = node.getChildren();
			for (Node child : children) {

				// update the performance of the tree
				Types.WINNER w = child.stateObs.getGameWinner();
				if (w == Types.WINNER.PLAYER_WINS) {
					score += 100;
				} else if (w == Types.WINNER.PLAYER_LOSES) {
					score -= 100;
				}
				score += child.stateObs.getGameScore();

			}
			queue.addAll(children);

			timer.addIteration();
		}

		return action;

	}

	/**
	 * Returns the score of the tree!
	 * 
	 * @return
	 */
	public double getScore() {
		return score;
	}

}
