package emergence_NI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class PathComparator implements Comparator<Evolutionary<Path>>{

	
	public static int TYPE = 0;
	
	public static int RANDOM = 0;
	public static int PORTAL = 1;
	public static int NPC = 2;
	

	public static double[] reward = new double[3];
	public static int[] used = new int[3];
	
	public static void setType() {
		ArrayList<Double> avg = new ArrayList<>();
		
		int maxUsed = -1;
		for (int i = 0; i < used.length; i++) {
			if (used[i] + Agent.r.nextDouble() * 0.000001 > maxUsed) maxUsed = used[i];
		}

		for (int i = 0; i < reward.length; i++) {
			double exploitation =  (used[i] != 0) ? reward[i] / used[i] : Double.POSITIVE_INFINITY;
			double exploration = Math.sqrt(Math.log(used[i] / maxUsed));
			double value = exploitation + exploration;
			avg.add(value);
		}
		double max = Collections.max(avg);
		int index = avg.indexOf(max);
		PathComparator.TYPE = index;
	}
	

	@Override
	public int compare(Evolutionary<Path> o1, Evolutionary<Path> o2) {
		Path p1 = (Path) o1;
		Path p2 = (Path) o2;
		
		Double d1 = p1.getScore();
		Double d2 = p2.getScore();
		int result = d2.compareTo(d1);
		if (result == 0) {
			if (TYPE == PORTAL) {
				d1 = p1.portalValue;
				d2 = p2.portalValue;
			} else if (TYPE == NPC) {
				d1 = p1.npcValue;
				d2 = p2.npcValue;
			} 
			result = d2.compareTo(d1);
			
			if (TYPE == RANDOM || result == 0) {
				d1 = Agent.r.nextDouble();
				d2 = Agent.r.nextDouble();
				result = d2.compareTo(d1);
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		String s = "";
		s += "PATHCOMP REWARD: " + Arrays.toString(PathComparator.reward) + '\n';
		s += "PATHCOMP USED: " + Arrays.toString(PathComparator.used)+ '\n';
		s += "PATHCOMP TYPE: " + PathComparator.TYPE + '\n';
		return s;
	}

}
