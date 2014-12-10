package emergence;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ontology.Types.ACTIONS;
import tools.Vector2d;
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

	
	public StateObservation advance(StateObservation stateObs, ACTIONS a) {
		init(stateObs);
		Vector2d avatarPosition = stateObs.getAvatarPosition();
		String hash = Helper.hash(stateObs);
		stateObs.advance(a);

		// if agent was not moving and it was no use function
		if (hash.equals(Helper.hash(stateObs))) {

			// the grid
			ArrayList<Observation>[][] grid = stateObs.getObservationGrid();

			// indices of the agent at the grid
			Point index = positionToGridIndex(avatarPosition);

			// look what object is where the action should lead to
			Point obsIndex = getMoveIndex(index, a);

			// if were not out of bounds
			if (obsIndex.x >= 0 && obsIndex.x < grid.length && obsIndex.y >= 0
					&& obsIndex.y < grid[0].length) {

				ArrayList<Observation> obsList = grid[obsIndex.x][obsIndex.y];

				// only if there is one observation you now that this is the
				// blocking cause
				if (obsList.size() == 1) {
					Observation obs = obsList.get(0);
					blockingSprites.add(obs.itype);
				}
			}
		}
		return stateObs;
	}

	/**
	 * Returns all actions that expects a change of this position or USE
	 * 
	 * @param stateObs
	 * @return all actions excepts a new position and USE
	 */
	public Set<ACTIONS> getRecommendActions(StateObservation stateObs) {
		init(stateObs);
		Point index = positionToGridIndex(stateObs.getAvatarPosition());
		ArrayList<Observation>[][] grid = stateObs.getObservationGrid();

		Set<ACTIONS> result = new HashSet<>();

		for (ACTIONS a : stateObs.getAvailableActions()) {

			Point obsIndex = getMoveIndex(index, a);

			if (obsIndex.x >= 0 && obsIndex.x < grid.length && obsIndex.y >= 0
					&& obsIndex.y < grid[0].length) {
				ArrayList<Observation> obsList = grid[obsIndex.x][obsIndex.y];

				// only if there is one observation you now that this is the
				// blocking cause
				if (obsList.size() == 1) {
					Integer itype = obsList.get(0).itype;
					if (blockingSprites.contains(itype)) continue;
				}
				result.add(a);
			}

		}
		return result;
	}

	public Set<Integer> getBlockingSprites() {
		return blockingSprites;
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

}
