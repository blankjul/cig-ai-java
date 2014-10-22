package controllers.cig;

import java.util.ArrayList;
import java.util.Random;

import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;


public class Agent extends AbstractPlayer {


	protected Random randomGenerator;
	

	
	public Agent(StateObservation so, ElapsedCpuTimer elapsedTimer)
    {
        randomGenerator = new Random();
    }
	

    public Types.ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
    	
    	// Initialize the timer for all iterations that are made
    	ActionTimer timer = new ActionTimer(elapsedTimer);
    	
    	// The action we will finally execute
        Types.ACTIONS action = null;
        
        // copy of our state object
        StateObservation stCopy = stateObs.copy();
        
        
        // check whether we've enough time for a next iteration. 
        System.out.println(timer.isTimeLeft());
        
        while(timer.isTimeLeft())
        {
        	timer.start();
        	
        	
        	ArrayList<Types.ACTIONS> actions = stateObs.getAvailableActions();
            int index = randomGenerator.nextInt(actions.size());
            action = actions.get(index);
            
            stCopy.advance(action);
            if(stCopy.isGameOver())
            {
                stCopy = stateObs.copy();
            }
            
            
            
            timer.stop();
        }

        
        
        return action;
    }

}
