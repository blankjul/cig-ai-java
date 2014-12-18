package emergence.tests;

import LevelLoader;

import org.junit.Test;

import tools.ElapsedCpuTimer;
import core.competition.CompetitionParameters;
import core.game.Game;
import core.game.StateObservation;
import emergence.strategy.mcts.MCTSNode;
import emergence.strategy.mcts.MCTStrategy;
import emergence.util.ActionTimer;

public class MCTSTest {

	@Test
	public void simulateTest() {
		Game g = Base.loadTestGame("scenario1.txt", "s1_nextToScore.txt");
		StateObservation myStateObs = g.getObservation();
				
				
		ElapsedCpuTimer ect = new ElapsedCpuTimer(ElapsedCpuTimer.TimerType.CPU_TIME);
		ect.setMaxTimeMillis(CompetitionParameters.INITIALIZATION_TIME);

		LevelLoader.show(g);
		
		MCTStrategy strategy = new MCTStrategy(new MCTSNode(null));
		strategy.root = new MCTSNode(null);
		ActionTimer timer = new ActionTimer(ect);
		
		timer.timeRemainingLimit = 100;
		strategy.expand(myStateObs, timer);
		

		System.out.println(strategy);
		System.out.println("------------");
	}
}
