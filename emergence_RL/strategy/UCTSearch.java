package emergence_RL.strategy;

import java.util.Random;

import ontology.Types;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class UCTSearch extends AStrategy {

	// thats the height of the tree - that is fix
	public int maxDepth;

	public double C;
	
	public boolean verbose = true;

	// generator for random numbers
	protected Random rand = new Random();

	public UCTSearch(Tree tree, int maxDepth, double C) {
		super(tree);
		this.maxDepth = maxDepth;
		this.C = C;
	}

	public Types.ACTIONS act() {
		return mostVisited();
	}

	
	@Override
	public boolean expand() {
		Node n = treePolicy(tree.root);
		double reward = defaultPolicy(n);
		backup(n, reward);
		return true;
	}

	private Node treePolicy(Node n) {
		while (!n.stateObs.isGameOver()) {
			if (!n.isFullyExpanded()) {
				return n.getRandomChild(rand, true);
			} else {
				n = bestChild(n, C);
			}
		}
		return n;
	}
	
	
	protected double defaultPolicy(Node n) {
		while (!n.stateObs.isGameOver() && n.level <= maxDepth) {
			n = n.getRandomChild(rand, false);
		}
		return n.heuristicScore;
	}

	protected Node bestChild(Node n, double c) {
		double bestUTC = Double.NEGATIVE_INFINITY;
		Node bestNode = null;
		
		for (Node child : n.getChildren()) {
			double exploitRate = child.Q / ((double)child.visited);
			double exploreRate = (2 * Math.log((double) n.visited)) / ((double) child.visited);
			child.utcValue = exploitRate + c * Math.sqrt(exploreRate);
			
			if (child.utcValue > bestUTC) {
				bestNode = child;
				bestUTC = child.utcValue;
			}
		}
		return bestNode;
	}

	

	
	protected void backup(Node n, double reward) {
		while (n != null) {
			++n.visited;
			n.Q += reward;
			n = n.father;
		}
	}
	
	protected Types.ACTIONS mostVisited() {
		Types.ACTIONS a = null;
		int mostVisited = -1;
		
		for (Node child : tree.root.getChildren()) {
			if (child.visited > mostVisited) {
				mostVisited = child.visited;
				a = child.lastAction;
			}
		}
		return a;
	}
	
	@Override
	public String toString() {
		bestChild(tree.root, C);
		StringBuffer sb = new StringBuffer();
		sb.append("ROOT\n");
		sb.append(tree.root.toString()+ "\n");
		sb.append("Children\n");
		for (Node child : tree.root.getChildren()) {
			sb.append(child.toString() + "\n");
		}
		return sb.substring(0, sb.length() -1);
	}

}
