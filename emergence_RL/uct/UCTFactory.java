package emergence_RL.uct;

import emergence_RL.uct.actor.IActor;
import emergence_RL.uct.actor.MostVisitedAdvanced;
import emergence_RL.uct.backpropagation.ABackPropagation;
import emergence_RL.uct.backpropagation.HeuristicBackpropagation;
import emergence_RL.uct.defaultPoliciy.ADefaultPolicy;
import emergence_RL.uct.defaultPoliciy.RandomDeltaPolicy;
import emergence_RL.uct.sample.SampleBackpropagation;
import emergence_RL.uct.sample.SampleTreePolicy;
import emergence_RL.uct.treePolicy.ATreePolicy;
import emergence_RL.uct.treePolicy.HeuristicTreePolicy;

public class UCTFactory {

	
	public static UCTSettings createDefault() {
		int maxDepth = 10;
		IActor actor = new MostVisitedAdvanced();
		ATreePolicy treePolicy = new SampleTreePolicy();
		ADefaultPolicy defaultPolicy = new RandomDeltaPolicy();
		ABackPropagation backPropagation = new SampleBackpropagation();
		double c = Math.sqrt(2);
		double gamma = 1;
		return new UCTSettings(actor, treePolicy, defaultPolicy,
				backPropagation, maxDepth, c, gamma);

	}

	
	public static UCTSettings createHeuristic() {
		int maxDepth = 10;
		IActor actor = new MostVisitedAdvanced();
		ATreePolicy treePolicy = new HeuristicTreePolicy();
		ADefaultPolicy defaultPolicy = new RandomDeltaPolicy();
		ABackPropagation backPropagation = new HeuristicBackpropagation();
		double c = Math.sqrt(2);
		double gamma = 1;
		return new UCTSettings(actor, treePolicy, defaultPolicy,
				backPropagation, maxDepth, c, gamma);

	}
}
