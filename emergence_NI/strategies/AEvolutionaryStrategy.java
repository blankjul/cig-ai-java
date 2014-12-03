package emergence_NI.strategies;

import emergence_NI.tree.Tree;

public abstract class AEvolutionaryStrategy extends AStrategy implements
		Comparable<AEvolutionaryStrategy> {

	public AEvolutionaryStrategy() {
		super();
	};

	public AEvolutionaryStrategy(Tree tree) {
		super(tree);
	}

	/**
	 * @return a random evolution strategy
	 */
	public abstract AEvolutionaryStrategy random();

	/**
	 * Mutate one strategy
	 * 
	 * @return
	 */
	public abstract AEvolutionaryStrategy mutate();

	/**
	 * Perform a crossover between this and the given strategy.
	 * 
	 * @param strategy
	 * @return
	 */
	public abstract AEvolutionaryStrategy crossover(
			AEvolutionaryStrategy strategy);

	/**
	 * Return the score of this evolutionary strategy
	 */
	public abstract double getScore();

	
	


}
