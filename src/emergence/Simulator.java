package emergence;

import ontology.Types.ACTIONS;
import tools.Vector2d;
import core.game.StateObservation;

/**
 * This class provides same advanced simulation and tracks statistics of it.
 * This should be used for all the simulation of recommended moves is used.
 */
public class Simulator {

	

	public StateObservation advance(StateObservation stateObs, ACTIONS a) {

		Vector2d oldAvatarPosition = stateObs.getAvatarPosition();
		double oldScore = stateObs.getGameScore();
		stateObs.advance(a);
		
		Factory.getEnvironment().update(stateObs, a, oldAvatarPosition, oldScore);

		return stateObs;
	}


	




}
