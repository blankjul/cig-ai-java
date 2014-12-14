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

public class SafetyGridSearch extends ASafety{
	

	// index where we are now.
	public boolean isSafe(StateObservation stateObs, ACTIONS a) {
		Set<Integer> sprites = new HashSet<>();

		// get the position of we do this move
		Point index = ObservationUtil.avatarPosToGridIndex(stateObs);
		Point newPositionIndex = ObservationUtil.getMoveIndex(index, a);

		// check all fields around for observations
		for (ACTIONS m : stateObs.getAvailableActions()) {
			Point moveIndex = ObservationUtil.getMoveIndex(newPositionIndex, m);
			ArrayList<Observation> obsList = ObservationUtil.getObservations(stateObs, moveIndex);
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

}
