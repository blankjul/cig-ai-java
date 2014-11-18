
import java.util.Random;

import core.ArcadeMachine;


public class OneAgentOneGame
{

    public static void main(String[] args)
    {
        
    	String controller = "emergence_RL.Agent";
    	//controller = "controllers.sampleMCTS.Agent";
    	
        String gameStr = "missilecommand";
        int levelIdx = 1; 
        
        
        //"aliens", "boulderdash", "butterflies", "chase", "frogs",
        //"missilecommand", "portals", "sokoban", "survivezombies", "zelda", 
        //"camelRace", "digdug", "firestorms", "infection", "firecaster",
        //"overload", "pacman", "seaquest", "whackamole", "eggomania"

        //Available games:
        String gamesPath = "examples/gridphysics/";
        
        //Other settings
        boolean visuals = true;
        String recordActionsFile = null; //where to record the actions executed. null if not to save.
        int seed = new Random().nextInt();

        //Game and level to play
        String game = gamesPath + gameStr + ".txt";
        String level1 = gamesPath + gameStr + "_lvl" + levelIdx +".txt";
        ArcadeMachine.runOneGame(game, level1, visuals, controller, recordActionsFile, seed);


    }
}
