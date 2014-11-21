package emergence_RL.strategies.uct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import emergence_RL.helper.Helper;
import emergence_RL.strategies.uct.actor.HighestReward;
import emergence_RL.strategies.uct.actor.HighestUCT;
import emergence_RL.strategies.uct.actor.IActor;
import emergence_RL.strategies.uct.actor.MostVisited;
import emergence_RL.strategies.uct.actor.MostVisitedAdvanced;
import emergence_RL.strategies.uct.actor.SampleActor;
import emergence_RL.strategies.uct.backpropagation.ABackPropagation;
import emergence_RL.strategies.uct.backpropagation.Backpropagation;
import emergence_RL.strategies.uct.backpropagation.SampleBackpropagation;
import emergence_RL.strategies.uct.defaultPolicy.ADefaultPolicy;
import emergence_RL.strategies.uct.defaultPolicy.EdgeWeightedRandomWalkPolicy;
import emergence_RL.strategies.uct.defaultPolicy.RandomDeltaPolicy;
import emergence_RL.strategies.uct.defaultPolicy.RandomPolicy;
import emergence_RL.strategies.uct.defaultPolicy.SampleDefaultPolicy;
import emergence_RL.strategies.uct.defaultPolicy.SelfAvoidingPathPolicy;
import emergence_RL.strategies.uct.treePolicy.ATreePolicy;
import emergence_RL.strategies.uct.treePolicy.FirstPlayUrgencyPoliciy;
import emergence_RL.strategies.uct.treePolicy.HeuristicTreePolicy;
import emergence_RL.strategies.uct.treePolicy.SampleTreePolicy;
import emergence_RL.strategies.uct.treePolicy.UCTPolicy;


/**
 * This factory return different settings of the uct search.
 * There are a lot of parameter that can differ. Each new settings
 * has advantages and disadvantages.
 *
 */
public class UCTFactory {

	/**
	 * The default settings that can be different all the time.
	 */
	public static UCTSettings createDefault() {
		UCTSettings settings = new UCTSettings();
		settings.maxDepth = 10;
		settings.actor = new MostVisitedAdvanced();
		settings.treePolicy = new SampleTreePolicy();
		settings.defaultPolicy = new RandomDeltaPolicy();
		settings.backPropagation = new Backpropagation();
		settings.c = Math.sqrt(2);
		settings.gamma = 1;
		return settings;

	}
	
	
	/**
	 * Play like the standard mcts search of the controller sample.
	 */
	public static UCTSettings createSample() {
		UCTSettings settings = new UCTSettings();
		settings.maxDepth = 10;
		settings.actor = new SampleActor();
		settings.treePolicy = new SampleTreePolicy();
		settings.defaultPolicy = new SampleDefaultPolicy();
		settings.backPropagation = new SampleBackpropagation();
		settings.c = Math.sqrt(2);
		settings.gamma = 1;
		return settings;

	}
	

	/**
	 * Use heuristic while playing.
	 */
	public static UCTSettings createHeuristic() {
		UCTSettings settings = new UCTSettings();
		settings.maxDepth = 13;
		settings.actor = new MostVisitedAdvanced();
		settings.treePolicy = new HeuristicTreePolicy();
		settings.defaultPolicy = new RandomDeltaPolicy();
		settings.backPropagation = new Backpropagation();
		settings.c = Math.sqrt(2);
		settings.gamma = 1;
		
		return settings;

	}
	

	
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
			weights[i] = UCTFactory.randomWeight(r);
		}
		return weights;
	}

	public static double randomWeight(Random r) {
			return r.nextDouble() * 3;
	}

}
