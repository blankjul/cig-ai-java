package emergence.util.pair;

/**
 * This is a pair class, consists o two values.
 */
public class Pair<F, S> {

	/** first member of pair */
	protected F first;

	/** second member of pair */
	protected S second;

	/**
	 * Create a pair.
	 * 
	 * @param first
	 * @param second
	 */
	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Set the first value.
	 * 
	 * @param first
	 */
	public void setFirst(F first) {
		this.first = first;
	}

	/**
	 * Set the second value.
	 * 
	 * @param second
	 */
	public void setSecond(S second) {
		this.second = second;
	}

	/**
	 * Returns the first value.
	 * 
	 * @return
	 */
	public F _1() {
		return first;
	}

	/**
	 * Returns the second value.
	 * 
	 * @return
	 */
	public S _2() {
		return second;
	}

	/**
	 * Generates a String, uses the toString-method of the two values.
	 */
	@Override
	public String toString() {
		return "(" + first.toString() + "," + second.toString() + ")";
	}

}
