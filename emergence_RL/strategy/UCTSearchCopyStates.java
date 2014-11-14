package emergence_RL.strategy;

import java.util.Random;

import emergence_RL.strategy.uct.actor.IActor;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class UCTSearchCopyStates extends UCTSearch{
	
	public UCTSearchCopyStates(Tree tree, Random r, int maxDepth, double C,
			double epsilon, double gamma, IActor actor) {
		super(tree, r, maxDepth, C, epsilon, gamma, actor);
	}
	@Override
	protected double defaultPolicy(Node n) {
		while (!n.stateObs.isGameOver() && n.level <= maxDepth) {
			n = n.getRandomChild(rand, false);
		}
		return n.heuristicScore;
	}



}
