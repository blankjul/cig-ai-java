package emergence.util;

/**
 * This is a pair class that is always sorted by the value!
 */
public class SortedPair<F, S extends Comparable<S>> extends Pair<F, S> implements Comparable<SortedPair<F, S>>{
	

	public SortedPair(F first, S second) {
		super(first,second);
	}


	@Override
	public int compareTo(SortedPair<F, S> obj) {
		return obj._2().compareTo(this._2());
	}
	

}
