package emergence;

import java.awt.Graphics2D;

import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.safety.SafetyAdvance;
import emergence.strategy.AStarStrategy;
import emergence.strategy.ExplorerStrategy;
import emergence.targets.ATarget;
import emergence.util.ActionTimer;
import emergence.util.MapInfo;

public class Agent extends AbstractPlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = true;

	private ExplorerStrategy explorer;

	private AStarStrategy astar;

	private ATarget bestTarget = null;
	
	private StateObservation currentStateObs;

	public Agent() {
	}

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		currentStateObs = stateObs;
		ActionTimer timer = new ActionTimer(elapsedTimer);

		explorer = new ExplorerStrategy(stateObs);
		if (VERBOSE)  System.out.println(String.format("[%s] %s",stateObs.getGameTick() ,explorer));
		explorer.expand(stateObs, timer);

		if (VERBOSE) {
			System.out.println(MapInfo.info(stateObs));
			System.out.println(Factory.getGameDetection().detect(stateObs));
		}

	}

	public Types.ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		// action that will be returned
		Environment env = Factory.getEnvironment();
		currentStateObs = stateObs;
		ACTIONS a = ACTIONS.ACTION_NIL;

		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 2;

		if (bestTarget == null) {
			if (env.getWinningTarget() != null) {
				bestTarget = env.getWinningTarget();
			} else if (env.getScoringTarget(stateObs) != null) {
				bestTarget = env.getScoringTarget(stateObs);
			}
			if (bestTarget != null) astar = new AStarStrategy(bestTarget);
		}
		

		if (astar == null) {
			a = new SafetyAdvance(5).getOneSafeAction(stateObs);
			explorer.expand(stateObs, timer);
			if (VERBOSE)  System.out.println(String.format("[%s] %s",stateObs.getGameTick() ,explorer));
				
		} else {
			
			astar.expand(stateObs, timer);
			a = astar.act();
				
			if (VERBOSE) {
				System.out.println(String.format("[%s] ASTAR: %s | %s | FOUND %s", stateObs.getGameTick(), bestTarget,
						bestTarget.getPosition(stateObs), astar.hasFound(stateObs)));
			}
		}

		if (stateObs.getGameTick() == 0 || stateObs.getGameTick() % 20 == 0) {
			System.out.println(Factory.getEnvironment().toString());
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
				//g.setColor(Color.GREEN);
				//Vector2d v = bestTarget.getPosition(currentStateObs);
				//g.fillRect((int) v.x, (int) v.y, 10, 10);
			}
			
		}

	}

}
