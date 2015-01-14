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
import emergence.safety.SafetyAdvance;
import emergence.strategy.AStarStrategy;
import emergence.strategy.ExplorerStrategy;
import emergence.targets.ATarget;
import emergence.util.ActionTimer;
import emergence.util.Helper;
import emergence.util.MapInfo;

/**
 * Heuristic agent (1st controller). It chooses the next action by using a
 * heuristic value
 */
public class HeuristicAgent extends AbstractPlayer {

	/** print out information. only DEBUG! */
	public static boolean VERBOSE = false;

	/** explorer startegy which is used */
	private ExplorerStrategy explorer;

	/** astar startegy which is used */
	private AStarStrategy astar;

	/** actual best target */
	private ATarget bestTarget = null;

	/** actual state observation */
	private StateObservation stateObs;



	/**
	 * Generates a new heuristic agent. It uses a heuristic and explorer
	 * strategy.
	 * 
	 * @param stateObs
	 *            state observation
	 * @param elapsedTimer
	 */
	public HeuristicAgent(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		this.stateObs = stateObs;
		ActionTimer timer = new ActionTimer(elapsedTimer);

		explorer = new ExplorerStrategy(stateObs);
		if (VERBOSE)
			System.out.println(String.format("[%s] %s", stateObs.getGameTick(),
					explorer));
		explorer.expand(stateObs, timer);

		if (VERBOSE) {
			System.out.println(MapInfo.info(stateObs));
			System.out.println(Factory.getGameDetection().detect(stateObs));
		}

	}

	/**
	 * Returns the action which will be executed. Uses a heuristic, a explorer
	 * strategy and the astar algorithm
	 */
	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		// print paramesters for cvs output
		if (stateObs.getGameTick() == 0) {
			printParam();
		}

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
			if (bestTarget != null)
				astar = new AStarStrategy(bestTarget);
		}

		if (astar == null) {
			a = new SafetyAdvance(5).getOneSafeAction(stateObs);
			explorer.expand(stateObs, timer);
			if (VERBOSE)
				System.out.println(String.format("[%s] %s",
						stateObs.getGameTick(), explorer));

		} else {

			boolean successfull = astar.expand(stateObs, timer);
			a = astar.act();

			if (VERBOSE) {
				System.out.println(String.format(
						"[%s] ASTAR: %s | %s | FOUND %s",
						stateObs.getGameTick(), bestTarget,
						bestTarget.getPosition(stateObs),
						astar.hasFound(stateObs)));
			}

			if (!successfull) {
				bestTarget = null;
				astar = null;
			}

		}

		if (stateObs.getGameTick() % 20 == 0) {
			if (VERBOSE)
				System.out.println(Factory.getEnvironment().toString());
			Factory.getEnvironment().reset();
			bestTarget = null;
			astar = null;
		}

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
				g.fillRect((int) v.x, (int) v.y, 10, 10);
			}

		}

	}

	/**
	 * Prints the parameters, used for csv output
	 */
	public void printParam() {
		String[] params = new String[2];

		params[0] = "HeuristicAgent";

		// parameters from class AStarStrategy

		// parameters from class ExplorerStrategy and subclasses
		params[1] = explorer.toCSVString();

		Helper.printParameter(params);
	}

}
