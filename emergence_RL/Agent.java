package emergence_RL;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_RL.helper.ActionTimer;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;
import emergence_RL.uct.UCTSearch;
import emergence_RL.uct.UCTSettings;
import emergence_RL.uct.actor.IActor;
import emergence_RL.uct.actor.MostVisited;
import emergence_RL.uct.backpropagation.DicountedBackpropagation;
import emergence_RL.uct.backpropagation.IBackPropagation;
import emergence_RL.uct.defaultPoliciy.IDefaultPolicy;
import emergence_RL.uct.defaultPoliciy.RandomPolicy;
import emergence_RL.uct.treePolicy.ATreePolicy;
import emergence_RL.uct.treePolicy.UCTPolicy;

public class Agent extends AThreadablePlayer {

	// print out information. only DEBUG!
	final private boolean VERBOSE = false;

	
	/*
	 * Configuration for the MCTS Tree
	 */
	private int maxDepth = 10;
	private IActor actor = new MostVisited();
	private IDefaultPolicy defaultPolicy = new RandomPolicy();
	private ATreePolicy treePolicy = new UCTPolicy();
	private IBackPropagation backPropagation = new DicountedBackpropagation();
	private double C = Math.sqrt(2);
	private double gamma = 1;
	
	
	// finally the settings for the tree!
	private UCTSettings settings;
	

	
	
	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		settings = new UCTSettings(actor, defaultPolicy, treePolicy, backPropagation, maxDepth, C, gamma);
	}

	
	
	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		Tree tree = new Tree(new Node(stateObs));
		
		UCTSearch uct = new UCTSearch(tree, settings);

		boolean hasNext = true;
		ActionTimer timer = new ActionTimer(elapsedTimer);
		while (timer.isTimeLeft() && hasNext) {
			uct.expand();
			timer.addIteration();
		}
		if (VERBOSE) {
			System.out.println(uct);
			System.out.println("--------------------------");
		}
		
		Types.ACTIONS a = uct.act();
		return a;
		
	}

	@Override
	public void initFromString(String parameter) {
		//this.maxDepth = Integer.valueOf(parameter);
	}

	@Override
	public String setToString() {
		return String.valueOf(maxDepth);
	}

}
