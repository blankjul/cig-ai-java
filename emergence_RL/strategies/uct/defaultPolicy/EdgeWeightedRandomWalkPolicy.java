package emergence_RL.strategies.uct.defaultPolicy;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

import ontology.Types;
import core.game.StateObservation;
import emergence_RL.strategies.uct.UCTSettings;
import emergence_RL.tree.Node;

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

	// available Actions
	TreeSet<Types.ACTIONS> actions = new TreeSet<Types.ACTIONS>();

	// the list of Actions which will be executed
	ArrayList<Types.ACTIONS> execActions = new ArrayList<Types.ACTIONS>();

	// array to store the number of Actions
	private int[] values = new int[5];

	// this Values can be modified:

	// true-> take the action from the root ; false-> take the action from
	// the expanded Node
	private boolean takeRootAction = true;

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

	public EdgeWeightedRandomWalkPolicy() {

	}

	@Override
	public double expand(UCTSettings s, Node n) {

		setDependingAction(n);

		actions.addAll(n.stateObs.getAvailableActions());

		setArray();

		generateExecActions(n);

		StateObservation currentStateObs = n.stateObs.copy();
		Types.ACTIONS currentAction = null;

		int level = n.level;

		while (!currentStateObs.isGameOver() && level <= s.maxDepth) {

			currentAction = getNextAction(s.r);
			currentStateObs.advance(currentAction);
			++level;
		}

		if (currentStateObs.isGameOver()) {
			Types.WINNER winner = currentStateObs.getGameWinner();
			if (winner == Types.WINNER.PLAYER_WINS)
				return 1000;
			else if (winner == Types.WINNER.PLAYER_LOSES)
				return -1000;
		}

		double delta = currentStateObs.getGameScore() - n.stateObs.getGameScore();
		
		//clear temporary values
		dependingAction = null;
		actions.clear();
		execActions.clear();
		
		return delta;
	}

	/**
	 * set the depending action which was selected in the attributes of this class
	 * @param n
	 */
	public void setDependingAction(Node n) {
		if (takeRootAction) {
			while (n.father.father != null) {
				n = n.father;
			}
		}
		dependingAction = n.lastAction;
	}

	/**
	 * get the next action to execute
	 * @param r
	 * @return
	 */
	public Types.ACTIONS getNextAction(Random r) {
		int random = r.nextInt(execActions.size());
		return execActions.get(random);
	}

	/**
	 * generates the execution List, depends on actions which are available
	 * and dependingAction
	 * @param n
	 */
	public void generateExecActions(Node n) {

		//while actual action is use, select the next one (bottom up to the root)
		while ((dependingAction == Types.ACTIONS.ACTION_USE || dependingAction == Types.ACTIONS.ACTION_NIL)
				&& n.father.father != null) {
			n = n.father;
			dependingAction = n.lastAction;
		}
		
		Types.ACTIONS actualAction = dependingAction;
		
		//if the action isn't use, set the values
		if (!(actualAction == Types.ACTIONS.ACTION_USE) && actualAction != null) {

			for (int e = 0; e < 4; e++) {
				if (actions.contains(actualAction)) {
					int anz_actions = values[e];
					for (int i = 0; i < anz_actions; i++) {
						execActions.add(actualAction);
					}
				}
				actualAction = nextAction(actualAction);
			}

			if (actions.contains(Types.ACTIONS.ACTION_USE)) {
				int anz_actions = values[4];
				for (int i = 0; i < anz_actions; i++) {
					execActions.add(Types.ACTIONS.ACTION_USE);
				}
			}
		}else{//the action was use -> just random
			execActions.addAll(actions);
		}
		
	}

	/**
	 * returns the next Action (in a circle up->right, right->down and so on...
	 * @param action
	 * @return
	 */
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

	/**
	 * sets the values selected by the user 
	 */
	public void setArray() {
		values[0] = actionSame;
		values[1] = action90;
		values[2] = action180;
		values[3] = action90_;
		values[4] = actionUse;
	}

}
