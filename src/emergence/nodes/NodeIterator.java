package emergence.nodes;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import ontology.Types.ACTIONS;

public class NodeIterator<T> implements Iterator<GenericNode<T>> {

	private GenericNode<T> node;
	private Queue<ACTIONS> queue;
	
	
	
    public NodeIterator(GenericNode<T> node, Collection<ACTIONS> actionSet) {
		super();
		this.node = node;
		this.queue = new LinkedList<>(actionSet);
	}

    
	@Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public GenericNode<T> next() {
    	ACTIONS a = queue.poll();
    	return node.getChild(a);
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
	
}
