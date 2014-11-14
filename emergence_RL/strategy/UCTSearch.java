package emergence_RL.strategy;

import java.util.Random;

import ontology.Types;
import core.game.StateObservation;
import emergence_RL.strategy.uct.actor.IActor;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class UCTSearch extends AStrategy {

	// thats the height of the tree - that is fix
	public int maxDepth;

	// the value for the exploration term
	public double C;

	// epsilon for the utc formula
	public double epsilon;

	// method that should by used by acting
	public IActor actor;

	// this is a discount factor for the backpropagation
	// if it's zero nothing happens!
	public double gamma;

	// generator for random numbers
	protected Random rand = new Random();

	// if true there is a debug output
	public boolean verbose = true;

	public UCTSearch(Tree tree, Random r, int maxDepth, double C,
			double epsilon, double gamma, IActor actor) {
		super(tree);
		this.rand = r;
		this.maxDepth = maxDepth;
		this.C = C;
		this.epsilon = epsilon;
		this.gamma = gamma;
		this.actor = actor;

	}

	@Override
	public boolean expand() {
		Node n = treePolicy(tree.root);
		double reward = defaultPolicy(n);
		backpropagate(n, reward);
		return true;
	}

	private Node treePolicy(Node n) {
		while (!n.stateObs.isGameOver() && n.level <= maxDepth) {
			if (!n.isFullyExpanded()) {
				return n.getRandomChild(rand, true);
			} else {
				n = uct(n);
			}
		}
		return n;
	}

	protected double defaultPolicy(Node n) {
		StateObservation s = n.stateObs.copy();
		int level = n.level;
		while (!n.stateObs.isGameOver() && level <= maxDepth) {
			Types.ACTIONS a = n.getRandomAction(rand);
			s.advance(a);
			++level;
		}
		double delta = Node.getScore(s);
		// TODO: normalize if bad result!
		return delta;

	}

	public Node uct(Node n) {
		double bestUTC = Double.NEGATIVE_INFINITY;
		Node bestNode = null;

		for (Node child : n.getChildren()) {
			child.utcValue = child.Q
					/ (child.visited + epsilon)
					+ C
					* Math.sqrt(Math.log(n.visited + 1)
							/ (child.visited + this.epsilon))
					+ rand.nextDouble() * epsilon;

			if (child.utcValue >= bestUTC) {
				bestNode = child;
				bestUTC = child.utcValue;
			}
		}
		return bestNode;
	}

	/**
	 * update the q values until the root is reached!
	 * 
	 * @param n
	 * @param reward
	 */
	protected void backpropagate(Node n, double reward) {
		int counter = 0;
		while (n != null) {
			++n.visited;

			// use a discount factor for the as a weight!
			n.Q += reward * Math.pow(gamma, counter);
			++counter;
			n = n.father;
		}
	}

	/**
	 * Use the actor interface to find the best action to return!
	 */
	@Override
	public Types.ACTIONS act() {
		Types.ACTIONS a = actor.act(this, tree);
		if (verbose)
			System.out.println("Selected action: " + a);
		return a;
	}

	@Override
	public String toString() {
		uct(tree.root);
		StringBuffer sb = new StringBuffer();
		sb.append("ROOT\n");
		sb.append(tree.root.toString() + "\n");
		sb.append("Children\n");
		for (Node child : tree.root.getChildren()) {
			sb.append(child.toString() + "\n");
		}
		return sb.substring(0, sb.length() - 1);
	}

}
