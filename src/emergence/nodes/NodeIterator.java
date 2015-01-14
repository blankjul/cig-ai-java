package emergence.nodes;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import ontology.Types.ACTIONS;

/**
 * Iterator over all nodes in a queue.
 * 
 * @author spakken
 *
 * @param <T>
 */
public class NodeIterator<T> implements Iterator<GenericNode<T>> {

	/** start node */
	private GenericNode<T> node;

	/** queue to store the available */
	private Queue<ACTIONS> queue;

	/**
	 * Constructs a NodeIterator by setting the start node and the available
	 * actions.
	 * 
	 * @param node
	 * @param actionSet
	 */
	public NodeIterator(GenericNode<T> node, Collection<ACTIONS> actionSet) {
		super();
		this.node = node;
		this.queue = new LinkedList<>(actionSet);
	}

	/**
	 * True if the queue has a next entry.
	 */
	@Override
	public boolean hasNext() {
		return !queue.isEmpty();
	}

	/**
	 * Returns the next entry.
	 */
	@Override
	public GenericNode<T> next() {
		ACTIONS a = queue.poll();
		return node.getChild(a);
	}

	/**
	 * remove a object.
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
