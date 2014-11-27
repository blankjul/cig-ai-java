package emergence_NI.helper;

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

	public F getFirst() {
		return first;
	}

	public S getSecond() {
		return second;
	}


	@Override
	public int compareTo(Pair<F, S> obj) {
		return obj.getSecond().compareTo(this.getSecond());
	}
	
	@Override
	public String toString() {
		return "(" + first.toString() + "," + second.toString() + ")";
	}

}
