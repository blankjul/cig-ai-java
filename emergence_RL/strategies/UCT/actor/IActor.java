package emergence_RL.strategies.UCT.actor;

import ontology.Types;
import emergence_RL.strategies.UCT.UCTSearch;
import emergence_RL.tree.Tree;

/**
 * This is a interface and collects three different methods 
 * that could be executed.
 *
 */
public interface IActor {
	
	public Types.ACTIONS act(UCTSearch search, Tree tree);


}
