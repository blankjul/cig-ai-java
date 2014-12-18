package emergence;

import java.awt.Color;
import java.awt.Graphics2D;

import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.safety.SafetyAdvance;
import emergence.strategy.AStarStrategy;
import emergence.strategy.ExplorerStrategy;
import emergence.targets.ATarget;
import emergence.util.ActionTimer;
import emergence.util.MapInfo;

public class AgentPrimary extends AbstractPlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = false;

	private ExplorerStrategy explorer;

	private AStarStrategy astar;

	private ATarget bestTarget = null;
	
	private StateObservation stateObs;
	
	
	public AgentPrimary() {
	}
	

	public AgentPrimary(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		
		this.stateObs = stateObs;
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 50;
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
			a = new SafetyAdvance(5).getOneSafeAction(stateObs);
			explorer.expand(stateObs, timer);
			if (VERBOSE)  System.out.println(String.format("[%s] %s",stateObs.getGameTick() ,explorer));
				
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
			}
			
		}

		if (stateObs.getGameTick() % 20 == 0) {
			if (VERBOSE) System.out.println(Factory.getEnvironment().toString());
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
				if (v != null) g.fillRect((int) v.x, (int) v.y, 10, 10);
			}
		}
	}

}
