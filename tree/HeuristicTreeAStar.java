package emergence_HR.tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;

import emergence_HR.ActionTimer;
import emergence_HR.heuristics.AHeuristic;

/**
 * this class expands the Tree using a modified a-star algorithm
 * and the given heuristic
 * @author spakken
 *
 */
public class HeuristicTreeAStar extends AHeuristicTree{

	Comparator<Node> comparator = new NodeComparator();
	
	TreeSet<Node> openList = new TreeSet<Node>(comparator);
	
	//storage of the forbidden (visited) positions
	TreeSet<Node> closedList = new TreeSet<Node>(comparator);
	
	public HeuristicTreeAStar(Node root, AHeuristic heuristic) {
		super(root, heuristic);
	}
	
	public void expand(ActionTimer timer) {
		
		//add own node to the open list
		openList.add(this.root);
		
		while (timer.isTimeLeft() && !openList.isEmpty()) {
			
			//Node comparator must work correctly -> first() gives the LOWEST element!
			Node n = openList.first();
			
			//TODO check if path is found, maybe sensless in this case...
			
			//add the actual position to the closed list
			closedList.add(n);
			
			this.nextStep(n);
		}
	}
	
	private void nextStep(Node n){
		
		LinkedList<Node> children = this.getChildren(n);
		
		//for every child that can be computed:
		for(Node child : children){
			
			//if the node (Position) is already  on the closed List, do nothing
			if(closedList.contains(child)){
				continue;
			}
			
			//if the actual Node is already on the open list and the new way isnt
			//shorter (depth of the tree) do nothing
			if(openList.contains(child)){
				continue;
			}
			
			//TODO kosten für den weg zum aktuellen knoten berechnen, hier sinnvoll?
			
		
			//the actual Node (the position of the Avatar) on the openList and 
			//the action 
			
		}
		
		
		
		
		
	}

	
}
