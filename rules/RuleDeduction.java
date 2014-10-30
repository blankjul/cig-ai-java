package emergence_HR.rules;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import tools.Vector2d;
import core.game.Event;
import core.game.Observation;
import core.game.StateObservation;
import emergence_HR.ActionTimer;
import emergence_HR.rules.nodes.RuleNode;
import emergence_HR.rules.nodes.RuleNodeComparator;

/**
 * This class helps to find out the rules of a level. The procedure is as
 * follows: - create a priority queue with a heuristic to a destination - always
 * expand the node with the highest heuristic value - until we have a collision
 * and find properly a rule or not ;)
 * 
 */
public class RuleDeduction {

	/**
	 * This is the type of the rule that should be deducted. For example there
	 * should be found out if there is a rule if the avatar collides with a
	 * portal then it is AVATER_PORTAL and so on.
	 * 
	 */
	public enum RuleType {
		AVATAR_PORTAL, AVATAR_IMMOVABLE
	}

	public RuleDeduction() {
	}

	
	
	public void explore(RuleType rt, StateObservation stateObs,
			ActionTimer timer) {

		// create a queue where always the node with the lowest heuristic
		// distance is on the top
		final Queue<RuleNode> queue = new PriorityQueue<RuleNode>(11,
				new RuleNodeComparator());

		Vector2d v = null;
		
		// find how many destinations we have
		if (rt == RuleType.AVATAR_PORTAL) {
			ArrayList<Observation>[] list = stateObs.getPortalsPositions(stateObs.getAvatarPosition());
			// if we have no immovable objects there can not be rules
			if (list == null || list.length == 0) return;
			for (ArrayList<Observation> lObersation : list) {
				// all observation of this list have the same group
				// so just take the nearest that means the first!
				if (lObersation == null | lObersation.size() == 0) break;
				Observation obs = lObersation.get(0);
				v = obs.position;
				
			}
		}
		
		
		if (v == null) return;
		System.out.println(v);
		
		RuleNode root = new RuleNode(null, stateObs, v);
		queue.add(root);

		// while there are nodes to explore and there is time left continue
		while (// timer.isTimeLeft() &&
		!queue.isEmpty()) {

			printNodes(queue);
			
			final RuleNode node = queue.poll();

			if (node.stateObs.getAvatarPosition().equals(v) || node.stateObs.isGameOver()) {
				System.out.println("yEAHH got collision");
				return;
			}
			queue.addAll(node.getChildren());

		}

	}

	public void printNodes(Queue<RuleNode> queue) {
		final int MAX = 4;
		int i = 0;
		for (RuleNode n : queue) {
			System.out.println(n);
			if (i>=MAX) break;
			++i;
		}
		System.out.println("------- TOP  ---------------");
		RuleNode top = queue.peek();
		if (top != null) {
			System.out.println(top);
			System.out.println("EVENT");
			for (Event e : top.stateObs.getEventsHistory()) {
				System.out.printf("%s [%s,%s], ", e.gameStep, e.activeTypeId,
						e.passiveTypeId);
			}
			System.out.println();
		}
		System.out.println("size: " + queue.size());
		System.out.println("\n-----------------------------");

	}

}