package emergence.agents;

import java.awt.Graphics2D;

import ontology.Types;
import tools.ElapsedCpuTimer;
import tools.Vector2d;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.strategy.AStarStrategy;
import emergence.targets.ATarget;
import emergence.targets.ATarget.TYPE;
import emergence.targets.ImmovableTarget;
import emergence.util.ActionTimer;
import emergence.util.MapInfo;

public class AStarAgent extends AbstractPlayer {

	// astar object that aims to find the path
	private AStarStrategy strategy;
	
	public AStarAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		
		super();
		System.out.println(MapInfo.info(stateObs));
		ATarget target = new ImmovableTarget(TYPE.Portal, 14, new Vector2d(736.0, 80.0));

		// expanding the astar algorithm while there is time
		ActionTimer timer = new ActionTimer(elapsedTimer);
		
		strategy = new AStarStrategy(stateObs, timer, target);
		strategy.expand();

		System.out.println(timer.status());

	}

	
	public Types.ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		ActionTimer timer = new ActionTimer(elapsedTimer);
		strategy.reset(stateObs, timer);
		strategy.expand();
		return strategy.act();
	}
	
	
	/**
     * Gets the player the control to draw something on the screen.
     * It can be used for debug purposes.
     * @param g Graphics device to draw to.
     */
    public void draw(Graphics2D g)
    {
     // if (astar!= null) astar.paint(g);
    }
	
	

}
