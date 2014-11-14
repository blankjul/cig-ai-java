package emergence_RL.strategy.uct.actor;

import emergence_RL.strategy.UCTSearch;
import emergence_RL.tree.Tree;
import ontology.Types;

/**
 * This is a interface and collects three different methods 
 * that could be executed.
 *
 */
public interface IActor {
	
	public Types.ACTIONS act(UCTSearch s, Tree tree);


}
