package emergence.util;

/**
 * This is a pair class that is always sorted by the value!
 */
public class Pair<F, S extends Comparable<S>> implements Comparable<Pair<F, S>>{
	private F first; // first member of pair
	private S second; // second member of pair

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
	public int compareTo(Pair<F, S> obj) {
		return obj._2().compareTo(this._2());
	}
	
	
	@Override
	public String toString() {
		return "(" + first.toString() + "," + second.toString() + ")";
	}

}
