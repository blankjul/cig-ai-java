package emergence.targets;

import tools.Vector2d;

/**
 * This class represents always a target at the level. First of all you have to
 * specify the type and after that the index. index means typesId of the object.
 * 
 */
abstract public class ATarget {

	public enum TYPE {
		Portal, Resource, NPC, Immovable, Movable
	};
	
	protected TYPE type;
	
	protected Integer itype;
	
	public ATarget(TYPE type, Integer itype) {
		this.type = type;
		this.itype = itype;
	}
	

	/**
	 * @return distance from avatar to target
	 */
	abstract public Vector2d position();
	
	/**
	 * Print all needed information to specify a target.
	 */
	public String toString() {
		Vector2d v = position();
		return String.format("(type:%s, pos:[%s,%s])",type.toString(), v.x, v.y);
	}

	public TYPE getType() {
		return type;
	}

	public Integer getItype() {
		return itype;
	}


}
