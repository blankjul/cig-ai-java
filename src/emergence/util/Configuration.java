package emergence.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class defines some values and Strings which are used by other classes.
 *
 */
public class Configuration {

	/** contains all names of the levels in the trainingset */
	public static String training[] = new String[] { "aliens", "boulderdash",
			"butterflies", "chase", "frogs", "missilecommand", "portals",
			"sokoban", "survivezombies", "zelda", };

	/** contains all names of the levels in the validationset */
	public static String validation[] = new String[] { "camelRace", "digdug",
			"firestorms", "infection", "firecaster", "overload", "pacman",
			"seaquest", "whackamole", "eggomania" };

	/** contains all names of all known games */
	public static String[] allGames = Helper.concatenate(training, validation);

	/** specifies the date format which is used */
	public static DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	/** specifies the date format which is used for csv output */
	public static DateFormat dateformatCSV = new SimpleDateFormat(
			"hh_mm_ss_dd_MM_yyyy");

	/** the number of threads */
	public final static int NUM_THREADS = 4;

	/** scheduler used for multithreading */
	public static ExecutorService SCHEDULER = Executors
			.newFixedThreadPool(NUM_THREADS);
}
