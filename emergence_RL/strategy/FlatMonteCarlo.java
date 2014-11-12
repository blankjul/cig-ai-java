package emergence_RL.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import ontology.Types;
import ontology.Types.ACTIONS;
import ontology.Types.WINNER;
import core.game.StateObservation;
import emergence_RL.tree.Tree;

public class FlatMonteCarlo extends AStrategy {

	// thats the height of the tree - that is fix
	protected int n = 10;

	// generator for random numbers
	protected Random rand = new Random();

	// this maps the possible next N actions and the score!
	protected Map<List<Types.ACTIONS>, Integer> map;

	protected int counter = 0;

	// there three variable are needed for the current iteration
	protected List<Types.ACTIONS> currentPath = null;
	protected int currentScore;
	protected StateObservation currentState;

	
	public FlatMonteCarlo(Tree tree, int nSteps) {
		super(tree);
		this.n = nSteps;
		map = new HashMap<List<ACTIONS>, Integer>();
		currentState = tree.root.stateObs.copy();
		currentPath = randomSteps(n);
	}

	public Types.ACTIONS act() {
		Types.ACTIONS action = Types.ACTIONS.ACTION_NIL;
		Entry<List<ACTIONS>, Integer> best = null;
		for (Entry<List<ACTIONS>, Integer> entry : map.entrySet())
		{
		    int score = entry.getValue();
			if (score > bestScore) {
				action = entry.getKey().get(0);
				best = entry;
			}
			
		}
		System.out.println(Arrays.toString(best.getKey().toArray()) + " -> " + best.getValue());
		return action;
	}
	
	@Override
	public boolean expand() {
		// if it is the first time and no path exists or path was executed
		if (counter >= currentPath.size()) {
			map.put(currentPath, currentScore);

			currentState = tree.root.stateObs.copy();
			currentScore = 0;
			currentPath = randomSteps(n);
			counter = 0;
		} else {
			// simulate the next step
			currentState.advance(currentPath.get(counter));
			
			if (currentState.getGameWinner() == WINNER.PLAYER_WINS) {
				map.clear();
				map.put(currentPath, Integer.MAX_VALUE);
				return false;
			} else if (currentState.getGameWinner() == WINNER.PLAYER_LOSES){
				map.put(currentPath, Integer.MIN_VALUE);
				counter = currentPath.size();
			}
			currentScore += currentState.getGameScore();
			++counter;
		}
		return true;
	}

	/**
	 * Returns an list of n random steps!
	 */
	protected List<Types.ACTIONS> randomSteps(int nSteps) {
		List<Types.ACTIONS> result = new ArrayList<Types.ACTIONS>();
		List<Types.ACTIONS> actions = tree.root.stateObs.getAvailableActions();
		for (int i = 0; i < nSteps; i++) {
			int index = rand.nextInt(actions.size());
			result.add(actions.get(index));
		}
		return result;
	}

}
