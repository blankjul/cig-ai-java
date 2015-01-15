package emergence.targets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;
import emergence.targets.ATarget.TYPE;

/**
 * This class is used to search specified targets and to get general information
 * about the environment of the avatar.
 *
 */
public class TargetFactory {

	/**
	 * Searched at the level for the target that has the type and itype. if type
	 * is null the observation is searched over all possible types.
	 * 
	 * @param type
	 *            TYPE of the target (portal, npc, ...). null of unknown.
	 * @param itype
	 *            type id of the target integer (0...n)
	 * @param stateObs
	 *            current observation
	 * @return null if no target exists. else the observation nearest to the
	 *         agent.
	 */
	public static Observation getObservationFromType(TYPE t, int itype,
			StateObservation stateObs) {
		Set<TYPE> typeSet = new HashSet<>();
		if (t == null)
			typeSet.addAll(Arrays.asList(TYPE.values()));
		else
			typeSet.add(t);

		for (TYPE type : typeSet) {
			ArrayList<Observation>[] obsListArray = getObservations(stateObs,
					type);
			if (obsListArray == null)
				return null;
			for (ArrayList<Observation> listObs : obsListArray) {
				// if there is a target with that given type
				if (!listObs.isEmpty()) {
					if (listObs.get(0).itype == itype) {
						return listObs.get(0);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Searches the observation over all possible types.
	 * 
	 * @param itype
	 * @param stateObs
	 * @return
	 */
	public static Observation getObservationFromType(int itype,
			StateObservation stateObs) {
		return getObservationFromType(null, itype, stateObs);

	}

	/**
	 * Returns all observations at the grid the are of an specific type.
	 * 
	 * @param stateObs
	 *            current state observation
	 * @param type
	 *            that should be returned.
	 * @return
	 */
	public static ArrayList<Observation>[] getObservations(
			StateObservation stateObs, TYPE type) {
		ArrayList<Observation>[] result = null;
		Vector2d agentPos = stateObs.getAvatarPosition();
		if (type.equals(TYPE.NPC)) {
			result = stateObs.getNPCPositions(agentPos);
		} else if (type.equals(TYPE.Portal)) {
			result = stateObs.getPortalsPositions(agentPos);
		} else if (type.equals(TYPE.Resource)) {
			result = stateObs.getResourcesPositions(agentPos);
		} else if (type.equals(TYPE.Movable)) {
			result = stateObs.getMovablePositions(agentPos);
		} else if (type.equals(TYPE.Immovable)) {
			result = stateObs.getImmovablePositions(agentPos);
		}
		return result;
	}

	/**
	 * Search for all type ids of a specific target type.
	 * 
	 * @return set with all type id of TYPE type.
	 */
	public static Set<Integer> getAllTypeIds(StateObservation stateObs,
			TYPE type) {
		Set<Integer> result = new HashSet<Integer>();
		ArrayList<Observation>[] obsListArray = getObservations(stateObs, type);
		if (obsListArray == null)
			return result;
		for (ArrayList<Observation> listObs : obsListArray) {
			if (!listObs.isEmpty())
				result.add(listObs.get(0).itype);
		}
		return result;
	}

	/**
	 * Returns all targets at the whole level.
	 * 
	 * @param stateObs
	 * @return a set with all the targets.
	 */
	public static Set<ATarget> getAllTargets(StateObservation stateObs) {
		Set<ATarget> result = new HashSet<>();
		for (TYPE type : TYPE.values()) {
			Set<Integer> typeSet = getAllTypeIds(stateObs, type);
			for (Integer itype : typeSet) {
				Observation obs = getObservationFromType(type, itype, stateObs);
				if (ATarget.isImmovable(type))
					result.add(new ImmovableTarget(type, itype, obs.position));
				else
					result.add(new DynamicTarget(type, itype));
			}

		}
		return result;
	}

	/**
	 * Returns the general TYPE when a itype is given.
	 */
	public static TYPE getType(StateObservation stateObs, int itype) {
		for (TYPE type : TYPE.values()) {
			ArrayList<Observation>[] obsListArray = getObservations(stateObs,
					type);
			if (obsListArray == null)
				continue;
			for (ArrayList<Observation> listObs : obsListArray) {
				if (!listObs.isEmpty()) {
					if (listObs.get(0).itype == itype) {
						return type;
					}
				}
			}
		}
		return null;
	}

}
