package emergence.nodes;

import java.util.ArrayList;

import ontology.Types.ACTIONS;
import core.game.StateObservation;


/**
 * This is one node that saves one state of the game. There are several
 * attributes like the last action or the whole path.
 *
 */
public class Node extends GenericNode<Object>{

	
	public Node(StateObservation stateObs) {
		super(stateObs);
	}
	
	public Node(StateObservation stateObs, ArrayList<ACTIONS> path) {
		super(stateObs, path);
	}


	
	




}
