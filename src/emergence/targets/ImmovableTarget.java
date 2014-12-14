package emergence.targets;

import tools.Vector2d;

public class ImmovableTarget extends ATarget{

	Vector2d targetPos;
	
	public ImmovableTarget(TYPE type, Integer itype, Vector2d targetPos) {
		super(type, itype);
		this.targetPos = targetPos;
	}
	
	@Override
	public Vector2d position() {
		return targetPos;
	}



}
