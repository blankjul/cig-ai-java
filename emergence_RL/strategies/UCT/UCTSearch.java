package emergence_RL.strategies.UCT;

import java.util.Arrays;
import java.util.Random;

import ontology.Types;
import emergence_RL.heuristic.TargetHeuristic;
import emergence_RL.strategies.AStrategy;
import emergence_RL.strategies.UCT.actor.IActor;
import emergence_RL.strategies.UCT.actor.MostVisitedAdvanced;
import emergence_RL.strategies.UCT.backpropagation.ABackPropagation;
import emergence_RL.strategies.UCT.backpropagation.Backpropagation;
import emergence_RL.strategies.UCT.defaultPolicy.ADefaultPolicy;
import emergence_RL.strategies.UCT.defaultPolicy.RandomDeltaPolicy;
import emergence_RL.strategies.UCT.treePolicy.ATreePolicy;
import emergence_RL.strategies.UCT.treePolicy.HeuristicTreePolicy;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class UCTSearch extends AStrategy {

	// this epsilon value is sometimes needed
	public static double epsilon = 1e-6;

	// generator for random numbers
	public static Random r = new Random();

	// Tree for all the iterations
	public Tree tree;

	// actor that is used after building the tree
	public IActor actor = new MostVisitedAdvanced();

	// tree policy for expand the nodes
	public ATreePolicy treePolicy = new HeuristicTreePolicy();

	// default policy for the roll out
	public ADefaultPolicy defaultPolicy = new RandomDeltaPolicy();

	// sending the feedback back with backpropagation
	public ABackPropagation backPropagation = new Backpropagation();

	// maximal depth of the tree -> 10 per default!
	public int maxDepth = 10;

	// the value for the exploration term
	public double c = Math.sqrt(2);

	// this is the discounting factor. it's one so disabled default
	public double gamma = 1;

	// this value defines the pessimistic iterations. if 0 it's disabled.
	public int pessimisticIterations = 3;

	// initialize the heuristic that could be used
	public TargetHeuristic heuristic = null;
	
	// the current best node that were used for acting!
	public Node bestNode = null;

	// weights that could be used for a formula!
	public double[] weights = new double[] { 1, 1, 1, 1 };
	
	// urgent tree policy. value for expansion
	public double urgentUCTValue = 3;

	public UCTSearch() {
		super();
	}

	public UCTSearch(Tree tree) {
		super(tree);
		this.tree = tree;
	}

	public boolean expand() {
		Node n = treePolicy.expand(this, tree.root);
		double reward = defaultPolicy.expand(this, n);
		backPropagation.backpropagate(this, n, reward);
		return true;
	}


	@Override
	public Types.ACTIONS act() {
		bestNode = actor.act(this, tree);
		if (bestNode == null) return Types.ACTIONS.ACTION_NIL;
		return bestNode.lastAction;
	}


	public static UCTSearch createRandom(String parameter) {
		UCTSearch search = new UCTSearch();
		// set the correct actor
		String[] array = parameter.split(" ");
		for (String s : array) {
			String key = s.split(":")[0];
			String value = s.split(":")[1];
			if (key.equals("weight[0]")) {
				search.weights[0] = Double.valueOf(value);
			} else if (key.equals("weight[1]")) {
				search.weights[1] = Double.valueOf(value);
			} else if (key.equals("weight[2]")) {
				search.weights[2] = Double.valueOf(value);
			} else if (key.equals("weight[3]")) {
				search.weights[3] = Double.valueOf(value);
			} else if (key.equals("weight[4]")) {
				search.weights[4] = Double.valueOf(value);
			}
		}
		return search;
	}

	/**
	 * Public clone method for evolution
	 */
	public UCTSearch copy() {
		UCTSearch s = new UCTSearch();
		s.heuristic =this.heuristic;
		for (int i = 0; i < weights.length; i++) {
			s.weights[i] = this.weights[i];
		}
		return s;
	}

	public String status() {
		treePolicy.bestChild(this, tree.root, 0);
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

	
	
	/**
	 * Print the whole settings to a string!
	 */
	@Override
	public String toString() {
		String s = String.format(
				"weight[0]:%s weight[1]:%s weight[2]:%s weight[3]:%s",
				weights[0], weights[1], weights[2], weights[3]);
		if (heuristic != null)
			s += " heuristics: " + Arrays.toString(heuristic.weights);
		return s;
	}

}
