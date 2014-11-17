package emergence_RL.uct.defaultPoliciy;

import java.util.Random;
import java.util.TreeSet;

import ontology.Types;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

/**
 * this class weights the possible action you pick. To weight the action
 * we need a dependingAction (this could be the action from the
 *  Node which was Expanded at the end of the TreePolicy, or the first
 *  action from the root node, which leeds to the expanded node, ect..) 
 * @author spakken
 *
 */
public class EdgeWeightedRandomWalkPolicy extends ADefaultPolicy{

	//action wich is used to weight the possible Actions
	private Types.ACTIONS dependingAction = null;
	
	TreeSet<Types.ACTIONS> actions = new TreeSet<Types.ACTIONS>();
	
	private int numberOfActions;
	//this are the probabilities of select an action, must sum up to one
	
	//the same, the dependingAction
	private double actionSame = 0.4;
	
	//the opposite Action Left -> Right; Right ->Left
	private double action180 = 0.1;
	
	//action on the right side UP -> Right
	private double action90 = 0.15;
	
	//actiom on the left sid UP ->Left
	private double action90_ = 0.15;
	
	//action use is always 1/nmberOfActions
	private double actionUse = 0;
	
	//Actions which willbeexecuted
	//[0] -> actionSame
	//[1] -> action180
	//[2] -> action90
	//[3] -> action90_
	//[4] -> actionuse
	private Types.ACTIONS[] execActions = new Types.ACTIONS[5];
	
	
	private double[] actionValues;
	
	public EdgeWeightedRandomWalkPolicy() {
		
	}
	
	@Override
	public double expand(UCTSettings s, Node n) {
		double numberOfActions = (double) n.children.length;
		actionUse = 1/numberOfActions;
		
		actions.addAll(n.stateObs.getAvailableActions());
		
		
		
		if(numberOfActions != 5){
			configureActionValues();
		}
		
		return 0;
	}
	
	public Types.ACTIONS getNextAction(Random r, Node n){
		
		
		
		return null;
	}
	
	public void configureActionValues(){
		double sum = 0;
		
		if(!actions.contains(Types.ACTIONS.ACTION_UP)){
			
		}
	}
	
	public void generateExecActions(){
		if(dependingAction == Types.ACTIONS.ACTION_UP){
			execActions[0] = Types.ACTIONS.ACTION_UP;
		}
	}
	
	public void generateActionValues(){
		
	}

}
