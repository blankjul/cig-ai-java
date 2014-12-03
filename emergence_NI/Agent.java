package emergence_NI;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ontology.Types;
import ontology.Types.ACTIONS;
import ontology.Types.WINNER;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import emergence_NI.helper.ActionTimer;
import emergence_NI.helper.LevelInfo;

public class Agent extends AThreadablePlayer {

	// print out information. only DEBUG!
	public static boolean VERBOSE = false;

	// random object
	public static Random r = new Random();

	// current evolution object that should be iterated
	public Evolution evo;

	// so often the next step is simulated. no dead!
	public int pessimistic = 5;

	// number of generation
	public int minGeneration = 4;

	// number of actions that are simulated
	private int pathLength = 20;

	// how many entries should the population has
	private int populationSize = 12;

	// number of the fittest to save for the next generation
	private int numFittest = 4;
	
	// switch the heuristic every x time steps
	private int switchHeuristic = 0;

	public Agent() {
	};

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		evo = new Evolution(pathLength, populationSize, numFittest, stateObs);

		LevelInfo.print(stateObs);
		if (VERBOSE) {
		}

		ActionTimer timerAll = new ActionTimer(elapsedTimer);
		timerAll.timeRemainingLimit = 50;

		while (timerAll.isTimeLeft()) {
			evo.expand(stateObs);
		}

	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		// slide the complete pool one action into the future
		evo.slidingWindow(stateObs);

		// start the evolution
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 7;
		while (timer.isTimeLeft()) {
			evo.expand(stateObs);
		}

		// get the next action by using pessimistic iterations
		Path selectedPath = getNextAction(timer, stateObs);
		ACTIONS nextAction = selectedPath.getFirstAction();

		// update statistics for the comparator at the next round
		updateStatistics(selectedPath);

		// set the path length adaptive
		
		int length = evo.getPathLength();
		System.out.println(evo.getNumGeneration());
		if (evo.getNumGeneration() < minGeneration) {
			if (length > 2)
				evo.setPathLength(length - 1);
		} else if (evo.getNumGeneration() > minGeneration) {
				evo.setPathLength(length + 1);
		}
		
		
		int tick = stateObs.getGameTick();
		if (switchHeuristic != 0 && tick > 0 && tick % switchHeuristic == 0) PathComparator.setType();
		
		
		// print the current status
		if (VERBOSE) {
			evo.print(evo.getPopulationSize());
			System.out.println(evo.getComparator());
			System.out.println(this.printToString());
			System.out.println("HEURISTIC " + PathComparator.TYPE);
		}

		// return the best action
		return nextAction;

	}

	private void updateStatistics(Path p) {
		if (p.getScore() != Double.NEGATIVE_INFINITY)
			PathComparator.reward[PathComparator.TYPE] += p.getScore();
		PathComparator.used[PathComparator.TYPE] += 1;

	}

	private Path getNextAction(ActionTimer timer, StateObservation stateObs) {
		Types.ACTIONS nextAction = ACTIONS.ACTION_NIL;
		boolean isDangerous = true;
		timer.timeRemainingLimit = 3;
		Path p = null;
		Set<Types.ACTIONS> forbiddenActions = new HashSet<Types.ACTIONS>();
		int i = 0;
		while (i < evo.getPopulationSize()) {
			p = (Path) evo.getPopulation().get(i);
			++i;
			nextAction = p.getFirstAction();

			// if we tried that action before continue
			if (forbiddenActions.contains(nextAction))
				continue;
			isDangerous = pessimisticNextAction(stateObs, nextAction, timer);
			if (!isDangerous)
				break;
			else
				forbiddenActions.add(nextAction);
		}
		return p;
	}

	private boolean pessimisticNextAction(StateObservation stateObs,
			ACTIONS nextAction, ActionTimer timer) {
		boolean dead = false;
		if (nextAction != null) {
			for (int i = 0; i < pessimistic; i++) {
				if (!timer.isTimeLeft())
					break;
				StateObservation tmp = stateObs.copy();
				tmp.advance(nextAction);
				if (tmp.getGameWinner() == WINNER.PLAYER_LOSES) {
					dead = true;
					if (VERBOSE) {
						System.out.println("-------------");
						System.out.println("DANGER! NEW EVOLUTION");
						System.out.println("-------------");
					}
					break;
				}
			}
		}
		return dead;
	}

	@Override
	public void createFromString(String parameter) {
		// set the correct actor
		if (parameter == null || parameter.equals(""))
			return;

		return;
		/*
		 * String[] array = parameter.split(" "); for (String s : array) {
		 * String key = s.split(":")[0]; String value = s.split(":")[1]; if
		 * (key.equals("pessimistic")) { this.pessimistic =
		 * Integer.valueOf(value); } else if (key.equals("pathLength")) {
		 * evo.setPathLength(Integer.valueOf(value)); } else if
		 * (key.equals("populationSize")) {
		 * evo.setPopulationSize(Integer.valueOf(value)); } else if
		 * (key.equals("numFittest")) {
		 * evo.setNumFittest(Integer.valueOf(value)); } }
		 */
	}

	@Override
	public String printToString() {
		String s = String.format(
				"pessimistic:%s pathLength:%s populationSize:%s numFittest:%s",
				pessimistic, evo.getPathLength(), evo.getPopulationSize(),
				evo.getNumFittest());
		return s;
	}

}
