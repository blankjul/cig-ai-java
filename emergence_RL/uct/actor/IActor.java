package emergence_RL.uct.actor;

import ontology.Types;
import emergence_RL.tree.Tree;
import emergence_RL.uct.UCTSettings;

/**
 * This is a interface and collects three different methods 
 * that could be executed.
 *
 */
public interface IActor {
	
	public Types.ACTIONS act(UCTSettings s, Tree tree);


}
