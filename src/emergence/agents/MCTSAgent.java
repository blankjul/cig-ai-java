package emergence.agents;

import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.Factory;
import emergence.strategy.mcts.MCTSNode;
import emergence.strategy.mcts.MCTStrategy;
import emergence.util.ActionTimer;
import emergence.util.MapInfo;

/**
 * MCTS agent (2nd controller). Uses MCTS-tree-search to build and search in a
 * tree.
 * 
 * @author spakken
 *
 */
public class MCTSAgent extends AbstractPlayer {

	/** print out information. only DEBUG! */
	public static final boolean VERBOSE = false;

	/** use the rolling horizon */
	public boolean rollingHorizon = true;

	/** the MCTS strategy used */
	private MCTStrategy strategy = null;

	/** the actual best action */
	private ACTIONS lastAction = ACTIONS.ACTION_NIL;

	/**
	 * Constructs a new MCTS agent and starts the simulation.
	 * 
	 * @param stateObs
	 * @param elapsedTimer
	 */
	public MCTSAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		System.out.println("MCTS");
		strategy = new MCTStrategy(new MCTSNode(null));
		strategy.root = new MCTSNode(null);
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 100;
		strategy.expand(stateObs, timer);
	}

	/**
	 * Returns the action which will be executed. Uses the MCTS strategy, tree
	 * policy, default policy and rolling horizon
	 */
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		// track the last action
		Factory.getFieldTracker().track(stateObs, lastAction);

		// create a timer
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 3;

		if (stateObs.getGameTick() != 0) {
			if (rollingHorizon) {
				strategy.rollingHorizon(lastAction);
			} else {
				strategy = new MCTStrategy(new MCTSNode(null));
			}
		}

		strategy.expand(stateObs, timer);
		ACTIONS a = strategy.act();
		lastAction = a;

		if (VERBOSE)
			System.out.println(strategy);

		return a;
	}

}
