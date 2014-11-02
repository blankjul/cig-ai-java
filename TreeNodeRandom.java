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
	
	//this is the TreeNodeRandom father, when you work with this class
	//be sure to use this father node
	protected TreeNodeRandom fatherTNR;
	
	//store the positionsof the avatar in this path
	ArrayList<Vector2d> positions = new ArrayList<Vector2d>();
	
	public TreeNodeRandom(StateObservation stateObs) {
		super(stateObs);
		this.action = null;
		this.fatherTNR = null;
		this.positions.add(stateObs.getAvatarPosition());
	}
	
	public TreeNodeRandom(StateObservation stateObs, TreeNodeRandom father, Types.ACTIONS action) {
		this(stateObs);
		this.fatherTNR = father;
		this.action = action;
		
	}

	/**
	 * this is a overloaded function to compute a defined number of
	 * randomly chosen children, the forbidden Actions are cant
	 * be picked
	 * @param children
	 * @return
	 */
	public LinkedList<TreeNodeRandom> getChildren(int children, ArrayList<Types.ACTIONS> forbidden_actions){
		// create result list and reserve memory for the temporary state object
		LinkedList<TreeNodeRandom> nodes = new LinkedList<TreeNodeRandom>();
		StateObservation tmpStateObs;
		ArrayList<Types.ACTIONS> actions = stateObs.getAvailableActions();		
		//for debugging only
		
		//debug
		//System.out.println("available Actions: " + stateObs.getAvailableActions().toString() + "numberOFACTIONS: " + available_actions);
		//System.out.println("1 name: " + stateObs.getAvailableActions().get(0).name() + "   key: " + stateObs.getAvailableActions().get(0).getKey().toString());
		//System.out.println("2 name: " + stateObs.getAvailableActions().get(1).name() + "   key: " + stateObs.getAvailableActions().get(1).getKey().toString());
		
		//delete the forbidden indexes
		if(forbidden_actions != null){
			actions.removeAll(forbidden_actions);
		}
		
		//generate the number of available actions except the forbidden ones
		int available_actions = actions.size();
		
		//not so many children available
		children = (children > available_actions) ? available_actions : children;
		
		//just generate the defined number of children
		while(children < actions.size()){
			int delete = (int) (Math.random() * available_actions);
			actions.remove(delete);
			available_actions--;
		}
		
		//generate children nodes
		for(Types.ACTIONS action : actions){

			tmpStateObs = stateObs.copy();
			tmpStateObs.advance(action);
			//System.out.println("position: " + stateObs.getAvatarPosition().toString());
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
	public TreeNodeRandom getFather(){
		return this.fatherTNR;
	}
	
	/**
	 * method to get the action which brings the Avatar to
	 * this node
	 * @return
	 */
	public Types.ACTIONS getAction(){
		return this.action;
	}
	
	/**
	 * this method casts a LinkedList with TreeNode's in it to a list
	 * with TreeNodeRandom's in it
	 * @param treeNode
	 * @return
	 */
	public static LinkedList<TreeNodeRandom> castToSubclass(LinkedList<TreeNode> treeNode){
		LinkedList<TreeNodeRandom> treeNodeRandom = null;
		for(TreeNode node: treeNode){
			treeNodeRandom.add((TreeNodeRandom) node);
		}
		return treeNodeRandom;
	}
}
