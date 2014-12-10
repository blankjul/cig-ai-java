package emergence.util;

import ontology.Types.ACTIONS;
import tools.Vector2d;
import core.game.StateObservation;

public class Helper {
	
	public static String hash(StateObservation stateObs) {
		Vector2d pos = stateObs.getAvatarPosition();
		return String.format("[%s,%s]", pos.x, pos.y);
	}
	
	
	public static double distance(Vector2d from, Vector2d to) {
		return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
	}
	
	public static ACTIONS getOppositeAction(ACTIONS a) {
		if (a == ACTIONS.ACTION_DOWN) {
			return ACTIONS.ACTION_UP;
		} else if (a == ACTIONS.ACTION_LEFT) {
			return ACTIONS.ACTION_RIGHT;
		} else if (a == ACTIONS.ACTION_UP) {
			return ACTIONS.ACTION_DOWN;
		} else if (a == ACTIONS.ACTION_RIGHT) {
			return ACTIONS.ACTION_LEFT;
		} else {
			return ACTIONS.ACTION_NIL;
		}
		
	}
	

}
