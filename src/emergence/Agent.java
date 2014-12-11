package emergence;

import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.util.MapInfo;

public class Agent extends AbstractPlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = true;

	public Agent() {
	};

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		System.out.println(MapInfo.info(stateObs));
		System.out.println(Factory.getGameDetection().detect(stateObs)); 
		
		
		Factory.getSimulator().advance(stateObs, ACTIONS.ACTION_LEFT);
		
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {
		
		

				return null;
		
	}

}
