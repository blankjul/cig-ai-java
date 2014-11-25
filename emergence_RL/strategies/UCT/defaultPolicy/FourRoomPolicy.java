package emergence_RL.strategies.UCT.defaultPolicy;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;

import ontology.Types;
import tools.Vector2d;
import core.game.StateObservation;
import emergence_RL.helper.Helper;
import emergence_RL.strategies.UCTSearch;
import emergence_RL.tree.Node;

public class FourRoomPolicy extends ADefaultPolicy {

	// to store the forbidden Actions
	public HashSet<String> forbiddenNodes = new HashSet<String>();
	
	//position (root Node)
	public Vector2d startPosition;
	
	//action from the root
	public Types.ACTIONS dependingAction;

	// standard, otherwise the conu
	// public boolean setConusFromRoot = true;

	@Override
	public double expand(UCTSearch s, Node n) {
		
		setPositionAndAction(n);
		
		if(dependingAction != Types.ACTIONS.ACTION_USE && dependingAction != Types.ACTIONS.ACTION_NIL){
			generateForbiddenPositions(n);
		}
		
		//if the agent in the simulation is on the forbidden field
		//go in the root-Direction
		
		StateObservation currentStateObs = n.stateObs.copy();
		Types.ACTIONS currentAction = null;
		
		ArrayList<Types.ACTIONS> actions = currentStateObs
				.getAvailableActions();
		
		int level = n.level;
		
 		while (!currentStateObs.isGameOver() && level <= s.maxDepth) {
			
			//if the position is forbidden, execute the root action
			Types.ACTIONS a;
			if(forbiddenNodes.contains(currentStateObs.getAvatarPosition().toString())){
				a = dependingAction;
			}else{
				a = Helper.getRandomEntry(actions, s.r);
			}
			currentStateObs.advance(a);
			++level;
		}
		
		if (currentStateObs.isGameOver()) {
			Types.WINNER winner = currentStateObs.getGameWinner();
			if (winner == Types.WINNER.PLAYER_WINS)
				return +100;
			else if (winner == Types.WINNER.PLAYER_LOSES)
				return -1;
		} 
		
		double delta = currentStateObs.getGameScore() - n.stateObs.getGameScore();
		return delta;
	}

	public void generateForbiddenPositions(Node n) {
		
		int posx = (int) startPosition.x;
		int posy = (int) startPosition.y;

		int blocksize = n.stateObs.getBlockSize();

		Dimension gameDimension = new Dimension(n.stateObs.getWorldDimension());
		int height = (int) gameDimension.getHeight();
		int width = (int) gameDimension.getWidth();

		forbiddenNodes.add(startPosition.toString());

		// four directions

		// top right
		for (int x = posx, y = posy; x <= width && y >= 0; x += blocksize, y -= blocksize) {
			forbiddenNodes.add((double) x + " : " + (double) y);
		}

		// top left
		for (int x = posx, y = posy; x >= 0 && y >= 0; x -= blocksize, y -= blocksize) {
			forbiddenNodes.add((double) x + " : " + (double) y);
		}

		// bottom right
		for (int x = posx, y = posy; x <= width && y <= height; x += blocksize, y += blocksize) {
			forbiddenNodes.add((double) x + " : " + (double) y);
		}

		// bottom left
		for (int x = posx, y = posy; x >= 0 && y <= height; x -= blocksize, y += blocksize) {
			forbiddenNodes.add((double) x + " : " + (double) y);
		}
	}

	public void setPositionAndAction(Node n) {
		while (n.father.father != null) {
			n = n.father;
		}
		dependingAction = n.lastAction;
		startPosition = n.father.stateObs.getAvatarPosition();
	}

}
