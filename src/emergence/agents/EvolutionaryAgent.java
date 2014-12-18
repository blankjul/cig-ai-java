package emergence.agents;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ontology.Types;
import ontology.Types.ACTIONS;
import ontology.Types.WINNER;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence.strategy.evolution.Evolution;
import emergence.strategy.evolution.EvolutionaryNode;
import emergence.util.ActionTimer;

public class EvolutionaryAgent extends AbstractPlayer{

	// print out information. only DEBUG!
	public static boolean VERBOSE = false;

	// random object
	public static Random r = new Random();

	// current evolution object that should be iterated
	public Evolution evo;

	// so often the next step is simulated. no dead!
	public int pessimistic = 5;

	// number of actions that are simulated
	public int pathLength = 6;

	// how many entries should the population has
	public int populationSize = 14;

	// number of the fittest to save for the next generation
	public int numFittest = 5;

	// update path length
	public boolean updatePathLength = false;
	
	// if update path length the target generation
	public int minGeneration = 4;

	
	
	public EvolutionaryAgent() {
	};

	
	public EvolutionaryAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		evo = new Evolution(pathLength, populationSize, numFittest, stateObs);
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 50;
		evo.expand(stateObs, timer);
		

	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {
		
		// slide the complete pool one action into the future
		evo.slidingWindow(stateObs);

		// start the evolution
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 7;
		evo.expand(stateObs, timer);
		

		// get the next action by using pessimistic iterations
		EvolutionaryNode selectedPath = getNextAction(timer, stateObs);
		ACTIONS nextAction = selectedPath.getFirstAction();

		
		// set the path length adaptive
		if (updatePathLength) updatePathLength(stateObs);
		
		
		// print the current status
		if (VERBOSE) {
			evo.print(evo.getPopulationSize());
		}

		// return the best action
		return nextAction;

	}

	
	private void updatePathLength(StateObservation stateObs) {
		int length = evo.getPathLength();
		// System.out.println(evo.getNumGeneration());
		if (evo.getNumGeneration() < minGeneration) {
			if (length > 2)
				evo.setPathLength(stateObs, length - 1);
		} else if (evo.getNumGeneration() > minGeneration) {
			if (length < 40) evo.setPathLength(stateObs, length + 1);
		}
		pathLength = evo.getPathLength();
	}

	
	private EvolutionaryNode getNextAction(ActionTimer timer, StateObservation stateObs) {
		Types.ACTIONS nextAction = ACTIONS.ACTION_NIL;
		boolean isDangerous = true;
		timer.timeRemainingLimit = 3;
		EvolutionaryNode p = null;
		Set<Types.ACTIONS> forbiddenActions = new HashSet<Types.ACTIONS>();
		int i = 0;
		while (i < evo.getPopulationSize()) {
			p = evo.getPopulation().get(i);
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


}
