package emergence_RL.uct.defaultPoliciy;

import emergence_RL.tree.Node;
import emergence_RL.uct.UCTSettings;

public interface IDefaultPolicy {

	public double expand(UCTSettings s, Node n);
	
}
