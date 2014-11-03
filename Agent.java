package emergence_HR;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.StateHeuristic;
import emergence_HR.heuristics.Target;
import emergence_HR.heuristics.TargetFactory;
import emergence_HR.heuristics.TargetHeuristic;
import emergence_HR.nodes.Node;
import emergence_HR.nodes.NodeComparator;
import emergence_HR.nodes.NodeTree;

public class Agent extends AbstractPlayer {

	final private boolean VERBOSE = true;

	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		LevelInfo.print(stateObs);
	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		
		
		// The action we will finally be executed
		Types.ACTIONS action = Types.ACTIONS.ACTION_NIL;

		// the current heuristic that is used
		ArrayList<Target> l = TargetFactory.getPortals(stateObs);
		Target t = l.get(0);
		
		StateHeuristic heuristic = new TargetHeuristic(t);


		Node root = new Node(stateObs);
		NodeTree tree = new NodeTree(root, heuristic);

		// initialize the values for the heuristic and the timer
		ActionTimer timer = new ActionTimer(elapsedTimer);

		action = tree.expand(timer);

		if (VERBOSE) {
			LevelInfo.printNodes(tree.queue);
			System.out.println(timer.status());
		}

		//return action;
		return Types.ACTIONS.ACTION_NIL;

	}
}
