package emergence;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import ontology.Types.ACTIONS;
import ontology.Types.WINNER;
import tools.Vector2d;
import core.game.Event;
import core.game.Observation;
import core.game.StateObservation;
import emergence.util.Helper;

/**
 * This class provides same advanced simulation and tracks statistics of it.
 * This should be used for all the simulation of recommended moves is used.
 */
public class Simulator {

	// store the blocksize
	private Double blocksize;

	// this contains all category ids of blocking sprites
	private Set<Integer> blockingSprites = new HashSet<Integer>();

	// this set contains all the sprites that followed to a win
	private Set<Integer> winSprites = new HashSet<Integer>();

	// sprites that has a consequence to loose
	private Set<Integer> looseSprites = new HashSet<Integer>();

	// sprites that causes a score
	private Set<Integer> scoreSprites = new HashSet<Integer>();

	// type of the agent
	private int myType = -1;

	public StateObservation advance(StateObservation stateObs, ACTIONS a) {
		init(stateObs);

		Vector2d oldAvatarPosition = stateObs.getAvatarPosition();
		// ArrayList<Observation>[][] oldGrid = stateObs.getObservationGrid();
		double oldScore = stateObs.getGameScore();

		stateObs.advance(a);

		updateBlockingSprites(stateObs, oldAvatarPosition, a);
		updateGameWinnerState(stateObs, WINNER.PLAYER_WINS, winSprites);
		updateGameWinnerState(stateObs, WINNER.PLAYER_LOSES, looseSprites);
		updateScoreSprite(stateObs, oldScore);

		return stateObs;
	}

	public Set<ACTIONS> getSafeActions(StateObservation stateObs) {
		Set<ACTIONS> result = new HashSet<>();
		// for all possible moves
		for (ACTIONS a : stateObs.getAvailableActions()) {
			if (isSafe(stateObs, a))
				result.add(a);
		}
		return result;
	}

	// index where we are now.
	public boolean isSafe(StateObservation stateObs, ACTIONS a) {
		init(stateObs);

		Set<Integer> sprites = new HashSet<>();

		// get the position of we do this move
		Point index = positionToGridIndex(stateObs.getAvatarPosition());
		Point newPositionIndex = getMoveIndex(index, a);

		// check all fields around for observations
		for (ACTIONS m : stateObs.getAvailableActions()) {
			Point moveIndex = getMoveIndex(newPositionIndex, m);
			ArrayList<Observation> obsList = getObservations(stateObs, moveIndex);
			for (Observation obs : obsList) {
				sprites.add(obs.itype);
			}
		}

		// now all the sprites around that move are collected
		// remove all sprites that are not dangerous
		sprites.removeAll(blockingSprites);
		sprites.removeAll(scoreSprites);
		sprites.removeAll(winSprites);
		sprites.remove(myType);

		return sprites.isEmpty();

	}

	public Set<ACTIONS> getSafeActionAdvance(StateObservation stateObs, int n) {
		Set<ACTIONS> result = new HashSet<>();
		ArrayList<ACTIONS> actions = stateObs.getAvailableActions();
		Collections.shuffle(actions);
		for (ACTIONS a : actions) {
			if (isSafeWithAdvance(stateObs, a, n)) result.add(a);
		}
		return result;
	}

