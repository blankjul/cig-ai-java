package emergence_HR.heuristics;

import java.util.ArrayList;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;
import emergence_HR.heuristics.Target.Type;

public class TargetFactory {

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
	 * Returns all the NPCs as a target object
	 */
	public static ArrayList<Target> getNPC(StateObservation stateObs) {
		Vector2d avatarPosition = stateObs.getAvatarPosition();
		ArrayList<Target> targetList = new ArrayList<Target>();
		// add all npcs
		ArrayList<Observation>[] npcPositions = stateObs
				.getNPCPositions(avatarPosition);
		if (npcPositions == null) return targetList;
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
		if (resourcesPositions == null) return targetList;
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
		if (portalPositions == null) return targetList;
		for (int i = 0; i < portalPositions.length; i++) {
			targetList.add(new Target(Type.Portals, i));
		}
		return targetList;
	}

}
