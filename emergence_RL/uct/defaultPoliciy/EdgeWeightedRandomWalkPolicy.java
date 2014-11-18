package emergence_RL.uct.defaultPoliciy;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

import core.game.StateObservation;
import ontology.Types;
import emergence_RL.helper.Helper;
import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

/**
 * this class weights the possible action you pick. To weight the action we need
 * a dependingAction (this could be the action from the Node which was Expanded
 * at the end of the TreePolicy, or the first action from the root node, which
 * leeds to the expanded node, ect..) the expansion will go in the direction
 * from the depending Action
 * 
 * @author spakken
 *
 */
public class EdgeWeightedRandomWalkPolicy extends ADefaultPolicy {

	// action wich is used to weight the possible Actions
	private Types.ACTIONS dependingAction;

	//available Actions
	TreeSet<Types.ACTIONS> actions = new TreeSet<Types.ACTIONS>();

	//the list of Actions which will be executed
	ArrayList<Types.ACTIONS> execActions = new ArrayList<Types.ACTIONS>();


	private int[] values = new int[5];
	
	// the same, the dependingAction
	private int actionSame = 4;

	// the opposite Action Left -> Right; Right ->Left
	private int action180 = 1;

	// action on the right side UP -> Right
	private int action90 = 2;

	// actiom on the left sid UP ->Left
	private int action90_ = 2;

	// action use is always 1/nmberOfActions
	private int actionUse = 2;

	// Actions which willbeexecuted
	// [0] -> actionSame
	// [1] -> action90
	// [2] -> action180
	// [3] -> action90_
	// [4] -> actionuse
	// private Types.ACTIONS[] execActions = new Types.ACTIONS[5];

	public EdgeWeightedRandomWalkPolicy() {

	}

	@Override
	public double expand(UCTSettings s, Node n) {

		dependingAction = n.lastAction;
		
		actions.addAll(n.stateObs.getAvailableActions());
		
		setArray();
		
		generateExecActions();
		
		StateObservation currentStateObs = n.stateObs.copy();
		Types.ACTIONS currentAction = null;

		int level = n.level;
		double delta = 0;

		while (!currentStateObs.isGameOver() && delta == 0
				&& level <= s.maxDepth) {
			
			currentAction = getNextAction(s.r);
			currentStateObs.advance(currentAction);
			delta = currentStateObs.getGameScore() - n.stateObs.getGameScore();
			++level;
		}
		
		
		if (currentStateObs.isGameOver()) {
			Types.WINNER winner = currentStateObs.getGameWinner();
			if (winner == Types.WINNER.PLAYER_WINS)
				return 1000;
			else if (winner == Types.WINNER.PLAYER_LOSES)
				return -1000;
		}

		if (delta > 0)
			delta = 1;
		else if (delta < 0)
			delta = -1;

		return delta;
	}

	public Types.ACTIONS getNextAction(Random r) {
		int random = r.nextInt(execActions.size());
		return execActions.get(random);
	}

	public void generateExecActions() {

		Types.ACTIONS actualAction = dependingAction;
		
		for(int e = 0; e < 4; e++){
			if(actions.contains(actualAction)){
				int anz_actions = values[e];
				for(int i = 0; i < anz_actions; i++){
					execActions.add(actualAction);
				}
			}
			actualAction = nextAction(actualAction);
		}
		
		if(actions.contains(Types.ACTIONS.ACTION_USE)){
			int anz_actions = values[4];
			for(int i = 0; i < anz_actions; i++){
				execActions.add(actualAction);
			}
		}
	}

	public Types.ACTIONS nextAction(Types.ACTIONS action) {
		if (action == Types.ACTIONS.ACTION_UP) {
			return Types.ACTIONS.ACTION_RIGHT;
		} else if (action == Types.ACTIONS.ACTION_RIGHT) {
			return Types.ACTIONS.ACTION_DOWN;
		} else if (action == Types.ACTIONS.ACTION_DOWN) {
			return Types.ACTIONS.ACTION_LEFT;
		} else if (action == Types.ACTIONS.ACTION_LEFT) {
			return Types.ACTIONS.ACTION_UP;
		}
		System.out.println("nextActionFehler");
		return null;
	}
	
	public void setArray(){
		values[0] = actionSame;
		values[1] = action90;
		values[2] = action180;
		values[3] = action90_;
		values[4] = actionUse;
	}

}