	public boolean isSafeWithAdvance(StateObservation stateObs, ACTIONS a, int n) {
		for (int i = 0; i < n; i++) {
			StateObservation tmp = stateObs.copy();
			advance(tmp, a);
			if (tmp.getGameWinner() == WINNER.PLAYER_LOSES) return false;
		}
		return true;
	}
	
	

	
	/**
	 * Returns all actions that expects a change of this position or USE
	 * 
	 * @param stateObs
	 * @return all actions excepts a new position and USE
	 */
	public Set<ACTIONS> getMoveActions(StateObservation stateObs) {
		init(stateObs);
		Point index = positionToGridIndex(stateObs.getAvatarPosition());
		ArrayList<Observation>[][] grid = stateObs.getObservationGrid();

		Set<ACTIONS> result = new HashSet<>();

		for (ACTIONS a : stateObs.getAvailableActions()) {

			Point obsIndex = getMoveIndex(index, a);

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

	private void updateScoreSprite(StateObservation stateObs, double oldScore) {
		if (stateObs.getGameScore() > oldScore) {
			TreeSet<Event> history = stateObs.getEventsHistory();
			if (history != null && !history.isEmpty()) {
				Event e = history.first();
				if (e.gameStep == stateObs.getGameTick()) scoreSprites.add(history.first().passiveTypeId);
			}
		}
	}

	private void updateGameWinnerState(StateObservation stateObs, WINNER w, Set<Integer> setToAdd) {
		if (stateObs.getGameWinner() == w) {
			TreeSet<Event> history = stateObs.getEventsHistory();
			if (history != null && !history.isEmpty()) {
				Event e = history.first();
				if (e.gameStep == stateObs.getGameTick()) setToAdd.add(history.first().passiveTypeId);
			}
		}
	}

	// update all the blocking sprites
	private void updateBlockingSprites(StateObservation stateObs, Vector2d oldAvatarPosition, ACTIONS a) {

		Vector2d avatarPosition = stateObs.getAvatarPosition();
		String hash = Helper.hash(stateObs);

		// if agent was not moving and it was no use function
		if (hash.equals(Helper.hash(stateObs))) {

			// indices of the agent at the grid
			Point index = positionToGridIndex(avatarPosition);

			// look what object is where the action should lead to
			Point obsIndex = getMoveIndex(index, a);

			// get all the observations on that point
			ArrayList<Observation> obsList = getObservations(stateObs, obsIndex);

			// only if there is one observation you now that this is the
			// blocking cause
			if (obsList.size() == 1) {
				Observation obs = obsList.get(0);
				blockingSprites.add(obs.itype);
			}
		}
	}

	// returns all the observation at a given point. if index is not present the
	// list is empty
	private ArrayList<Observation> getObservations(StateObservation stateObs, Point index) {
		ArrayList<Observation>[][] grid = stateObs.getObservationGrid();
		if (index.x >= 0 && index.x < grid.length && index.y >= 0 && index.y < grid[0].length) {
			return grid[index.x][index.y];
		} else {
			return new ArrayList<Observation>();
		}

	}

	// return the new index at the grid if the actions is performed
	// and the controller is really moving
	private Point getMoveIndex(Point index, ACTIONS a) {
		if (a == ACTIONS.ACTION_DOWN) {
			return new Point(index.x, index.y + 1);
		} else if (a == ACTIONS.ACTION_LEFT) {
			return new Point(index.x - 1, index.y);
		} else if (a == ACTIONS.ACTION_UP) {
			return new Point(index.x, index.y - 1);
		} else if (a == ACTIONS.ACTION_RIGHT) {
			return new Point(index.x + 1, index.y);
		} else {
			return new Point(index.x, index.y);
		}
	}

	// just set initial values if they are not present yet
	private void init(StateObservation stateObs) {
		if (blocksize == null)
			blocksize = (double) stateObs.getBlockSize();
		if (myType == -1) {
			Point myIndex = positionToGridIndex(stateObs.getAvatarPosition());
			ArrayList<Observation> obsList = getObservations(stateObs, myIndex);
			if (obsList.size() > 0) {
				myType = obsList.get(0).itype;
			}
		}

	}

	// return the index of the grid calculated from the position
	private Point positionToGridIndex(Vector2d avatarPosition) {
		int iX = (int) (avatarPosition.x / blocksize);
		int iY = (int) (avatarPosition.y / blocksize);
		return new Point(iX, iY);
	}

	@SuppressWarnings("unused")
	private Vector2d getNextPositionIfMove(Vector2d avatarPosition, ACTIONS a) {
		double x = avatarPosition.x;
		double y = avatarPosition.y;
		if (a == ACTIONS.ACTION_LEFT)
			return new Vector2d(x - blocksize, y);
		else if (a == ACTIONS.ACTION_UP)
			new Vector2d(x, y - blocksize);
		else if (a == ACTIONS.ACTION_RIGHT)
			new Vector2d(x + blocksize, y);
		else if (a == ACTIONS.ACTION_DOWN)
			new Vector2d(x, y + blocksize);

		return new Vector2d(x, y);

	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		//sb.append(String.format("myType: %s | ", myType));
		sb.append("block:" + Helper.toString(blockingSprites) + " | ");
		sb.append("score:" + Helper.toString(scoreSprites) + " | ");
		sb.append("loose:" + Helper.toString(looseSprites) + " | ");
		sb.append("win:" + Helper.toString(winSprites) + " | ");
		return sb.toString();
	}
	

	
	/*
	 * Getter methods
	 */

	public Set<Integer> getBlockingSprites() {
		return blockingSprites;
	}

	public Set<Integer> getWinSprites() {
		return winSprites;
	}

	public Set<Integer> getLooseSprites() {
		return looseSprites;
	}

	public Set<Integer> getScoreSprites() {
		return scoreSprites;
	}

}
