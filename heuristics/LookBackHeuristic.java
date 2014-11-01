package emergence_HR.heuristics;

import java.util.ArrayList;

import ontology.Types;
import core.game.StateObservation;
import emergence_HR.TreeNodeRandom;

/**
 * this heuristic compares the actual node with the father node
 * @author spakken
 *
 */
public class LookBackHeuristic extends StateHeuristic{
	
	protected ArrayList<Types.ACTIONS> actions_forbidden;
	
	public LookBackHeuristic(StateObservation stateObs){
		
	}

	/**
	 * just a dummy function, when you use this heuristic you 
	 * have to use the overloaded function
	 */
	public double evaluateState(StateObservation stateObs) {
		return 0;
	}
	
	/**
	 * compute an heuristic value depending on the last actions and
	 * positionsof the Avatar
	 * @param node
	 * @return score
	 */
	public double evaluateState(TreeNodeRandom node){
		
		StateObservation stateObs = node.getObservation();
		Types.ACTIONS action = node.getAction();
		StateObservation father_stateObs = node.getFather().getObservation();
		
		//0 is the default score, negative values are bad, positives good
		double score = 0;
		
		//when the action from the father was ineffective, dont
		//use this action again
		if(action != Types.ACTIONS.ACTION_USE && action != Types.ACTIONS.ACTION_NIL
				&& action != Types.ACTIONS.ACTION_ESCAPE
				&& stateObs.getAvatarPosition() == father_stateObs.getAvatarPosition()){
			
			//dont go the same way
			actions_forbidden.add(action);
			
			//senseless to decrement the score here?????????
			score--;
		}
		
		//if we win, pick this action, if we lose, dont pick the action
		if(stateObs.getGameWinner() == Types.WINNER.PLAYER_WINS){
			return Double.POSITIVE_INFINITY;
		} else if (stateObs.getGameWinner() == Types.WINNER.PLAYER_LOSES) {
            return Double.NEGATIVE_INFINITY;
        }
		
		//include the portalHeuristic, go a portal -> it is good
		//TODO
		
		//Gamescore
		score += stateObs.getGameScore();
		
		//if the score raised scince the last action, add more score
		//senseless????????????????
		if(stateObs.getGameScore() > father_stateObs.getGameScore()){
			score = score * 1.10;
		}
		
		//if we kill an NPC, add Points to the score
		//TODO
		
		return score;
	}
}
