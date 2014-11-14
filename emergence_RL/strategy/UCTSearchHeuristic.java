package emergence_RL.strategy;

import java.util.Random;

import ontology.Types;
import core.game.StateObservation;
import emergence_RL.strategy.uct.actor.IActor;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class UCTSearchHeuristic extends UCTSearch{
	
	public UCTSearchHeuristic(Tree tree, Random r, int maxDepth, double C,
			double epsilon, double gamma, IActor actor) {
		super(tree, r, maxDepth, C, epsilon, gamma, actor);
	}
	
	
	
	@Override
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



}
