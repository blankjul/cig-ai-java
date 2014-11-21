package emergence_RL.strategies.uct.defaultPolicy;

import java.util.ArrayList;

import ontology.Types;
import tools.Utils;
import core.game.StateObservation;
import emergence_RL.strategies.uct.UCTSettings;
import emergence_RL.tree.Node;

public class SampleDefaultPolicy extends ADefaultPolicy{

    private static double[] lastBounds = new double[]{0,1};
    private static double[] curBounds = new double[]{0,1};
    
    
    private static final double HUGE_NEGATIVE = -10000000.0;
    private static final double HUGE_POSITIVE =  10000000.0;
	
    
	@Override
	public double expand(UCTSettings s, Node n) {
		
		lastBounds[0] = curBounds[0];
        lastBounds[1] = curBounds[1];
		
		StateObservation rollerState = n.stateObs.copy();
        int thisDepth = n.level;
        
        ArrayList<Types.ACTIONS> actions = n.stateObs.getAvailableActions();
        
        while (!rollerState.isGameOver() && thisDepth <= s.maxDepth) {
            int index = s.r.nextInt(actions.size());
            rollerState.advance(actions.get(index));
            thisDepth++;
        }

        double delta = value(rollerState);

        if(delta < curBounds[0]) curBounds[0] = delta;
        if(delta > curBounds[1]) curBounds[1] = delta;

        double normDelta = Utils.normalise(delta ,lastBounds[0], lastBounds[1]);

        return normDelta;
	} 
	
	
	 private double value(StateObservation a_gameState) {

	        boolean gameOver = a_gameState.isGameOver();
	        Types.WINNER win = a_gameState.getGameWinner();
	        double rawScore = a_gameState.getGameScore();

	        if(gameOver && win == Types.WINNER.PLAYER_LOSES)
	            return HUGE_NEGATIVE;

	        if(gameOver && win == Types.WINNER.PLAYER_WINS)
	            return HUGE_POSITIVE;

	        return rawScore;
	    }
	 
	 

}
