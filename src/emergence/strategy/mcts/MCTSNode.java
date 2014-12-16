package emergence.strategy.mcts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ontology.Types;
import ontology.Types.ACTIONS;
import ontology.Types.WINNER;
import core.game.StateObservation;
import emergence.Factory;
import emergence.heuristics.AHeuristic;
import emergence.nodes.GenericNode;
import emergence.util.Helper;
import emergence.util.pair.Pair;

/**
 * This class represents a TreeNode. Important is that there is the possibility
 * to simulate next steps of a node. This has the consequence that there are
 * always father and children states.
 */
public class MCTSNode extends GenericNode<Object> {


	// father node, if null it's the root
	private MCTSNode father = null;

	// children store
	private HashMap<ACTIONS, MCTSNode> children = new HashMap<>();
	
	// the value for the exploration term
	private double c = Math.sqrt(2);
	
	// number of visits
	private int visited = 0;
	
	// random generator
	private Random r = new Random();
	
	// if this node was one time is loosing node
	private boolean loosingAtSimulation;

	// very small value
	public static double epsilon = 0.0000000001d;
	
	// reward of this node
	public double Q = 0d;

	// the uct value
	public double uct = 0d;
	
	private double heuristicValue;

	
	
	public MCTSNode(MCTSNode father) {
		super();
		this.father = father;
	}
	
	/**
	 * Construct a MCTS Node. no state observation is needed it's open loop!
	 * 
	 * @param father
	 *            previous node
	 * @param lastAction
	 *            last action to come to this node
	 */
	public MCTSNode(MCTSNode father, Types.ACTIONS lastAction) {
		this(father);
		// create the new path
		if (father != null) path = new ArrayList<>(father.getPath());
		path.add(lastAction);

	}

	/**
	 * Check if there are actions that were not expanend
	 * 
	 * @param stateObs
	 * @return false if there could be new children
	 */
	public boolean isFullyExpanded(StateObservation stateObs) {
		return stateObs.getAvailableActions().size() == children.size();
	}

	/**
	 * Removes the first action
	 */
	public void removeFirstAction() {
		if (!path.isEmpty()) {
			path.remove(0);
		}
	}
	

	@Override
	public GenericNode<Object> getChild(ACTIONS lastAction) {
		MCTSNode child = new MCTSNode(this, lastAction);
		this.children.put(lastAction, child);
		return child;
	}
	
	
	@Override
	public Set<GenericNode<Object>> getAllChildren() {
		Set<GenericNode<Object>> result = new HashSet<>();
		for (ACTIONS a : children.keySet()) {
			MCTSNode n = children.get(a);
			result.add(n);
		}
		return result;
	}
	
	
	public GenericNode<Object> getRandomUnexpandedChildren(StateObservation stateObs) {
		Set<ACTIONS> allActions = new HashSet<>(stateObs.getAvailableActions());
		allActions.removeAll(children.keySet());
		
		if (allActions.isEmpty()) return null;
		ACTIONS rndAction = Helper.getRandomEntry(allActions);
		GenericNode<Object> child = getChild(rndAction);
		
		return child;
	}
	

	public MCTSNode getFather() {
		return father;
	}

	public void setFather(MCTSNode father) {
		this.father = father;
	}
	
	
	public Pair<MCTSNode, StateObservation> bestChild(Pair<MCTSNode, StateObservation> pair, AHeuristic heuristic) {
		
		MCTSNode n = pair._1();
		StateObservation stateObs = pair._2();

		// always the best child is saved here
		MCTSNode bestChild = null;
		double bestValue = Double.NEGATIVE_INFINITY;
		StateObservation bestStateObs = null;
		

		for (GenericNode<Object> childGeneric : getAllChildren()) {

			MCTSNode child = (MCTSNode) childGeneric;
			
			// get the current state observation from the child
			StateObservation stateObsChild = stateObs.copy();
			Factory.getSimulator().advance(stateObsChild, child.getLastAction());
			child.checkLoosing(stateObs);
			
			double exploitation = child.Q / (child.visited + epsilon * r.nextDouble());
			double exploration = Math.sqrt(Math.log(n.visited + 1) / (child.visited));
			double history = getHistoryValue(stateObsChild, child.getLastAction());
			child.heuristicValue = (heuristic == null) ? 0 : heuristic.evaluateState(stateObsChild);
			double tiebreak = r.nextDouble() * epsilon;

			child.uct = exploitation + c * exploration  + history + child.heuristicValue + tiebreak;

			if (child.uct >= bestValue) {
				bestChild = child;
				bestValue = child.uct;
				bestStateObs = stateObsChild;
			}
		}
		return new Pair<MCTSNode, StateObservation>(bestChild, bestStateObs); 
	}
	
	
	public Pair<MCTSNode, StateObservation> bestChild(Pair<MCTSNode, StateObservation> pair) {
		return bestChild(pair, null);
	}
	

	public boolean isLoosingAtSimulation() {
		return loosingAtSimulation;
	}
	
	public void checkLoosing(StateObservation stateobs) {
		if (stateobs.getGameWinner() == WINNER.PLAYER_LOSES) {
			loosingAtSimulation = true;
		}
	}
	
	public int getVisited() {
		return visited;
	}

	public void addVisited() {
		this.visited += 1;
	}

	@Override
	public String toString() {
		return String.format("level:%s | path:%s | visited:%s | Q:%s | heuristic:%s", getLevel(), Helper.toString(path), visited, Q, heuristicValue);
	}
	
	
	public double getHistoryValue(StateObservation stateObs, ACTIONS lastAction) {
		String h = Helper.hash(stateObs, lastAction);
		Integer visitsOfField = FieldTracker.fieldVisits.get(h);
		double historyValue = 1;
		if (visitsOfField != null && FieldTracker.maxVisitedField > 0) {
			historyValue = Math.sqrt((1 - visitsOfField
					/ (double) FieldTracker.maxVisitedField));
		}
		return historyValue;
	}


}
