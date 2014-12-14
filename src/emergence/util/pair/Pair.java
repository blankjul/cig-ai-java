package emergence.util.pair;

/**
 * This is a pair class that is always sorted by the value!
 */
public class Pair<F, S> {
	
	protected F first; // first member of pair
	protected S second; // second member of pair

	
	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	public void setFirst(F first) {
		this.first = first;
	}

	public void setSecond(S second) {
		this.second = second;
	}

	public F _1() {
		return first;
	}

	public S _2() {
		return second;
	}

	
	@Override
	public String toString() {
		return "(" + first.toString() + "," + second.toString() + ")";
	}

}
