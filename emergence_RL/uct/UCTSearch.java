package emergence_RL.uct;

import ontology.Types;
import emergence_RL.heuristic.AHeuristic;
import emergence_RL.heuristic.EquationStateHeuristic;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;
import emergence_RL.uct.treePolicy.ATreePolicy;
import emergence_RL.uct.treePolicy.HeuristicPolicy;
import emergence_RL.uct.treePolicy.UCTPolicy;

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

	AHeuristic heuristic1 = new EquationStateHeuristic("camelRace/eggomania",
			new double[] { 83.56525340105779, -94.18003693788877,
					63.8497743799621, 52.91407845744166, -89.16201858673986,
					50.898113590684744, -59.55816967825811, 41.28391268668591,
					-70.88223625353956, -17.469607503662886 });

	AHeuristic heuristic2 = new EquationStateHeuristic(
			"aliens/butterflies/missilecommand", new double[] {
					-40.62327505720693, -58.69258914953923,
					-49.606898527438204, -81.32390879388393, 71.43675019986114,
					-61.35483540585036, -61.11143207906202, -87.16329141011143,
					-62.109108312834024, 69.37755076132808 });

	public ATreePolicy[] treePolicies = new ATreePolicy[] { new UCTPolicy(),
			new HeuristicPolicy(heuristic1), new HeuristicPolicy(heuristic2)};
	
	public double[] treeProbablities = new double[treePolicies.length];

	
	
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

		// set equal probabilities
		double value = 1 / (double) treePolicies.length;
		for (int i = 0; i < treeProbablities.length; i++) {
			treeProbablities[i] = value;
		}
	}

	public boolean expand() {
		Node n = null;
		double reward = 0;

		n = s.treePolicy.treePolicy(s, tree.root);
		reward = s.defaultPolicy.expand(s, n);

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
		//sb.append("HeuristicProb: " + heuristicProb + "\n");
		sb.append("ROOT\n");
		sb.append(tree.root.toString() + "\n");
		sb.append("Children\n");
		for (Node child : tree.root.getChildren()) {
			sb.append(child.toString() + "\n");
		}
		return sb.substring(0, sb.length() - 1);
	}

}
