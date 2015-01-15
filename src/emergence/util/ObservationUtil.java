package emergence.util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.TreeSet;

import ontology.Types.ACTIONS;
import tools.Vector2d;
import core.game.Event;
import core.game.Observation;
import core.game.StateObservation;

/**
 * This class provides static methods to do some operations and checks in
 * connection to the grid or the state observation.
 *
 */
public class ObservationUtil {

	/**
	 * Returns if there was a collision at the last game tick the itype of the
	 * passive sprite.
	 * 
	 * @param stateObs
	 * @return itype or null if no collision
	 */
	public static Integer collisionLastStep(StateObservation stateObs) {
		TreeSet<Event> history = stateObs.getEventsHistory();
		if (history != null && !history.isEmpty()) {
			Event e = history.last();
			if (e.gameStep == stateObs.getGameTick()) {
				return history.last().passiveTypeId;
			}
		}
		return null;
	}

	/**
	 * returns all the observations at a given point. if index is not present
	 * the list is empty
	 * 
	 * @param stateObs
	 * @param index
	 * @return
	 */
	public static ArrayList<Observation> getObservations(
			StateObservation stateObs, Point index) {
		ArrayList<Observation>[][] grid = stateObs.getObservationGrid();
		if (index.x >= 0 && index.x < grid.length && index.y >= 0
				&& index.y < grid[0].length) {
			return grid[index.x][index.y];
		} else {
			return new ArrayList<Observation>();
		}

	}

	/**
	 * return the new index at the grid if the actions is performed and the
	 * avatar is really moving
	 * 
	 * @param index
	 * @param a
	 * @return
	 */
	public static Point getMoveIndex(Point index, ACTIONS a) {
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

	/**
	 * return the index of the grid calculated from the position
	 * 
	 * @param stateObs
	 * @return
	 */
	public static Point avatarPosToGridIndex(StateObservation stateObs) {
		Vector2d avatarPosition = stateObs.getAvatarPosition();
		int iX = (int) (avatarPosition.x / stateObs.getBlockSize());
		int iY = (int) (avatarPosition.y / stateObs.getBlockSize());
		return new Point(iX, iY);
	}

	/**
	 * Returns the next position if the avatar uses the action a.
	 * 
	 * @param avatarPosition
	 * @param a
	 * @param blocksize
	 * @return
	 */
	public static Vector2d getNextPositionIfMove(Vector2d avatarPosition,
			ACTIONS a, double blocksize) {
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
}