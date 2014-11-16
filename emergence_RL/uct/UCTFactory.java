package emergence_RL.uct;

import emergence_RL.uct.actor.IActor;
import emergence_RL.uct.actor.MostVisited;
import emergence_RL.uct.backpropagation.ABackPropagation;
import emergence_RL.uct.backpropagation.SampleBackpropagation;
import emergence_RL.uct.defaultPoliciy.ADefaultPolicy;
import emergence_RL.uct.defaultPoliciy.RandomPolicy;
import emergence_RL.uct.treePolicy.ATreePolicy;
import emergence_RL.uct.treePolicy.UCTPolicy;

public class UCTFactory {

	
	public static UCTSettings createDefault() {
		int maxDepth = 5;
		IActor actor = new MostVisited();
		ATreePolicy treePolicy = new UCTPolicy();
		ADefaultPolicy defaultPolicy = new RandomPolicy();
		ABackPropagation backPropagation = new SampleBackpropagation();
		double c = Math.sqrt(2);
		double gamma = 1;
		return new UCTSettings(actor, treePolicy, defaultPolicy,
				backPropagation, maxDepth, c, gamma);

	}

}
