package emergence_HR.target;

import core.game.StateObservation;

/**
 * This class represents always a target at the level. First of all you have to
 * specify the type and after that the index. index means typesId of the object.
 * 
 */
abstract public class ATarget {

	/**
	 * 
	 * @return distance from avatar to target
	 */
	abstract public double distance(StateObservation stateObs);

	
	
	/**
	 * Print all needed information to specify a target.
	 */
	public String toString() {
		return String.format("type:%s", this.getClass().getSimpleName());
	}


}
