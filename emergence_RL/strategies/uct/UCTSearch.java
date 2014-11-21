package emergence_RL.strategies.uct;

import java.util.HashMap;

import ontology.Types;
import emergence_RL.strategies.AStrategy;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class UCTSearch extends AStrategy{

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
	public static int maxVisitedField = 0;
	public static Types.ACTIONS lastAction = Types.ACTIONS.ACTION_NIL;

	/**
	 * For the construction there is
	 * 
	 * @param tree
	 * @param r
	 * @param settings
	 */
	public UCTSearch(Tree tree, UCTSettings settings) {
		super(tree);
		
		this.tree = tree;
		this.s = settings;

		
		// track the statistic of each field!
		tree.root.lastAction = lastAction;
		String fieldHash = tree.root.hash();
		boolean visited = fieldVisits.containsKey(fieldHash);
		if (visited) {
			int value = fieldVisits.get(fieldHash) + 1;
			if (value > maxVisitedField) value = maxVisitedField;
			fieldVisits.put(fieldHash, value);
		} else {
			if (1 > maxVisitedField) maxVisitedField = 1;
			fieldVisits.put(fieldHash, 1);
		}
		


	}

	public boolean expand() {
		Node n = s.treePolicy.treePolicy(s, tree.root);
		double reward = s.defaultPolicy.expand(s, n);
		s.backPropagation.backpropagate(s, n, reward);
		return true;
	}

	@Override
	public Types.ACTIONS act() {
		Types.ACTIONS a = s.actor.act(s, tree);
		lastAction = a;
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
