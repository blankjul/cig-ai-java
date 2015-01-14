package emergence.safety;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ontology.Types.ACTIONS;
import core.game.Observation;
import core.game.StateObservation;
import emergence.Environment;
import emergence.Factory;
import emergence.util.ObservationUtil;

/**
 * A Safety which checks if a action is safe by analyzing the neighbourhood of
 * the avatar.
 * 
 * @author spakken
 *
 */
public class SafetyGridSearch extends ASafety {

	/**
	 * Returns true if the action is safe, false otherwise. A grid is build with
	 * all sprites which sourround the avatar. When the grid contains dangerous
	 * sprites it returns false.
	 */
	public boolean isSafe(StateObservation stateObs, ACTIONS a) {
		Set<Integer> sprites = new HashSet<>();

		// get the position of we do this move
		Point index = ObservationUtil.avatarPosToGridIndex(stateObs);
		Point newPositionIndex = ObservationUtil.getMoveIndex(index, a);

		// check all fields around for observations
		for (ACTIONS m : stateObs.getAvailableActions()) {
			Point moveIndex = ObservationUtil.getMoveIndex(newPositionIndex, m);
			ArrayList<Observation> obsList = ObservationUtil.getObservations(
					stateObs, moveIndex);
			for (Observation obs : obsList) {
				sprites.add(obs.itype);
			}
		}
		Environment env = Factory.getEnvironment();

		// now all the sprites around that move are collected
		// remove all sprites that are not dangerous
		sprites.removeAll(env.getBlockingSprites());
		sprites.removeAll(env.getScoreSprites());
		sprites.removeAll(env.getWinSprites());
		sprites.remove(env.getAvatarType());

		return sprites.isEmpty();
	}

	/**
	 * Generate a String object which is used in csv files.
	 */
	@Override
	public String toCSVString() {
		return "SafetyGridSearch,1";
	}

}
