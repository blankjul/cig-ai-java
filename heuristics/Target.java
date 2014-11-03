package emergence_HR.heuristics;

import java.util.ArrayList;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;

/**
 * This class represents always a target at the level. First of all you have to
 * specify the type and after that the index. index means typesId of the object.
 * 
 * @author julesy
 * 
 */
public class Target {

	/**
	 * Type of the target.
	 */
	public enum Type {
		NPC, Portals, Resources
	}

	// type that we are searching for
	private Type type;

	// index that means typeid
	private int index;

	/**
	 * Define an object that should be a target of same action.
	 * 
	 * @param t
	 *            type that you want to get near to.
	 * @param i
	 *            typeid of the target.
	 */
	public Target(Type t, int i) {
		this.type = t;
		this.index = i;
	}

	/**
	 * Searches in the level for the correct type of target and then look for
	 * the type id.
	 * 
	 * @return null if no enemy of this type or this index is found. else a
	 *         position Vector2d where we want to go!
	 */
	public Vector2d getPosition(StateObservation stateObs) {
		Vector2d avatarPosition = stateObs.getAvatarPosition();
		Vector2d v = null;

		if (type == Type.NPC) {
			ArrayList<Observation>[] npcPositions = stateObs
					.getNPCPositions(avatarPosition);
			if (npcPositions.length > index && !npcPositions[index].isEmpty())
				v = npcPositions[index].get(0).position;

		} else if (type == Type.Portals) {
			ArrayList<Observation>[] portalPositions = stateObs
					.getPortalsPositions(avatarPosition);
			if (portalPositions.length > 0 && !portalPositions[index].isEmpty())
				v = portalPositions[index].get(0).position;

		} else if (type == Type.Resources) {
			ArrayList<Observation>[] resourcesPositions = stateObs
					.getResourcesPositions(avatarPosition);
			if (resourcesPositions.length > 0
					&& !resourcesPositions[index].isEmpty())
				v = resourcesPositions[index].get(0).position;
		}
		return v;
	}

	/**
	 * This is just a factory method that returns all the possible targets at
	 * the given state.
	 * 
	 * @param stateObs
	 *            current state
	 * @return list with all possible targets.
	 */
	public static ArrayList<Target> getAllTargets(StateObservation stateObs) {

		ArrayList<Target> targetList = new ArrayList<Target>();
		targetList.addAll(getPortals(stateObs));
		targetList.addAll(getNPC(stateObs));
		targetList.addAll(getResources(stateObs));
		return targetList;
	}

	
	/**
	 * Returns all the nps as a target object
	 */
	public static ArrayList<Target> getNPC(StateObservation stateObs) {
		Vector2d avatarPosition = stateObs.getAvatarPosition();
		ArrayList<Target> targetList = new ArrayList<Target>();
		// add all npcs
		ArrayList<Observation>[] npcPositions = stateObs
				.getNPCPositions(avatarPosition);
		for (int i = 0; i < npcPositions.length; i++) {
			targetList.add(new Target(Type.NPC, i));
		}
		return targetList;
	}
	
	
	/**
	 * Returns all the resources as a target object
	 */
	public static ArrayList<Target> getResources(StateObservation stateObs) {
		Vector2d avatarPosition = stateObs.getAvatarPosition();
		ArrayList<Target> targetList = new ArrayList<Target>();
		// add all resources
		ArrayList<Observation>[] resourcesPositions = stateObs
				.getPortalsPositions(avatarPosition);
		for (int i = 0; i < resourcesPositions.length; i++) {
			targetList.add(new Target(Type.Resources, i));
		}
		return targetList;
	}
	


	/**
	 * Returns all the portals as a target object
	 */
	public static ArrayList<Target> getPortals(StateObservation stateObs) {
		Vector2d avatarPosition = stateObs.getAvatarPosition();
		ArrayList<Target> targetList = new ArrayList<Target>();
		// add all portals
		ArrayList<Observation>[] portalPositions = stateObs
				.getPortalsPositions(avatarPosition);
		for (int i = 0; i < portalPositions.length; i++) {
			targetList.add(new Target(Type.Portals, i));
		}
		return targetList;
	}

	/**
	 * Print all needed information to specify a target.
	 */
	public String toString() {
		return String.format("type:%s, id:%s", type.getClass().getSimpleName(),
				index);
	}



}
