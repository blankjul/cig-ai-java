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
import emergence_RL.uct.backpropagation.ABackPropagation;
import emergence_RL.uct.backpropagation.DicountedBackpropagation;
import emergence_RL.uct.defaultPoliciy.ADefaultPolicy;
import emergence_RL.uct.defaultPoliciy.ExplorePolicy;
import emergence_RL.uct.defaultPoliciy.GreedyPolicy;
import emergence_RL.uct.defaultPoliciy.RandomPolicy;
import emergence_RL.uct.treePolicy.ATreePolicy;
import emergence_RL.uct.treePolicy.UCTPolicy;

public class UCTSettings {

	public static double epsilon = 1e-6;
	
	// this are the different policies that could be used!
	public IActor actor;
	public static List<IActor> allActors = Arrays.asList(new HighestReward(),
			new HighestUCT(), new MostVisited());

	public ADefaultPolicy defaultPolicy;
	public static List<ADefaultPolicy> allDefaultPolicies = new ArrayList<ADefaultPolicy>(
			Arrays.asList(new RandomPolicy(), new ExplorePolicy(), new GreedyPolicy()));

	public ATreePolicy treePolicy;
	public static List<ATreePolicy> allTreePolicies = new ArrayList<ATreePolicy>(
			Arrays.asList(new UCTPolicy()));

	public ABackPropagation backPropagation;
	public static List<ABackPropagation> allBackPropagation = new ArrayList<ABackPropagation>(
			Arrays.asList(new DicountedBackpropagation()));

	// maximal depth of the tree
	public int maxDepth;

	// the value for the exploration term
	public double C;

	// this is a discount factor for the backpropagation
	// if it's zero nothing happens!
	public double gamma;

	// generator for random numbers
	public Random r = new Random();
	



	public UCTSettings(IActor actor, ATreePolicy treePolicy,
			ADefaultPolicy defaultPolicy, ABackPropagation backPropagation,
			int maxDepth, double c, double gamma) {
		super();
		this.actor = actor;
		this.defaultPolicy = defaultPolicy;
		this.treePolicy = treePolicy;
		this.backPropagation = backPropagation;
		this.maxDepth = maxDepth;
		C = c;
		this.gamma = gamma;
	}

	

	@Override
	public String toString() {
		String s = String.format(
				"actor:%s tree:%s default:%s back:%s depth:%s c:%s gamma:%s",
				actor.getClass().getName(), treePolicy.getClass().getName(),
				defaultPolicy.getClass().getName(), backPropagation.getClass()
						.getName(), maxDepth, C, gamma);
		return s;
	}

	/**
	 * All the following methods are needed for random issues.
	 */

	public static UCTSettings random(Random r) {
		int maxDepth = randomMaxDepth(r);
		double gamma = randomGamma(r);
		double c = randomC(r);
		IActor actor = randomActor(r);
		ATreePolicy treePolicy = randomTreePolicy(r);
		ADefaultPolicy defaultPolicy = randomDefaultPolicy(r);
		ABackPropagation backPropagation = randomBackPropagation(r);
		return new UCTSettings(actor, treePolicy, defaultPolicy,
				backPropagation, maxDepth, c, gamma);
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

	/**
	 * This method is needed for string initialization
	 */

	public static UCTSettings create(String parameter) {
		try {

			IActor actor = null;
			ATreePolicy treePolicy = null;
			ADefaultPolicy defaultPolicy = null;
			ABackPropagation backPropagation = null;
			int maxDepth = -1;
			double gamma = 1;
			double c = 0;

			// set the correct actor
			String[] array = parameter.split(" ");
			for (String s : array) {
				String key = s.split(":")[0];
				String value = s.split(":")[1];
				if (key.equals("actor")) {
					actor = (IActor) Class.forName(value).newInstance();
				} else if (key.equals("tree")) {
					treePolicy = (ATreePolicy) Class.forName(value)
							.newInstance();
				} else if (key.equals("default")) {
					defaultPolicy = (ADefaultPolicy) Class.forName(value)
							.newInstance();
				} else if (key.equals("back")) {
					backPropagation = (ABackPropagation) Class.forName(value)
							.newInstance();
				} else if (key.equals("depth")) {
					maxDepth = Integer.valueOf(value);
				} else if (key.equals("c")) {
					c = Double.valueOf(value);
				} else if (key.equals("gamma")) {
					gamma = Double.valueOf(value);
				}
			}

			return new UCTSettings(actor, treePolicy, defaultPolicy,
					backPropagation, maxDepth, c, gamma);

		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

}
