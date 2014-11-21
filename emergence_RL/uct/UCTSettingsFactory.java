package emergence_RL.uct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import emergence_RL.helper.Helper;
import emergence_RL.uct.actor.HighestReward;
import emergence_RL.uct.actor.HighestUCT;
import emergence_RL.uct.actor.IActor;
import emergence_RL.uct.actor.MostVisited;
import emergence_RL.uct.actor.MostVisitedAdvanced;
import emergence_RL.uct.backpropagation.ABackPropagation;
import emergence_RL.uct.backpropagation.Backpropagation;
import emergence_RL.uct.defaultPoliciy.ADefaultPolicy;
import emergence_RL.uct.defaultPoliciy.EdgeWeightedRandomWalkPolicy;
import emergence_RL.uct.defaultPoliciy.RandomDeltaPolicy;
import emergence_RL.uct.defaultPoliciy.RandomPolicy;
import emergence_RL.uct.defaultPoliciy.SelfAvoidingPathPolicy;
import emergence_RL.uct.treePolicy.ATreePolicy;
import emergence_RL.uct.treePolicy.FirstPlayUrgencyPoliciy;
import emergence_RL.uct.treePolicy.UCB1TunedPolicy;
import emergence_RL.uct.treePolicy.UCTPolicy;

public class UCTSettingsFactory {
	
	
	
	public static List<ABackPropagation> allBackPropagation = new ArrayList<ABackPropagation>(
			Arrays.asList(new Backpropagation()));
	
	public static List<ATreePolicy> allTreePolicies = new ArrayList<ATreePolicy>(
			Arrays.asList(new UCTPolicy(), new FirstPlayUrgencyPoliciy()));

	public static List<ADefaultPolicy> allDefaultPolicies = new ArrayList<ADefaultPolicy>(
			Arrays.asList(new RandomPolicy(), new RandomDeltaPolicy(), new SelfAvoidingPathPolicy(), new EdgeWeightedRandomWalkPolicy()));
	
	public static List<IActor> allActors = Arrays.asList(new HighestReward(),
			new HighestUCT(), new MostVisited(), new MostVisitedAdvanced());
	
	
	public static UCTSettings random(Random r) {
		UCTSettings s = new UCTSettings();
		s.maxDepth = randomMaxDepth(r);
		s.gamma = randomGamma(r);
		s.c = randomC(r);
		s.actor = randomActor(r);
		s.treePolicy = randomTreePolicy(r);
		s.defaultPolicy = randomDefaultPolicy(r);
		s.backPropagation = randomBackPropagation(r);
		s.weights = randomWeights(r);
		return s;
	}


	public static int randomMaxDepth(Random r) {
		return r.nextInt((20 - 5) + 1) + 5;
	}

	public static double randomGamma(Random r) {
		return 1 - (Math.random() * 0.4);
	}

	public static double randomC(Random r) {
		return r.nextDouble() * 5;
	}
	
	public static IActor randomActor(Random r) {
		return Helper.getRandomEntry(allActors, r);
	}

	public static ABackPropagation randomBackPropagation(Random r) {
		return Helper.getRandomEntry(allBackPropagation, r);
	}

	public static ATreePolicy randomTreePolicy(Random r) {
		return Helper.getRandomEntry(allTreePolicies, r);
	}

	public static ADefaultPolicy randomDefaultPolicy(Random r) {
		return Helper.getRandomEntry(allDefaultPolicies, r);
	}
	
	
	public static double[] randomWeights(Random r) {
		double[] weights = new double[4];
		for (int i = 0; i < weights.length; i++) {
			weights[i] = UCTSettingsFactory.randomWeight(r);
		}
		return weights;
	}

	public static double randomWeight(Random r) {
			return r.nextDouble() * 3;
	}



	
}
