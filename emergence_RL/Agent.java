package emergence_RL;

import java.util.Random;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_RL.helper.ActionTimer;
import emergence_RL.strategy.AStrategy;
import emergence_RL.strategy.UCTSearchCopyStates;
import emergence_RL.strategy.uct.actor.HighestUCT;
import emergence_RL.strategy.uct.actor.IActor;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

public class Agent extends AThreadablePlayer {

	// print out information. only DEBUG!
	final private boolean VERBOSE = true;

	// generator for random numbers
	protected Random rand = new Random();
	
	
	/*
	 * Configuration for the MCTS Tree
	 */
	private int maxDepth = 10;
	private double C = Math.sqrt(2);
	private double epsilon = 1e-6;
	private IActor actor = new HighestUCT();
	private double gamma = 0.8;

	
	
	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		Tree tree = new Tree(new Node(stateObs));
		AStrategy strategy = new UCTSearchCopyStates(tree, rand, maxDepth, C, epsilon, gamma, actor);

		boolean hasNext = true;
		ActionTimer timer = new ActionTimer(elapsedTimer);
		while (timer.isTimeLeft() && hasNext) {
			strategy.expand();
			timer.addIteration();
		}
		if (VERBOSE) {
			System.out.println(strategy);
			System.out.println("--------------------------");
		}
		
		Types.ACTIONS a = strategy.act();
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
