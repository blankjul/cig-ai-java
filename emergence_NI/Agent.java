package emergence_NI;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_NI.GeneticStrategy.AGeneticStrategy;
import emergence_NI.GeneticStrategy.GeneticSettings;
import emergence_NI.GeneticStrategy.MemoryGenStrategy;
import emergence_NI.GeneticStrategy.NormalGenStartegy;
import emergence_NI.helper.ActionMap2;
import emergence_NI.helper.LevelInfo;

public class Agent extends AThreadablePlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = false;
	
	//action map for fast access of the actions
	public ActionMap2 map;

	
	public Agent() {};

	
	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		if (VERBOSE) {
			LevelInfo.print(stateObs);
		}
		
		this.map = new ActionMap2(stateObs);

	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		/*int counter = 0;
		StateObservation current = stateObs.copy();
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 2;
		Random r = new Random();
		while(timer.isTimeLeft()){
			counter++;
			current.advance(Helper.getRandomEntry(current.getAvailableActions(), r));
		}
		System.out.println("counter: " + counter);
		return current.getAvailableActions().get(0);
		*/
		map.checkValidation(stateObs);
		
		GeneticSettings settings = new GeneticSettings();
		
		settings.setDefaultSettings();
		
		AGeneticStrategy strategy = new MemoryGenStrategy(stateObs, settings, elapsedTimer);
		
		Types.ACTIONS action = strategy.compute();
		
		//System.out.println("aplying action: " + action.toString());
		//System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
		//System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
		//System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
		return action;
		
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
