

import java.lang.reflect.Array;
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
	
	String[] allGames = concatenate(training,validation);

	public static DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	
	
	public final static int NUM_THREADS = 4;
	public static ExecutorService SCHEDULER = Executors
			.newFixedThreadPool(NUM_THREADS);

	
	public <T> T[] concatenate (T[] A, T[] B) {
	    int aLen = A.length;
	    int bLen = B.length;

	    @SuppressWarnings("unchecked")
	    T[] C = (T[]) Array.newInstance(A.getClass().getComponentType(), aLen+bLen);
	    System.arraycopy(A, 0, C, 0, aLen);
	    System.arraycopy(B, 0, C, aLen, bLen);

	    return C;
	}

}
