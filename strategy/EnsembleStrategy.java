package emergence_HR.strategy;

import java.util.ArrayList;

import emergence_HR.heuristics.AHeuristic;
import emergence_HR.heuristics.EquationStateHeuristic;
import emergence_HR.heuristics.SimpleStateHeuristic;
import emergence_HR.tree.Tree;

/**
 * This class is for the administration issue for the heuristics. It's a
 * singleton class!
 * 
 */
public class EnsembleStrategy {

	// all the different strategies
	public ArrayList<AStrategy> pool = new ArrayList<AStrategy>();

	// index that should be expanded on this calculation
	private int index = 0;

	// the tree that is used for iteration
	public Tree tree;

	// private constructor for singleton pattern
	public EnsembleStrategy(Tree tree) {
		this.tree = tree;
		init();
	}

	public void init() {
		pool.clear();

		
		pool.add(new LevelOrderStrategy(tree, new EquationStateHeuristic(new double[] {-49.5443045908031, -76.65986246072826, 59.59783811655683, -16.781302059663176, -15.831953558924013, -49.594771195526135, 43.633126436641476, -69.00493928938643, 67.69641417834421, 52.67659142551494})));
		pool.add(new LevelOrderStrategy(tree, new EquationStateHeuristic(new double[] {-25.386360943843627, 50.545914114289246, -69.59858153753189, 60.06842733757696, -27.963264261204543, 9.245547142335184, -73.44870954557166, -95.08564682758431, 47.334768990321095, -77.30960318093977 })));
		pool.add(new LevelOrderStrategy(tree, new EquationStateHeuristic(new double[] {62.00254023282221, -57.97680942209267, -13.042068488296877, -70.83281829850031, 44.740723616098535, -94.08400169697155, -87.69916047808206, -51.841315509569895, -16.271776407482676, -76.43933629040964})));
		pool.add(new LevelOrderStrategy(tree, new EquationStateHeuristic(new double[] { 19.37619596057891, -79.73664702906483, -96.25329543520054, -57.41270457857273, -23.11472539195603, 75.38677000001712, -75.8471732997867, 85.55967681256107, 47.99694570131024, 82.46257047984727})));
		pool.add(new LevelOrderStrategy(tree, new EquationStateHeuristic(new double[] { 88.10877648255644, -73.68912669008343, -52.889134614405585, 53.9632128811391, -83.07613574489743, -83.46284603049938, 12.114410556207432, 8.185354924682159, 92.4547649169102, 85.44081064129963})));
		pool.add(new LevelOrderStrategy(tree, new SimpleStateHeuristic()));

	}

	public boolean expand() {
		AStrategy strategy = pool.get(index % pool.size());
		strategy.expand();
		++index;
		return true;
	}

	public AHeuristic top() {
		double maxScore = Double.NEGATIVE_INFINITY;
		AHeuristic heur = null;

		for (AStrategy strategy : pool) {
			if (strategy.heuristic.getScore() > maxScore) {
				maxScore = strategy.heuristic.getScore();
				heur = strategy.heuristic;
			}
		}
		return heur;
	}

	@Override
	public String toString() {
		String s = "---------------------------\n";
		s += "heuristic pool - size: " + pool.size() + "\n";
		s += "---------------------------\n";
		for (AStrategy strategy : pool) {
			AHeuristic heuristic = strategy.heuristic;
			s += String.format("heuristic:%s -> %s \n", heuristic,
					heuristic.getScore());
		}
		s += "---------------------------\n";
		return s;
	}

}
