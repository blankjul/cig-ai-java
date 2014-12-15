package emergence.strategy.evolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import emergence.agents.EvolutionaryAgent;

public class PathComparator implements Comparator<Evolutionary<Path>>{

	
	public static int TYPE = 0;
	
	public static int RANDOM = 0;
	public static int PORTAL = 1;
	public static int NPC = 2;
	public static int PORTAL2 = 3;
	

	public static double[] reward = new double[4];
	public static int[] used = new int[4];
	
	public static void setType() {
		ArrayList<Double> avg = new ArrayList<>();
		
		int maxUsed = -1;
		for (int i = 0; i < used.length; i++) {
			if (used[i] + EvolutionaryAgent.r.nextDouble() * 0.000001 > maxUsed) maxUsed = used[i];
		}

		for (int i = 0; i < reward.length; i++) {
			double exploitation =  (used[i] != 0) ? reward[i] / used[i] : Double.POSITIVE_INFINITY;
			double exploration = Math.sqrt(Math.log(used[i] / maxUsed));
			double value = exploitation + Math.sqrt(2) * exploration;
			avg.add(value);
		}
		double max = Collections.max(avg);
		int index = avg.indexOf(max);
		PathComparator.TYPE = index;
	}
	
	
	
	public static void sort(ArrayList<Evolutionary<Path>> population, PathComparator comp) {
		Collections.shuffle(population);
		Collections.sort(population, comp);
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
				d1 = p1.getPortalValue();
				d2 = p2.getPortalValue();
			} else if (TYPE == NPC) {
				d1 = p1.getNPCValue();
				d2 = p2.getNPCValue();
			} else if (TYPE == PORTAL2) {
				d1 = p1.getPortal2Value();
				d2 = p2.getPortal2Value();
			} 
			result = d2.compareTo(d1);
		}
		return result;
	}
	
	@Override
	public String toString() {
		String s = "";
		s += "PATHCOMP REWARD: " + Arrays.toString(PathComparator.reward) + '\n';
		s += "PATHCOMP USED: " + Arrays.toString(PathComparator.used)+ '\n';
		s += "PATHCOMP TYPE: " + PathComparator.TYPE;
		return s;
	}

}
