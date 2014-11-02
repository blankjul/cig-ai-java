package emergence_HR.heuristics;

import java.util.Stack;

import ontology.Types;
import emergence_HR.TreeNodeRandom;

/**
 * 
 * @author spakken
 *
 */
public class WinningPath {
	
	//this stack contains the Actions which have tob be executed to win
	//the game
	private Stack<Types.ACTIONS> winning_path = new Stack<Types.ACTIONS>();
	
	public WinningPath(){
		
	}
	
	public void createWinningPath(TreeNodeRandom node){
		//push all Actions on the Stack, bottom-up
		TreeNodeRandom actual_node = node;
		while(actual_node.getFather() != null){
			winning_path.push(actual_node.getAction());
			actual_node = actual_node.getFather();
		}
	}
	
	public Types.ACTIONS getNextAction(){
		return this.winning_path.pop();
	}
	
	public boolean isEmpty(){
		return this.winning_path.isEmpty();
	}
}
