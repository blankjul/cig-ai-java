package emergence.targets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;
import emergence.targets.ATarget.TYPE;
import emergence.util.pair.Pair;

public class TargetFactory {

	/**
	 * This is just a factory method that returns all the possible targets at
	 * the given state.
	 * 
	 * @param stateObs
	 *            current state
	 * @return list with all possible targets.
	 */
	public static Set<ATarget> getAllTargets(StateObservation stateObs) {
		Set<ATarget> result = new HashSet<>();

		TYPE[] targetTypes = new TYPE[] { TYPE.Portal, TYPE.Resource, TYPE.NPC};

		for (TYPE t : targetTypes) {
			ArrayList<Observation>[] obsListArray = getObservations(stateObs, t);
			Set<ATarget> targets = getTargets(stateObs, t, obsListArray);
			result.addAll(targets);
		}

		return result;
	}

	public static Pair<TYPE, Observation> getObservationFromType(int itype, StateObservation stateObs) {
		for (TYPE t : TYPE.values()) {
			ArrayList<Observation>[] obsListArray = getObservations(stateObs, t);
			if (obsListArray == null)
				continue;
			for (ArrayList<Observation> listObs : obsListArray) {
				for (Observation obs : listObs) {
					// if there is a target with that given type
					if (obs.itype == itype) {
						return new Pair<TYPE, Observation>(t, obs);
					}
				}
			}
		}
		return null;
	}

	/*
	 * if (typeOfTarget.equals(TYPE.Portal) ||
	 * typeOfTarget.equals(TYPE.Immovable) ||
	 * typeOfTarget.equals(TYPE.Resource)){ ATarget target = new
	 * ImmovableTarget(t, obs.itype, obs.position); return target; } else if
	 * (typeOfTarget.equals(TYPE.NPC) || typeOfTarget.equals(TYPE.Movable)) {
	 * ATarget target = new MovableTarget(t, obs.itype); return target; }
	 */

	private static ArrayList<Observation>[] getObservations(StateObservation stateObs, TYPE type) {
		ArrayList<Observation>[] result = null;
		Vector2d avatarPosition = stateObs.getAvatarPosition();
		if (type.equals(TYPE.NPC)) {
			result = stateObs.getNPCPositions(avatarPosition);
		} else if (type.equals(TYPE.Portal)) {
			result = stateObs.getPortalsPositions(avatarPosition);
		} else if (type.equals(TYPE.Resource)) {
			result = stateObs.getResourcesPositions(avatarPosition);
		} else if (type.equals(TYPE.Movable)) {
			result = stateObs.getMovablePositions(avatarPosition);
		} else if (type.equals(TYPE.Immovable)) {
			result = stateObs.getImmovablePositions(avatarPosition);
		}
		return result;
	}

	private static Set<ATarget> getTargets(StateObservation stateObs, TYPE type, ArrayList<Observation>[] obsListArray) {

		Set<ATarget> result = new HashSet<>();

		if (obsListArray == null)
			return result;

		for (ArrayList<Observation> listObs : obsListArray) {
			for (Observation obs : listObs) {
				if (isImmovable(type)) {
					result.add(new ImmovableTarget(type, obs.itype, obs.position));
				} else {
					result.add(new MovableTarget(type, obs.itype, stateObs));
				}
			}
		}

		return result;
	}

	
	public static boolean isImmovable(TYPE type) {
		if (type.equals(TYPE.Portal) || type.equals(TYPE.Immovable) || type.equals(TYPE.Resource)) {
			return true;
		}
		return false;
	}
	
}
