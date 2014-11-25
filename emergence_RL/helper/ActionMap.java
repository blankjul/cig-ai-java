package emergence_RL.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ontology.Types;

/**
 * The ActionMap is useful to get very fast the integer from a action or the
 * other way a round. it is a singleton class because normally that does not
 * change during one game!
 */
public class ActionMap {

	public int NUM_ACTIONS;
	

	// maps an action to an integer
	private Map<Types.ACTIONS, Integer> act_int_map;

	// maps and integer to an action by using simply an array
	private Types.ACTIONS[] int_act_map;


	
	public ActionMap(ArrayList<Types.ACTIONS> act) {
		NUM_ACTIONS = act.size();
		act_int_map = new HashMap<Types.ACTIONS, Integer>();
		int_act_map = new Types.ACTIONS[NUM_ACTIONS];
		for (int i = 0; i < act.size(); ++i) {
			act_int_map.put(act.get(i), i);
			int_act_map[i] = act.get(i);
		}
	}
	

	/**
	 * Converts the action to an integer by using the internal hash map!
	 * 
	 * @param a
	 *            action that should be transformed
	 * @return integer of that action
	 */
	public int getInt(Types.ACTIONS a) {
		return act_int_map.get(a);
	}

	/**
	 * Convert the integer to the corresponding action.
	 * 
	 * @param i
	 *            integer of the available action list.
	 * @return corresponding action.
	 */
	public Types.ACTIONS getAction(int i) {
		return int_act_map[i];
	}

}
