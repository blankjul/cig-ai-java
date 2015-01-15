package emergence.util.pair;

/**
 * This is a pair class that is always sorted by the second value!
 */
public class SortedPair<F, S extends Comparable<S>> extends Pair<F, S>
		implements Comparable<SortedPair<F, S>> {

	/**
	 * Creates a sorted pair by just calling the super constructor.
	 * 
	 * @param first
	 * @param second
	 */
	public SortedPair(F first, S second) {
		super(first, second);
	}

	/**
	 * Sorts the Pairs by the the second value.
	 */
	@Override
	public int compareTo(SortedPair<F, S> obj) {
		return obj._2().compareTo(this._2());
	}
}