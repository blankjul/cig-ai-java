package emergence_RL.uct;

import java.util.Random;

import emergence_RL.uct.actor.IActor;
import emergence_RL.uct.backpropagation.IBackPropagation;
import emergence_RL.uct.defaultPoliciy.IDefaultPolicy;
import emergence_RL.uct.treePolicy.ATreePolicy;

public class UCTSettings {
	


	// this are the different policies that could be used!
	public IActor actor;
	public IDefaultPolicy defaultPolicy;
	public ATreePolicy treePolicy;
	public IBackPropagation backPropagation;
	

	// maximal depth of the tree
	public int maxDepth;

	// the value for the exploration term
	public double C;

	// this is a discount factor for the backpropagation
	// if it's zero nothing happens!
	public double gamma;
	
	// generator for random numbers
	public Random r = new Random();
	
	
	
	public UCTSettings(IActor actor, IDefaultPolicy defaultPolicy,
			ATreePolicy treePolicy, IBackPropagation backPropagation,
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
	
	
	
	
}
