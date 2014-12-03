package emergence_NI;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_NI.helper.LevelInfo;

public class EvoAgent extends AThreadablePlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = false;

	
	public EvoAgent() {};

	
	public EvoAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		if (VERBOSE) {
			LevelInfo.print(stateObs);
		}

	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		return Types.ACTIONS.ACTION_NIL;

	}




	
	
	@Override
	public String printToString() {
		/*
		String s = String.format(
				"evo_tick:%s pool_size:%s pool_fittest:%s evo_time:%s",
				EVO_GAME_TICK, POOL_SIZE, POOL_FITTEST, TIME_FOR_EVOLUTION);
				*/
		return "";
	}

	@Override
	public void createFromString(String parameter) {
		// set the correct actor
		/*
		if (parameter == null || parameter.equals(""))
			return;
		String[] array = parameter.split(" ");
		for (String s : array) {
			String key = s.split(":")[0];
			String value = s.split(":")[1];
			if (key.equals("evo_tick")) {
				this.EVO_GAME_TICK = Integer.valueOf(value);
			} else if (key.equals("pool_size")) {
				this.POOL_SIZE = Integer.valueOf(value);
			} else if (key.equals("pool_fittest")) {
				this.POOL_FITTEST = Integer.valueOf(value);
			} else if (key.equals("evo_time")) {
				this.TIME_FOR_EVOLUTION = Integer.valueOf(value);
			}
		}
		*/
	}

}
