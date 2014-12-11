package emergence;

import emergence.util.GameDetection;

public class Factory {
	
	
	// the game detection instance
	private static GameDetection detection = null;
	
	private static Simulator simulator = null;
	
	
	public static GameDetection getGameDetection() {
		if (detection == null) detection = new GameDetection();
		return detection;
	}
	
	public static Simulator getSimulator() {
		if (simulator == null) simulator = new Simulator();
		return simulator;
	}
	
	

}
