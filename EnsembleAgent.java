package emergence_HR;

import java.util.Stack;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import emergence_HR.heuristics.AHeuristic;
import emergence_HR.tree.AHeuristicTree;
import emergence_HR.tree.HeuristicTreeGreedy;
import emergence_HR.tree.Node;

public class EnsembleAgent extends AbstractPlayer {

	// print out information. only DEBUG!
	final private boolean VERBOSE = true;

	// heuristic that is used
	AHeuristic heuristic;

	// tree iteration that will explore the states
	AHeuristicTree tree;

	// path that is found by the heuristic
	Stack<Types.ACTIONS> path = new Stack<Types.ACTIONS>();

	public EnsembleAgent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		// simulate the heuristic and find out which is the best
		ActionTimer timer = new ActionTimer(elapsedTimer);
		timer.timeRemainingLimit = 100;
		
		HeuristicTreeGreedy tree = new HeuristicTreeGreedy(new Node(stateObs));
		HeuristicEnsemble he = new HeuristicEnsemble(tree);
		
		// TODO: Calculate all the heuristics. not only this one!!
		// IMPORTANT!
		he.calculate(timer);
		
		heuristic = he.getTOP();

		if (VERBOSE) {
			LevelInfo.print(stateObs);
			System.out.println(he);
			System.out.println("Using now: " + heuristic);
			System.out.println(timer.status());
		}

		
		// create the path
		Node n = tree.bestNode;
		while (n.father != null) {
			path.push(n.lastAction);
			n = n.father;
		}
		path.push(n.lastAction);
		

	}

	public Types.ACTIONS act(StateObservation stateObs,
			ElapsedCpuTimer elapsedTimer) {

		if (path.isEmpty()) {

			tree = new HeuristicTreeGreedy(new Node(stateObs));

			ActionTimer timer = new ActionTimer(elapsedTimer);
			tree.expand(timer, heuristic);

			if (VERBOSE) {
				System.out.println(tree);
				System.out.println(timer.status());
			}

			// create the path
			Node n = tree.bestNode;
			while (n.father != null) {
				path.push(n.lastAction);
				n = n.father;
			}
			path.push(n.lastAction);

		}

		Types.ACTIONS action = path.pop();
		return action;
	}

}
