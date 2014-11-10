package emergence_HR;

import java.util.Random;

import javax.swing.text.SimpleAttributeSet;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.AHeuristic;
import emergence_HR.heuristics.SimpleStateHeuristic;
import emergence_HR.heuristics.WinScoreHeuristic;
import emergence_HR.tree.AHeuristicTree;
import emergence_HR.tree.HeuristicTreeAStar;
import emergence_HR.tree.Node;

public class Agent extends AbstractPlayer {

	// print out information. only DEBUG!
	final private boolean VERBOSE = false;

	// heuristic that is used
	final AHeuristic heuristic = new WinScoreHeuristic();
	
	//counter for the null action
	private static int null_counter = 0;
	
	//enable the random action after 3 times no action found
	private boolean random_action = true;

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		AHeuristicTree gameTree = new HeuristicTreeAStar(
				new Node(stateObs), heuristic);

		ActionTimer timer = new ActionTimer(elapsedTimer);
		gameTree.expand(timer);
		Types.ACTIONS action = gameTree.action();
		System.out.println("pos: " + stateObs.getAvatarPosition() + "  Action: " + (action==null ? "null" : action.toString()));
		
		//3 times no action, just do somethig
		if(action == null && random_action){
			null_counter++;
			if(null_counter >= 8){
				Random rand = new Random();
				action = stateObs.getAvailableActions().get(rand.nextInt(stateObs.getAvailableActions().size()));
			}
		}else{
			null_counter = 0;
		}
		
		//System.out.println("blocksize:: " + stateObs.getBlockSize());
		
		if (VERBOSE)
			System.out.println(timer.status());
		
		return action;

	}
}
