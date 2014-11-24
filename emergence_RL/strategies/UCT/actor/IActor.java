package emergence_RL.strategies.UCT.actor;

import emergence_RL.strategies.UCT.UCTSearch;
import emergence_RL.tree.Node;
import emergence_RL.tree.Tree;

/**
 * This is a interface and collects three different methods 
 * that could be executed.
 *
 */
public interface IActor {
	
	public Node act(UCTSearch search, Tree tree);


}
