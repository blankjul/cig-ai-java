package emergence_RL.strategies.uct.actor;

import ontology.Types;
import emergence_RL.strategies.uct.UCTSettings;
import emergence_RL.tree.Tree;

/**
 * This is a interface and collects three different methods 
 * that could be executed.
 *
 */
public interface IActor {
	
	public Types.ACTIONS act(UCTSettings s, Tree tree);


}
