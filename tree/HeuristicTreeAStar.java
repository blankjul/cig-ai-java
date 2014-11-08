package emergence_HR.tree;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

import tools.Vector2d;
import emergence_HR.ActionTimer;
import emergence_HR.heuristics.AHeuristic;

/**
 * this class expands the Tree using a modified a-star algorithm. There is 
 * no check if we found our Goal, the actual Action is set by every loop
 * in the expand method using the rootaction from the best Node of the
 * closedList.
 * and the given heuristic
 * @author spakken
 *
 */
public class HeuristicTreeAStar extends AHeuristicTree{

	//(a < b) = 1 if a.score > b.score
	Comparator<Node> comparator = new NodeComparator();
	
	//to check if a Node is on the openlist
	Map<String, Node> openSet = new HashMap<String, Node>();
	
	//to check if a Node is on the closedlist
	Map<String, Node> closedSet = new HashMap<String, Node>();
	
	//to get the best Node from the openlist
	PriorityQueue<Node> openList = new PriorityQueue<Node>(comparator);
	
	//to get the best Node from the closedlist
	PriorityQueue<Node> closedList = new PriorityQueue<Node>(comparator);
	
	public HeuristicTreeAStar(Node root, AHeuristic heuristic) {
		super(root, heuristic);
	}
	
	public void expand(ActionTimer timer) {
		//int  iterations = 0;
		//store the score in the node, so that the NodeComparator can compare them
		this.root.score = this.heuristic.evaluateState(this.root.stateObs);
		
		//add own node to the open list
		openSet.put(this.root.hash(), this.root);
		openList.add(this.root);
		
		while (timer.isTimeLeft() && !openList.isEmpty()) {
			
			Node n = openList.poll();
			openSet.remove(n.hash());
			
			//TODO check if path is found, maybe sensless in this case...
		
			//add the actual position to the closed list
			closedSet.put(n.hash(), n);
			closedList.add(n);
			
			//set the actual action, the rootaction from the best Node
			//on the closed List
			this.action = closedList.peek().getRootAction();
			//iterations++;
			this.nextStep(n);
		}
		//System.out.println("iterations: " + iterations);
	}
	
	private void nextStep(Node n){
		
		//generate all children
		LinkedList<Node> children = this.getChildren(n);
		
		//for every child that can be computed:
		for(Node child : children){
			
			//store the score in the node, so that the NodeComparator can compare them
			child.score = this.heuristic.evaluateState(child.stateObs);
			
			//if the node (Position) is already  on the closed List, do nothing
			if(closedSet.containsKey(child.hash())){
				continue;
			}
			
			Node node = openSet.get(child.hash());
			
			if(node == null){
				//add node to the openList
				openSet.put(child.hash(), child);
				openList.add(child);
				
			}else if(node.level >= child.level){
				//change path to child ni openSet
				openSet.put(child.hash(), child);
				openList.add(child);
				
			}
			
			//Node node = new Node(child.stateObs);
			//for testing
			//node = child;
			//Node checknode = openSet.get(child.hash());
			//Node checknode2 = openList.poll();
		}
		
		
		
		
		
	}

	
}
