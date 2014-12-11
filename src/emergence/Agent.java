package emergence;

import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.strategy.AStrategy;
import emergence.strategy.StayAlive;
import emergence.util.ActionTimer;
import emergence.util.Helper;
import emergence.util.MapInfo;

public class Agent extends AbstractPlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = true;

	public Agent() {
	};

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		System.out.println(MapInfo.info(stateObs));
		System.out.println(Factory.getGameDetection().detect(stateObs)); 
		
		ActionTimer timerAll = new ActionTimer(elapsedTimer);
		timerAll.timeRemainingLimit = 150;
		StateObservation tmpState = stateObs.copy();
		while (timerAll.isTimeLeft()) {
			ACTIONS random = Helper.getRandomAction(stateObs);
			Factory.getSimulator().advance(tmpState, random);
			if (tmpState.isGameOver()) tmpState = stateObs.copy();
			timerAll.addIteration();
		}
		
	}

	
	
	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {
		AStrategy strategy = new StayAlive(stateObs);
		ACTIONS a = strategy.act();
		if (stateObs.getGameTick() == 0 || stateObs.getGameTick() % 100 == 0) System.out.println(Factory.getSimulator().toString());
		
		
		ActionTimer timerAll = new ActionTimer(elapsedTimer);
		timerAll.timeRemainingLimit = 2;
		StateObservation tmpState = stateObs.copy();
		while (timerAll.isTimeLeft()) {
			ACTIONS random = Helper.getRandomAction(stateObs);
			Factory.getSimulator().advance(tmpState, random);
			if (tmpState.isGameOver()) tmpState = stateObs.copy();
			timerAll.addIteration();
		}
		
		return a;
		
	}

}
