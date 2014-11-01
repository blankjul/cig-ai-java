package emergence_HR;

import java.util.ArrayList;
import java.util.LinkedList;

import ontology.Types;
import ontology.Types.ACTIONS;
import tools.Vector2d;
import core.game.StateObservation;

/**
 * this class is an extension to the class TreeNode
 * it contains some methods and attributes which are
 * needed by AgentRandom and the LookBackHeuristic
 * @author spakken
 *
 */
public class TreeNodeRandom extends TreeNode{
	
	//this value is used to compare the actual stateObs with
	//the stateObs of the father. 
	//which action you used to get to the actual Node
	protected Types.ACTIONS action;
	
	//store the positionsof the avatar in this path
	ArrayList<Vector2d> positions;
	
	public TreeNodeRandom(StateObservation stateObs) {
		super(stateObs);
		this.action = null;
		positions.add(stateObs.getAvatarPosition());
		// TODO Auto-generated constructor stub
	}
	
	public TreeNodeRandom(StateObservation stateObs, TreeNodeRandom father, Types.ACTIONS action) {
		this(stateObs);
		this.father = father;
		this.action = action;
		
	}

	/**
	 * this is a overloaded function to compute a defined number of
	 * randomly chosen children
	 * @param children
	 * @return
	 */
	public LinkedList<TreeNodeRandom> getChildren(int children){
		// create result list and reserve memory for the temporary state object
		LinkedList<TreeNodeRandom> nodes = new LinkedList<TreeNodeRandom>();
		StateObservation tmpStateObs;
		int available_actions = stateObs.getAvailableActions().size();
		//debug
		//System.out.println("available Actions: " + stateObs.getAvailableActions().toString() + "numberOFACTIONS: " + available_actions);
		int[] index_list= new int[children];
		
		//not so many children available
		children = (children > available_actions) ? available_actions : children;
		//String string = "";
		//generate random indexes for the actions
		for(int i = 0; i < children; i++){
			index_list[i] = (int) (Math.random() * available_actions);
			//string += index_list[i];
		}
		//System.out.println("index List" + string);
		//generate children nodes
		for(int i = 0; i < children; i++){
			ACTIONS action = stateObs.getAvailableActions().get(index_list[i]);
			tmpStateObs = stateObs.copy();
			tmpStateObs.advance(action);
			System.out.println("position: " + stateObs.getAvatarPosition().toString());
			TreeNodeRandom n = new TreeNodeRandom(tmpStateObs, this, action);
			// set the correct action from the root. if it's the root set action
			// else just inheritate
			n.rootAction = (this.father == null) ? action : this.rootAction;
			nodes.add(n);
		}
		tmpStateObs = null;
		return nodes;	
	}

	/**
	 * method to get the father of the actual Node
	 * @return
	 */
	public TreeNode getFather(){
		return this.father;
	}
	
	/**
	 * method to get the action which brings the Avatar to
	 * this node
	 * @return
	 */
	public Types.ACTIONS getAction(){
		return this.action;
	}
	
}
