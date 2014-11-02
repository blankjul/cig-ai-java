package emergence_HR.heuristics;

import java.util.ArrayList;

import ontology.Types;
import tools.Vector2d;
import core.game.StateObservation;
import emergence_HR.TreeNodeRandom;

/**
 * this heuristic compares the actual node with the father node
 * @author spakken
 *
 */
public class LookBackHeuristic extends StateHeuristic{
	
	//actions which are forbidden for the future
	protected ArrayList<Types.ACTIONS> actions_forbidden = new ArrayList<Types.ACTIONS>();

	//found a path on which the avatar can score or win
	private boolean winningpath = false;
	
	
	//Winning path Object, contains the Actions to win the game
	
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
	 * positions of the avatar
	 * @param node
	 * @return score
	 */
	public double evaluateState(TreeNodeRandom node){
		
		//clear the list, its from the old node (same Heuristic-Object is used
		//for lots of nodes)
		actions_forbidden.clear();
		
		//get the state Observation
		StateObservation stateObs = node.getObservation();
		
		//the action which was used by the father-Node to get to this node
		Types.ACTIONS action = node.getAction();
		
		//the state Oberservation from the father, to compare the actual node
		//with the father
		StateObservation father_stateObs = node.getFather().getObservation();
		
		//0 is the default score, negative values are bad, positives good
		double score = 0;
		
		//values which only take the information of one Node
		double actual_score = stateObs.getGameScore();
		double father_score = father_stateObs.getGameScore();
		boolean actual_player_win = stateObs.getGameWinner() == Types.WINNER.PLAYER_WINS;
		boolean actual_player_los = stateObs.getGameWinner() == Types.WINNER.PLAYER_LOSES;
		
		//values to compare the actual node with the father node
		boolean same_position = stateObs.getAvatarPosition().equals(father_stateObs.getAvatarPosition());
		boolean first_step = node.getFather().getFather() == null;
		boolean move_action_ineffective = action != Types.ACTIONS.ACTION_USE && same_position;
		int game_score = (int) (actual_score - father_score);		//1=score raise 0=same score -1= score is fallen
		
		//when the move action from the father was ineffective (no move no points)
		//forbid the action
		if(move_action_ineffective && game_score != 1){
			actions_forbidden.add(action);
		}
		
		//if we win, pick this action and put all the actions which bring the 
		//player to the end on a stack
		if(actual_player_win){
			this.winningpath = true;
			return Double.POSITIVE_INFINITY;
		} else if (actual_player_los) {
            return Double.NEGATIVE_INFINITY;
        }
		
		//if score raised, save the path
		if(game_score == 1){
			this.winningpath = true;
			return Double.POSITIVE_INFINITY;
		}
		
		//if this is the first step (father is the root) some special
		//score rules computed here
		if(first_step){
			//if the first action was use and the score does not raise, it is a verv bad idea to
			//follow this Tree, kill the tree, by adding all actions to the forbidden list
			if((action == Types.ACTIONS.ACTION_USE || move_action_ineffective) && game_score != 1){
				actions_forbidden.addAll(stateObs.getAvailableActions());
			}
		}
		
		//todo: use the positions (which can be stored in a variable in the agent class
		//the act method creates it and destroys it) to look if the player was on
		//this field before -> bad score...
		
		//copy of the portalHeuristic, go a portal -> it is good
		//TODO
		
		//if we kill an NPC, add Points to the score
		//TODO
		
		return score;
	}
	
	public ArrayList<Types.ACTIONS> getForbiddenActions(){
		return this.actions_forbidden;
	}
	
	public boolean foundWinPath(){
		return this.winningpath;
	}
}
