package emergence.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Configuration {

	
	
	public static String training[] = new String[] { "aliens", "boulderdash",
			"butterflies", "chase", "frogs", "missilecommand", "portals",
			"sokoban", "survivezombies", "zelda", };
	
	public static String validation[] = new String[] {"camelRace", "digdug",
		"firestorms", "infection", "firecaster", "overload", "pacman",
		"seaquest", "whackamole", "eggomania" };
	
	public static String[] allGames = Helper.concatenate(training,validation);

	
	public static DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	
	public static DateFormat dateformatCSV = new SimpleDateFormat("hh_mm_ss_dd_MM_yyyy");
	
	public final static int NUM_THREADS = 4;
	public static ExecutorService SCHEDULER = Executors
			.newFixedThreadPool(NUM_THREADS);



}
