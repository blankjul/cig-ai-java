package emergence_RL.uct;

import emergence_RL.heuristic.HeuristicTreePolicy;
import emergence_RL.uct.actor.MostVisitedAdvanced;
import emergence_RL.uct.backpropagation.Backpropagation;
import emergence_RL.uct.defaultPoliciy.RandomDeltaPolicy;
import emergence_RL.uct.sample.SampleActor;
import emergence_RL.uct.sample.SampleBackpropagation;
import emergence_RL.uct.sample.SampleDefaultPolicy;
import emergence_RL.uct.sample.SampleTreePolicy;


/**
 * This factory return different settings of the uct search.
 * There are a lot of parameter that can differ. Each new settings
 * has advantages and disadvantages.
 *
 */
public class UCTFactory {

	/**
	 * The default settings that can be different all the time.
	 */
	public static UCTSettings createDefault() {
		UCTSettings settings = new UCTSettings();
		settings.maxDepth = 10;
		settings.actor = new MostVisitedAdvanced();
		settings.treePolicy = new SampleTreePolicy();
		settings.defaultPolicy = new RandomDeltaPolicy();
		settings.backPropagation = new Backpropagation();
		settings.c = Math.sqrt(2);
		settings.gamma = 1;
		return settings;

	}
	
	
	/**
	 * Play like the standard mcts search of the controller sample.
	 */
	public static UCTSettings createSample() {
		UCTSettings settings = new UCTSettings();
		settings.maxDepth = 10;
		settings.actor = new SampleActor();
		settings.treePolicy = new SampleTreePolicy();
		settings.defaultPolicy = new SampleDefaultPolicy();
		settings.backPropagation = new SampleBackpropagation();
		settings.c = Math.sqrt(2);
		settings.gamma = 1;
		return settings;

	}
	

	/**
	 * Use heuristic while playing.
	 */
	public static UCTSettings createHeuristic() {
		UCTSettings settings = new UCTSettings();
		settings.maxDepth = 10;
		settings.actor = new MostVisitedAdvanced();
		settings.treePolicy = new HeuristicTreePolicy();
		settings.defaultPolicy = new RandomDeltaPolicy();
		settings.backPropagation = new Backpropagation();
		settings.c = Math.sqrt(2);
		settings.gamma = 1;
		return settings;

	}
}
