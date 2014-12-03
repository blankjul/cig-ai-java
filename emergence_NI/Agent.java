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
	public Evolution evo = new Evolution();

	// so often the next step is simulated. no dead!
	public int pessimistic = 5;

	public int minGeneration = 4;

	public Agent() {
	};

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		if (VERBOSE) {
			LevelInfo.print(stateObs);
		}

		ActionTimer timerAll = new ActionTimer(elapsedTimer);
		timerAll.timeRemainingLimit = 50;

		while (timerAll.isTimeLeft()) {
			evo.expand(stateObs);
		}

	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		evo.slidingWindow(stateObs);

		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 7;
		while (timer.isTimeLeft()) {
			evo.expand(stateObs);
		}

		Types.ACTIONS nextAction = ACTIONS.ACTION_NIL;

		boolean isDangerous = true;
		timer.timeRemainingLimit = 4;
		Path p = null;
		Set<Types.ACTIONS> forbiddenActions = new HashSet<Types.ACTIONS>();
		int i = 0;
		while (i < evo.population.size()) {

			// get the next action
			if (p == null) {
				p = evo.best();
			} else {
				p = (Path) evo.population.get(i);
				++i;
			}
			nextAction = p.list.get(0);

			// if we tried that action before continue
			if (forbiddenActions.contains(nextAction))
				continue;
			isDangerous = pessimisticNextAction(stateObs, nextAction, timer);
			if (!isDangerous)
				break;
			else
				forbiddenActions.add(nextAction);

		}

		if (p.getScore() != Double.NEGATIVE_INFINITY)
			PathComparator.reward[PathComparator.TYPE] += p.getScore();
		PathComparator.used[PathComparator.TYPE] += 1;

		// adaptive path length that we get always four generations!

		int length = evo.getPathLength();
		System.out.println(evo.getNumGeneration());
		if (evo.getNumGeneration() < minGeneration) {
			if (r.nextDouble() > 0.5) {
				if (length > 2)
					evo.setPathLength(length - 1);
			}
		} else if (evo.getNumGeneration() > minGeneration) {
			evo.setPathLength(length + 1);
		}

		if (VERBOSE) {
			evo.print(evo.populationSize);
			System.out.println(evo.comp);
			System.out.println("PATHLENGTH: " + evo.getPathLength());
		}

		return nextAction;

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
		String[] array = parameter.split(" ");
		for (String s : array) {
			String key = s.split(":")[0];
			String value = s.split(":")[1];
			if (key.equals("pessimistic")) {
				this.pessimistic = Integer.valueOf(value);
			} else if (key.equals("pathLength")) {
				evo.setPathLength(Integer.valueOf(value));
			} else if (key.equals("populationSize")) {
				evo.populationSize = Integer.valueOf(value);
			} else if (key.equals("numFittest")) {
				evo.numFittest = Integer.valueOf(value);
			}
		}
	}

	@Override
	public String printToString() {
		String s = String.format(
				"pessimistic:%s pathLength:%s populationSize:%s numFittest:%s",
				pessimistic, evo.getPathLength(), evo.populationSize,
				evo.numFittest);
		return s;
	}

}
