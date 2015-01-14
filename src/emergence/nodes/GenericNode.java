package emergence.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ontology.Types.ACTIONS;
import tools.Vector2d;
import core.game.StateObservation;
import emergence.Factory;
import emergence.Simulator;
import emergence.util.Helper;

/**
 * Special node that could save one generic object!
 */
public class GenericNode<T> implements Iterable<GenericNode<T>> {

	/** the current state observation */
	protected StateObservation stateObs;

	/** path to this value */
	protected ArrayList<ACTIONS> path = new ArrayList<>();

	/** information that could be saved for that object */
	protected T info;

	/**
	 * Constructor, unused!
	 */
	public GenericNode() {
	}

	/**
	 * Create a Node that has an state observation
	 * 
	 * @param stateObs
	 *            current state observation.
	 */
	public GenericNode(StateObservation stateObs) {
		super();
		this.stateObs = stateObs;
	}

	/**
	 * Create a node that has a state observation and a path.
	 * 
	 * @param stateObs
	 * @param path
	 */
	public GenericNode(StateObservation stateObs, ArrayList<ACTIONS> path) {
		this(stateObs);
		this.path = path;
	}

	/**
	 * Returns all the children that could be expanded from the actions set. For
	 * advancing the simulator instance is used!
	 * 
	 * @param actionSet
	 *            actions that should be used for expanding.
	 * @return all children expanded by that actions.
	 */
	public Set<GenericNode<T>> getChildren(Collection<ACTIONS> actionSet) {

		// create result list and reserve memory for the temporary state object
		Set<GenericNode<T>> nodes = new HashSet<GenericNode<T>>();

		// for each possible action
		for (ACTIONS action : actionSet) {
			GenericNode<T> child = getChild(action);
			nodes.add(child);
		}

		return nodes;
	}

	/**
	 * Returns that child if the action is performed.
	 * 
	 * @param action
	 * @return child node.
	 */
	public GenericNode<T> getChild(ACTIONS action) {
		// create the next state
		StateObservation tmpStateObs;
		tmpStateObs = stateObs.copy();
		Simulator sim = Factory.getSimulator();
		sim.advance(tmpStateObs, action);

		// create the new path
		ArrayList<ACTIONS> pathOfChild = new ArrayList<>(getPath());
		pathOfChild.add(action);

		GenericNode<T> child = new GenericNode<T>(tmpStateObs, pathOfChild);
		return child;
	}

	/**
	 * Return all children with all available actions!
	 * 
	 * @return
	 */
	public Set<GenericNode<T>> getAllChildren() {
		return getChildren(stateObs.getAvailableActions());
	}

	/**
	 * return the current level from this node.
	 * 
	 * @return level.
	 */
	public int getLevel() {
		return path.size();
	}

	/**
	 * Returns the first action. That means the action from the root node to the
	 * second one one the path to achieve this node.
	 * 
	 * @return
	 */
	public ACTIONS getFirstAction() {
		if (path.isEmpty())
			return ACTIONS.ACTION_NIL;
		else
			return path.get(0);
	}

	/**
	 * Returns simply the last action that was advanced.
	 * 
	 * @return
	 */
	public ACTIONS getLastAction() {
		if (path.isEmpty())
			return ACTIONS.ACTION_NIL;
		else
			return path.get(path.size() - 1);
	}

	/**
	 * Return the path of this node.
	 * 
	 * @return
	 */
	public ArrayList<ACTIONS> getPath() {
		return path;
	}

	/**
	 * Return a "hash string" of each node. That string contains position and if
	 * the last actions was a used action.
	 * 
	 * @return
	 */
	public String hash() {
		return Helper.hash(stateObs, getLastAction());
	}

	/**
	 * String representation of the attributes.
	 */
	@Override
	public String toString() {
		Vector2d pos = stateObs.getAvatarPosition();
		return String.format("me:[%s,%s] | level%s | path:%s", pos.x, pos.y,
				getLevel(), Helper.toString(path));
	}

	/**
	 * Get the specified info object.
	 * 
	 * @return
	 */
	public T getInfo() {
		return info;
	}

	/**
	 * Iterator implementations.
	 */
	@Override
	public Iterator<GenericNode<T>> iterator() {
		return new NodeIterator<T>(this, new HashSet<>(
				stateObs.getAvailableActions()));
	}

	/**
	 * Iterator implementation.
	 * 
	 * @param actionSet
	 * @return
	 */
	public Iterator<GenericNode<T>> iteratorFromActions(
			Collection<ACTIONS> actionSet) {
		return new NodeIterator<T>(this, actionSet);
	}

	/**
	 * Returns the state observation.
	 * 
	 * @return
	 */
	public StateObservation getStateObs() {
		return stateObs;
	}

	/**
	 * Sets the state observation.
	 * 
	 * @param stateObs
	 */
	public void setStateObs(StateObservation stateObs) {
		this.stateObs = stateObs;
	}

	/**
	 * Sets the path of this node.
	 * 
	 * @param path
	 */
	public void setPath(ArrayList<ACTIONS> path) {
		this.path = path;
	}
}
