package emergence.agents;

import java.awt.Color;
import java.awt.Graphics2D;

import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.Environment;
import emergence.Factory;
import emergence.heuristics.DistanceRangeAscendingHeuristic;
import emergence.strategy.AStarStrategy;
import emergence.strategy.ExplorerStrategy;
import emergence.strategy.mcts.FieldTracker;
import emergence.strategy.mcts.MCTSNode;
import emergence.strategy.mcts.MCTStrategy;
import emergence.targets.ATarget;
import emergence.util.ActionTimer;
import emergence.util.MapInfo;

public class MCTSHeuristicAgent extends AbstractPlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = false;

	private ExplorerStrategy explorer;

	private AStarStrategy astar;

	private ATarget bestTarget = null;

	private StateObservation stateObs;

	private MCTStrategy strategy = null;

	public MCTSHeuristicAgent() {
	}

	public MCTSHeuristicAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		this.stateObs = stateObs;
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 100;

		strategy = new MCTStrategy();
		strategy.root = new MCTSNode(null);

		explorer = new ExplorerStrategy(stateObs);
		explorer.expand(stateObs, timer);

		if (VERBOSE) {
			System.out.println(String.format("[%s] %s", stateObs.getGameTick(), explorer));
			System.out.println(MapInfo.info(stateObs));
			System.out.println(Factory.getGameDetection().detect(stateObs));
		}

	}

	public Types.ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		// action that will be returned
		this.stateObs = stateObs;
		Environment env = Factory.getEnvironment();
		ACTIONS a = ACTIONS.ACTION_NIL;

		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 2;

		if (bestTarget == null) {
			if (env.getWinningTarget(stateObs) != null) {
				bestTarget = env.getWinningTarget(stateObs);
			} else if (env.getScoringTarget(stateObs) != null) {
				bestTarget = env.getScoringTarget(stateObs);
			}
			if (bestTarget != null) astar = new AStarStrategy(bestTarget);
		}
		

		if (astar == null) {

			timer.timeRemainingLimit = 30;
			explorer.expand(stateObs, timer);

			if (strategy.getHeuristic() == null) {
				if (env.getScoringTarget(stateObs) != null) {
					bestTarget = env.getScoringTarget(stateObs);
					if (bestTarget != null)
						strategy.setHeuristic(new DistanceRangeAscendingHeuristic(bestTarget));
				}
			}

			timer.timeRemainingLimit = 5;
			strategy.expand(stateObs, timer);
			a = strategy.act();
			strategy.rollingHorizon();
			if (VERBOSE) System.out.println(strategy);

		} else {

			boolean successfull = astar.expand(stateObs, timer);
			a = astar.act();

			if (VERBOSE) {
				System.out.println(String.format("[%s] ASTAR: %s | %s | FOUND %s", stateObs.getGameTick(), bestTarget,
						bestTarget.getPosition(stateObs), astar.hasFound(stateObs)));
			}

			if (!successfull) {
				bestTarget = null;
				astar = null;
				Factory.getEnvironment().reset();
			}

		}

		if (stateObs.getGameTick() % 20 == 0) {
			if (VERBOSE) {
				System.out.println(Factory.getEnvironment().toString());
			}
			Factory.getEnvironment().reset();
			bestTarget = null;
			astar = null;
		}

		FieldTracker.lastAction = a;

		return a;

	}

	/**
	 * Gets the player the control to draw something on the screen. It can be
	 * used for debug purposes.
	 * 
	 * @param g
	 *            Graphics device to draw to.
	 */
	public void draw(Graphics2D g) {

		if (astar != null) {
			// astar.getAstar().paint(g);
			if (bestTarget != null) {
				g.setColor(Color.GREEN);
				Vector2d v = bestTarget.getPosition(stateObs);
				if (v != null)
				g.fillRect((int) v.x, (int) v.y, 10, 10);
			}

		}

	}

}
