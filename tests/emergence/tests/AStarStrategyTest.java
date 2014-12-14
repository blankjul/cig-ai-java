package emergence.tests;

import java.util.Random;

import org.junit.Test;

import tools.Vector2d;
import core.ArcadeMachine;
import core.competition.CompetitionParameters;
import core.game.Game;
import core.player.AbstractPlayer;
import emergence.targets.ATarget;
import emergence.targets.ATarget.TYPE;
import emergence.targets.ImmovableTarget;

public class AStarStrategyTest {

	
	
	@Test
	public void portal1Test() {
		
		
		Game toPlay = Base.loadTestGame("scenario1.txt", "s1_nextToWall.txt");
		ATarget t = new ImmovableTarget(TYPE.Immovable, 2, new Vector2d(728.0,
				252.0));
		
		ArcadeMachine.warmUp(toPlay, CompetitionParameters.WARMUP_TIME);
		// Then, play the game.
		
		AbstractPlayer player = new AStarAgent(toPlay.getObservation(), t);
        toPlay.playGame(player, new Random().nextInt());
		
		// Finally, when the game is over, we need to tear the player down.
		ArcadeMachine.tearPlayerDown(player);

	}
	
	

	
	
	
}
