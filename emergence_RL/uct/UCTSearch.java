package emergence_RL.uct;

import java.util.Arrays;
import java.util.HashMap;

import ontology.Types;
import emergence_RL.heuristic.TargetHeuristic;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class UCTSearch {

	/**
	 * Tree for all the iterations
	 */
	public Tree tree;

	/**
	 * This is the object that contains all the settings for the uct algorithm.
	 * The Results depends highly on this instance!
	 */
	public UCTSettings s;

	
	/**
	 * This static object contains all the number of visits of each field! This
	 * could be useful for policies!
	 */
	public static HashMap<String, Integer> fieldVisits = new HashMap<String, Integer>();

	/**
	 * For the construction there is
	 * 
	 * @param tree
	 * @param r
	 * @param settings
	 */
	public UCTSearch(Tree tree, UCTSettings settings) {
		this.tree = tree;
		this.s = settings;
		
		System.out.println(Arrays.toString(TargetHeuristic.used));
		System.out.println(Arrays.toString(TargetHeuristic.rewards));
		
		// track the statistic of each field!
		String fieldHash = tree.root.hash();
		boolean visited = fieldVisits.containsKey(fieldHash);
		if (visited) {
			int value = fieldVisits.get(fieldHash);
			fieldVisits.put(fieldHash, value + 1);
		} else {
			fieldVisits.put(fieldHash, 1);
		}
		


	}

	public boolean expand() {
		Node n = s.treePolicy.treePolicy(s, tree.root);
		double reward = s.defaultPolicy.expand(s, n);
		s.backPropagation.backpropagate(s, n, reward);
		return true;
	}

	public Types.ACTIONS act() {
		Types.ACTIONS a = s.actor.act(s, tree);
		return a;
	}

	@Override
	public String toString() {
		s.treePolicy.bestChild(s, tree.root, 0);
		StringBuffer sb = new StringBuffer();
		// sb.append("HeuristicProb: " + heuristicProb + "\n");
		sb.append("ROOT\n");
		sb.append(tree.root.toString() + "\n");
		sb.append("Children\n");
		for (Node child : tree.root.getChildren()) {
			sb.append(child.toString() + "\n");
		}
		return sb.substring(0, sb.length() - 1);
	}

}
