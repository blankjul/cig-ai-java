package emergence.strategy;

import java.util.Set;

import ontology.Types.ACTIONS;
import core.game.StateObservation;
import emergence.heuristics.DistanceHeuristic;
import emergence.strategy.astar.AStar;
import emergence.targets.ATarget;
import emergence.targets.TargetFactory;
import emergence.util.ActionTimer;
import emergence.util.Helper;

public class ExplorerStrategy extends AStrategy {
	
	
	private ATarget currentTarget = null;
	
	private Set<ATarget> targets; 
	
	private AStar astar;
	
	
	public ExplorerStrategy(StateObservation stateObs,ActionTimer timer) {
		super(stateObs, timer);
		targets = TargetFactory.getAllTargets(stateObs);
	}


	@Override
	public void expand() {
		if (!targets.isEmpty()) {
			currentTarget = Helper.getRandomEntry(targets);
			targets.remove(currentTarget);
			astar = new AStar(stateObs, new DistanceHeuristic(currentTarget), 15);
			
			while (timer.isTimeLeft()) {
				
				boolean next = astar.expand() != null;
				if (targets == null || targets.isEmpty()) targets = TargetFactory.getAllTargets(stateObs);
				
				if (currentTarget == null || !next) {
					currentTarget = Helper.getRandomEntry(targets);
					targets.remove(currentTarget);
					astar = new AStar(stateObs, new DistanceHeuristic(currentTarget));
				}
				timer.addIteration();
			} 
		}
		return;
	}

	@Override
	public ACTIONS act() {
		return ACTIONS.ACTION_NIL;
	}
	

}
