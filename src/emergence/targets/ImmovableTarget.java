package emergence.targets;

import core.game.StateObservation;
import tools.Vector2d;

/**
 * This class represents a immovable target, it has a fixed position (in
 * contrast to DynamicTarget).
 *
 */
public class ImmovableTarget extends ATarget {

	/** position of this target */
	Vector2d targetPos;

	/**
	 * Creates a immovable target by calling the super constructor and the
	 * specifying the position.
	 * 
	 * @param type
	 * @param itype
	 * @param targetPos
	 */
	public ImmovableTarget(TYPE type, Integer itype, Vector2d targetPos) {
		super(type, itype);
		this.targetPos = targetPos;
	}

	/**
	 * Returns the position.
	 */
	@Override
	public Vector2d getPosition(StateObservation stateObs) {
		return targetPos;
	}

	/**
	 * Returns a String containing type, itype and position.
	 */
	@Override
	public String toString() {
		return String.format("(type:%s, itype:%s, pos:[%s,%s])",
				type.toString(), itype, targetPos.x, targetPos.y);
	}

}
