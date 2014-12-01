package emergence_NI.helper;

import java.util.ArrayList;
import java.util.Queue;

import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;
import emergence_NI.tree.Node;

public class LevelInfo {

	

	
	/*
	 * Print methods to look for the level while debugging
	 */

	public static void print(StateObservation stateObs) {
		Vector2d avatarPosition = stateObs.getAvatarPosition();

		System.out.println("---------------------");
		System.out.println("ME");
		System.out.println("---------------------");
		System.out.printf("[%s,%s], ", avatarPosition.x, avatarPosition.y);
		System.out.println("");

		System.out.println("---------------------");
		System.out.println("NPC");
		System.out.println("---------------------");
		LevelInfo.printList(stateObs.getNPCPositions(avatarPosition));

		System.out.println("---------------------");
		System.out.println("Portals");
		System.out.println("---------------------");
		LevelInfo.printList(stateObs.getPortalsPositions(avatarPosition));

		System.out.println("---------------------");
		System.out.println("Movable");
		System.out.println("---------------------");
		LevelInfo.printList(stateObs.getMovablePositions(avatarPosition));

		
		System.out.println("---------------------");
		System.out.println("Immovable");
		System.out.println("---------------------");
		LevelInfo.printList(stateObs.getImmovablePositions(avatarPosition));

		System.out.println("---------------------");
		System.out.println("Resources");
		System.out.println("---------------------");
		LevelInfo.printList(stateObs.getResourcesPositions(avatarPosition));
		
		

	}

	public static void printList(ArrayList<Observation>[] list) {
		// if we have no immovable objects there can not be rules
		if (list == null || list.length == 0)
			return;

		for (int i = 0; i < list.length; i++) {
			ArrayList<Observation> lObs = list[i];
			// all observation of this list have the same group
			// so just take the nearest that means the first!
			if (!lObs.isEmpty())
				System.out.print("Category: " + lObs.get(0).itype + " -> ");
			for (int j = 0; j < lObs.size(); j++) {
				Observation obs = lObs.get(j);
				System.out.printf("[%s,%s], ", obs.position.x, obs.position.y);
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void printNodes(Queue<Node> queue) {
		final int MAX = 4;
		int i = 0;
		for (Node n : queue) {
			System.out.println(n);
			if (i >= MAX)
				break;
			++i;
		}
		System.out.println("size: " + queue.size());
		System.out.println("\n-----------------------------");

	}

}
