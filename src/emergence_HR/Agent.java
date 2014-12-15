package emergence_HR;

import java.awt.Color;
import java.awt.Graphics2D;

import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.Factory;
import emergence.safety.SafetyAdvance;
import emergence.strategy.AStarStrategy;
import emergence.strategy.ExplorerStrategy;
import emergence.targets.ATarget;
import emergence.targets.ATarget.TYPE;
import emergence.targets.ImmovableTarget;
import emergence.targets.TargetFactory;
import emergence.util.ActionTimer;
import emergence.util.MapInfo;
import emergence.util.pair.Pair;

public class Agent extends AbstractPlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = true;

	private ExplorerStrategy explorer;

	private AStarStrategy astar;

	private ATarget bestTarget = null;


	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		ActionTimer timer = new ActionTimer(elapsedTimer);

		explorer = new ExplorerStrategy(stateObs);
		explorer.expand(stateObs);

		if (VERBOSE) {
			System.out.println(MapInfo.info(stateObs));
			System.out.println(Factory.getGameDetection().detect(stateObs));
		}

	}

	public Types.ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		// return new SafetyGridSearch().getOneSafeAction(stateObs);
		// return new StayAliveStrategy(stateObs, 10).act();

		// action that will be returned
		ACTIONS a = ACTIONS.ACTION_NIL;

		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 2;

		int itype = -1;
		if (!Factory.getEnvironment().getWinSprites().isEmpty())
			itype = Factory.getEnvironment().getWinSprites().iterator().next();
		if (itype == -1 && !Factory.getEnvironment().getScoreSprites().isEmpty())
			itype = Factory.getEnvironment().getScoreSprites().iterator().next();

		// if a good target was found
		if (itype != -1) {
			Pair<TYPE, Observation> p = TargetFactory.getObservationFromType(itype, stateObs);
			if (p == null) {
				bestTarget = null;
				astar = null;
			} else {
				TYPE type = p._1();
				if (TargetFactory.isImmovable(type)) {
					bestTarget = new ImmovableTarget(type, itype, p._2().position);
				} else {
					bestTarget = new MovableTarget(type, itype, stateObs);
				}
			}
		}

		if (bestTarget != null) {
			// System.out.println(bestTarget);
			astar = new AStarStrategy(stateObs, timer, bestTarget);
		}

		if (astar == null) {
			if (VERBOSE)  System.out.println("STAY ALIVE AND EXPLORE");
			a = new SafetyAdvance(5).getOneSafeAction(stateObs);
			explorer.reset(stateObs, timer);
			explorer.expand();
		} else {
			astar.reset(stateObs, timer);
			astar.expand();
			if (VERBOSE) System.out.println("ASTAR: " + bestTarget + " FOUND " + astar.hasFound());
			a = astar.act();
			
		}

		if (stateObs.getGameTick() == 0 || stateObs.getGameTick() % 20 == 0) {
			System.out.println(Factory.getEnvironment().toString());
			//Factory.getEnvironment().reset();
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
			//astar.getAstar().paint(g);
			g.setColor(Color.GREEN);
			Vector2d v = astar.getTarget().position();
            g.fillRect((int)v.x,(int) v.y, 10, 10);
		}
		
	}

}
