package emergence_RL.uct.defaultPoliciy;

import ontology.Types;
import tools.Vector2d;
import core.game.StateObservation;
import emergence_RL.tree.Node;

/**
 * stores only stateObs and lastAction, used by DefaultPolicies
 * no need for a normal node with father ActionMap ect.
 * @author spakken
 *
 */
public class SimNode {
	
	public StateObservation stateObs;
	
	public Types.ACTIONS lastAction;
	
	public SimNode(StateObservation stateObs, Types.ACTIONS lastAction){
		this.stateObs =  stateObs;
		this.lastAction = lastAction;
	}
	
	public String hash() {
		Vector2d pos = stateObs.getAvatarPosition();
		String used = (lastAction == null || lastAction != Types.ACTIONS.ACTION_USE) ? "n"
				: "y";
		return String.format("[%s,%s,%s]", pos.x, pos.y, used);
	}
	
	//generate the hash value without instanciate the Node, used in SelfAvoidingPathPlicy
	public static String hash(StateObservation stateObs_, Types.ACTIONS action) {
		Vector2d pos = stateObs_.getAvatarPosition();
		String used = (action == null || action != Types.ACTIONS.ACTION_USE) ? "n"
				: "y";
		return String.format("[%s,%s,%s]", pos.x, pos.y, used);
	}
}
