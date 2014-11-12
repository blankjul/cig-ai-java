

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Configuration {

	public static String gamesAll[] = new String[] { "aliens", // "boulderdash",
			"butterflies", "chase", "frogs", "missilecommand", "portals",
			"sokoban", "survivezombies", "zelda", "camelRace", "digdug",
			"firestorms", "infection", "firecaster", "overload", "pacman",
			"seaquest", "whackamole", "eggomania" };

	public static DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	
	
	public final static int NUM_THREADS = 4;
	public static ExecutorService SCHEDULER = Executors
			.newFixedThreadPool(NUM_THREADS);

	
	

}
