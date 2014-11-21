package emergence_RL.strategies.uct.treePolicy;

import java.util.ArrayList;
import java.util.Random;

import ontology.Types;
import core.game.StateObservation;
import emergence_RL.strategies.uct.UCTSettings;
import emergence_RL.tree.Node;

public class SampleTreePolicy extends ATreePolicy {

	// epsilon for the utc formula
	public double epsilon = 1e-6;
	
	
	public Node treePolicy(UCTSettings s, Node n) {
		while (!n.stateObs.isGameOver() && n.level <= s.maxDepth) {
			if (!n.isFullyExpanded()) {
				return expand(n, s.r);
			} else {
				n = bestChild(s, n, s.c);
			}
		}
		return n;
	}

	
	@Override
	public Node bestChild(UCTSettings s, Node n, double c) {
		Node selected = null;
        double bestValue = -Double.MAX_VALUE;
        for (Node child : n.children)
        {
            
        	
        	if (child == null) continue;
            
        	double hvVal = child.Q;
            double childValue =  hvVal / (child.visited + epsilon);

            double uctValue = childValue +
                    c * Math.sqrt(Math.log(n.visited + 1) / (child.visited + epsilon)) +
                    s.r.nextDouble() * epsilon;

            // small sampleRandom numbers: break ties in unexpanded nodes
            if (uctValue > bestValue) {
                selected = child;
                bestValue = uctValue;
            }
        }

        if (selected == null)
        {
            throw new RuntimeException("Warning! returning null: " + bestValue + " : " + n.children.length);
        }

        return selected;
	}

	
	
	private Node expand(Node n, Random r) {
		int bestAction = 0;
		double bestValue = -1;

		for (int i = 0; i < n.children.length; i++) {
			double x = r.nextDouble();
			if (x > bestValue && n.children[i] == null) {
				bestAction = i;
				bestValue = x;
			}
		}

		StateObservation nextState = n.stateObs.copy();
		ArrayList<Types.ACTIONS> actions = n.stateObs.getAvailableActions();
		Types.ACTIONS a = actions.get(bestAction);
		nextState.advance(a);

		Node child = new Node(nextState, n, a);
		n.children[bestAction] = child;
		return child;
	}

}
