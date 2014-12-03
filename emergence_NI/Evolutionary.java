package emergence_NI;




public abstract class Evolutionary<T> {
	
	
	/**
	 * @return a random evolution strategy
	 */
	abstract public T random();

	/**
	 * Mutate one strategy
	 * 
	 * @return
	 */
	abstract public T mutate();

	/**
	 * Perform a crossover between this and the given strategy.
	 * 
	 * @param strategy
	 * @return
	 */
	abstract public T crossover(T strategy);


	

	

}
