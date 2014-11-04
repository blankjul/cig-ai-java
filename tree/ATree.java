package emergence_HR.tree;

import ontology.Types;
import ontology.Types.WINNER;
import emergence_HR.ActionTimer;

/**
 * This is an abstract tree that has several attributes that should be needed by
 * every class that inherits from this one.
 * 
 * There are abstract method that has to be implement at children classes: -
 * expand: strategy to expand the tree -> oneStepAhead, levelOrder,
 * bestHeuristic, astar - action: returns always the action that is currently
 * the best for this simulation
 * 
 */
abstract public class ATree {

	public Node root;

	protected double score;

	public ATree(Node root) {
		this.root = root;
		this.root.level = 0;
		this.score = 0;
	}

	/**
	 * expand the tree until the timer says there is no time left. there could
	 * be implemented different methods!
	 */
	abstract public void expand(ActionTimer timer);

	/**
	 * returns the action that should be done if we need a decision now!
	 */
	abstract public Types.ACTIONS action();

	/**
	 * Get the score from the current tree that uses the given strategy
	 */
	public double getScore() {
		return score;
	}


	public void addScore(Node n) {
		if (n.stateObs.getGameWinner() == WINNER.PLAYER_WINS) score += 100;
		score += n.stateObs.getGameScore();
	}

}
