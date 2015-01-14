package emergence;

import emergence.util.GameDetection;

/**
 * This is the global factory that provides access to all
 * single objects of the games.
 */
public class Factory {
	
	
	private static GameDetection detection = null;
	
	private static Environment env = null;
	
	private static Simulator simulator = null;
	
	private static FieldTracker fieldTracker = null;
	
	
	public static GameDetection getGameDetection() {
		if (detection == null) detection = new GameDetection();
		return detection;
	}
	
	public static Simulator getSimulator() {
		if (simulator == null) simulator = new Simulator();
		return simulator;
	}
	
	public static Environment getEnvironment() {
		if (env == null) env = new Environment();
		return env;
	}
	
	public static FieldTracker getFieldTracker() {
		if (fieldTracker == null) fieldTracker = new FieldTracker();
		return fieldTracker;
	}

}
