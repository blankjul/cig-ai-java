package emergence;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ontology.Types.ACTIONS;
import ontology.Types.WINNER;
import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;
import emergence.targets.ATarget;
import emergence.targets.DynamicTarget;
import emergence.targets.ATarget.TYPE;
import emergence.targets.TargetFactory;
import emergence.util.Helper;
import emergence.util.ObservationUtil;

/**
 * The Environment class safes all the observation of the environment that were
 * made by simulating using the simulator class. It tracks the blocking,
 * scoring, loosing and winning sprites. For that there are some information
 * before and after advancing are needed. Use the update method to keep the
 * environment really up to date.
 *
 */
public class Environment {

	// store the blocksize
	private Double blocksize;

	// type of the agent
	private int avatarType = -1;

	// this contains all category ids of blocking sprites
	private Set<ATarget> blockingSprites = new HashSet<ATarget>();

	// this set contains all the sprites that followed to a win
	private Set<ATarget> winSprites = new HashSet<ATarget>();

	// sprites that has a consequence to loose
	private Set<ATarget> looseSprites = new HashSet<ATarget>();

	// sprites that causes a score
	private Set<ATarget> scoreSprites = new HashSet<ATarget>();

	/**
	 * Updates all the values that might help for the exploration
	 */
	public void update(StateObservation stateObs, ACTIONS lastAction, Vector2d oldAvatarPos, double oldScore) {
		init(stateObs);
		updateBlockingSprites(stateObs, oldAvatarPos, lastAction);
		updateGameEndState(stateObs, WINNER.PLAYER_WINS, winSprites);
		updateGameEndState(stateObs, WINNER.PLAYER_LOSES, looseSprites);
		updateScoreSprite(stateObs, oldScore);
	}

	public void reset() {
		blockingSprites.clear();
		looseSprites.clear();
		scoreSprites.clear();
		winSprites.clear();
	}

	// just set initial values if they are not present yet
	private void init(StateObservation stateObs) {
		if (blocksize == null)
			blocksize = (double) stateObs.getBlockSize();
		if (avatarType == -1) {
			Point myIndex = ObservationUtil.avatarPosToGridIndex(stateObs);
			ArrayList<Observation> obsList = ObservationUtil.getObservations(stateObs, myIndex);
			if (obsList.size() > 0) {
				avatarType = obsList.get(0).itype;
			}
		}
	}

	private void updateScoreSprite(StateObservation stateObs, double oldScore) {
		if (stateObs.getGameScore() > oldScore) {
			Integer itype = ObservationUtil.collisionLastStep(stateObs);
			if (itype != null) {
				TYPE t = TargetFactory.getType(stateObs, itype);
				if (t != null) {
					scoreSprites.add(new DynamicTarget(t, itype));
				}
			}
		}
	}

	private void updateGameEndState(StateObservation stateObs, WINNER w, Set<ATarget> setToAdd) {
		if (stateObs.getGameWinner() == w) {
			Integer itype = ObservationUtil.collisionLastStep(stateObs);
			if (itype != null) {
				TYPE t = TargetFactory.getType(stateObs, itype);
				if (t != null) {
					setToAdd.add(new DynamicTarget(t, itype));
				}
			}
		}
	}

	// update all the blocking sprites
	private void updateBlockingSprites(StateObservation stateObs, Vector2d oldAvatarPosition, ACTIONS a) {

		// if agent was not moving and it was no use function
		if (stateObs.getAvatarPosition().equals(oldAvatarPosition) && a != ACTIONS.ACTION_USE
				&& a != ACTIONS.ACTION_NIL) {

			// indices of the agent at the grid
			Point index = ObservationUtil.avatarPosToGridIndex(stateObs);

			// look what object is where the action should lead to
			Point obsIndex = ObservationUtil.getMoveIndex(index, a);

			// get all the observations on that point
			ArrayList<Observation> obsList = ObservationUtil.getObservations(stateObs, obsIndex);

			// only if there is one observation you now that this is the
			// blocking cause
			if (obsList.size() == 1) {
				Observation obs = obsList.get(0);
				TYPE t = TargetFactory.getType(stateObs, obs.itype);
				if (t != null && t != TYPE.Portal) {
					blockingSprites.add(new DynamicTarget(t, obs.itype));
				}
			}
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		// sb.append(String.format("myType: %s | ", myType));
		sb.append("block:" + Helper.toString(blockingSprites) + " | ");
		sb.append("score:" + Helper.toString(scoreSprites) + " | ");
		sb.append("loose:" + Helper.toString(looseSprites) + " | ");
		sb.append("win:" + Helper.toString(winSprites) + " | ");
		return sb.toString();
	}

	/**
	 * Returns all actions that expects a change of this position or USE
	 * 
	 * @param stateObs
	 * @return all actions excepts a new position and USE
	 */
	public Set<ACTIONS> getMoveActions(StateObservation stateObs) {

		init(stateObs);
		Point index = ObservationUtil.avatarPosToGridIndex(stateObs);
		ArrayList<Observation>[][] grid = stateObs.getObservationGrid();

		Set<ACTIONS> result = new HashSet<>();

		for (ACTIONS a : stateObs.getAvailableActions()) {

			Point obsIndex = ObservationUtil.getMoveIndex(index, a);

			if (obsIndex.x >= 0 && obsIndex.x < grid.length && obsIndex.y >= 0 && obsIndex.y < grid[0].length) {
				ArrayList<Observation> obsList = grid[obsIndex.x][obsIndex.y];

				// only if there is one observation you now that this is the
				// blocking cause
				if (obsList.size() == 1) {
					Integer itype = obsList.get(0).itype;
					if (blockingSprites.contains(itype))
						continue;
				}
				result.add(a);
			}
		}
		return result;
	}

	/*
	 * Getter methods
	 */

	public Set<ATarget> getBlockingSprites() {
		return blockingSprites;
	}

	public Set<ATarget> getWinSprites() {
		return winSprites;
	}

	public Set<ATarget> getLooseSprites() {
		return looseSprites;
	}

	public Set<ATarget> getScoreSprites() {
		return scoreSprites;
	}

	public Integer getAvatarType() {
		return avatarType;
	}

	public ATarget getWinningTarget() {
		if (winSprites.isEmpty())
			return null;
		else
			return Helper.getRandomEntry(winSprites);
	}

	public ATarget getScoringTarget(StateObservation stateObs) {
		if (scoreSprites.isEmpty())
			return null;
		else {
			Set<ATarget> targets = new HashSet<ATarget>(scoreSprites);
			ATarget target;
			do {
				target = Helper.getRandomEntry(scoreSprites);
				targets.remove(target);
				if (target.exists(stateObs)) targets.clear();

			} while (target != null && !targets.isEmpty());

			return target;
		}
	}
}
