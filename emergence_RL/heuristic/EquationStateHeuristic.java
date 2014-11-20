package emergence_RL.heuristic;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import ontology.Types.WINNER;
import tools.Vector2d;
import core.game.Observation;
import core.game.StateObservation;
import emergence_RL.GameResult;
import emergence_RL.tree.Tree;

public class EquationStateHeuristic extends AHeuristic{

	public double[] weights;

	public double fitness = Double.NEGATIVE_INFINITY;
	
	public ArrayList<Future<GameResult>> resultList = new ArrayList<Future<GameResult>>();
	
	public String createdOnGame = "";
	
	public static EquationStateHeuristic random() {
		double[] weights = new double[10];
		for (int i = 0; i < weights.length; i++) {
			Random r = new Random();
			double rangeMin = -100;
			double rangeMax = +100;
			double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
			weights[i] = randomValue;
		}
		return new EquationStateHeuristic(weights);
	}
	

	public String parameter() {
		String s = "";
		for (double d : weights) {
			s += String.valueOf(d) + ",";
		}
		s = s.substring(0, s.length()-1);
		return s;
	}
	
	@Override
	public String toString() {
		return "Created on game: " + createdOnGame + " " + parameter();
	}
	
	public EquationStateHeuristic(double[] weights) {
		this.weights = weights;
	}
	
	public EquationStateHeuristic(String createdOnGame, double[] weights) {
		this(weights);
		this.createdOnGame = createdOnGame;
	}

	
	@Override
	public double evaluateState(StateObservation stateObs) {

		if (stateObs.getGameWinner() == WINNER.PLAYER_WINS) {
			return Double.POSITIVE_INFINITY;
		} else if (stateObs.getGameWinner() == WINNER.PLAYER_LOSES) {
			 return Double.NEGATIVE_INFINITY;
		}
		
		double[] state = new double[1];
		state[0] = stateObs.getGameScore();
		
		double[] npc = npc(stateObs, "npc");
		double[] portals = npc(stateObs, "portals");
		double[] resource = npc(stateObs, "resource");

		double[] value = concatAll(state,npc,portals,resource);

		double score = 0;
		for (int i = 0; i < value.length; i++) {
			score += value[i] * weights[i];
		}
		return score;

	}

	public static double distance(Vector2d from, Vector2d to) {
		return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
	}

	
	
	private double[] npc(StateObservation stateObs, String type) {
		double[] eq = new double[3];
		Vector2d avatarPosition = stateObs.getAvatarPosition();
		ArrayList<Observation>[] positions = null;
		
		if (type.equals("npc")) {
			positions = stateObs.getNPCPositions(avatarPosition);
		} else if (type.equals("portals")) {
			positions = stateObs.getPortalsPositions(avatarPosition);
		} else if (type.equals("resource")) {
			positions = stateObs.getResourcesPositions(avatarPosition);
		}

		if (positions == null)
			return eq;

		for (int i = 0; i < positions.length && i < 3; i++) {
			ArrayList<Observation> listObs = positions[i];
			if (listObs == null || listObs.isEmpty())
				continue;
			Observation obs = listObs.get(0);
			eq[i] = distance(stateObs.getAvatarPosition(), obs.position);
		}
		return eq;
	}

	public double[] concatAll(double[]... array) {
        int len = 0;
        for (final double[] job : array) {
            len += job.length;
        }

        final double[] result = new double[len];

        int currentPos = 0;
        for (final double[] job : array) {
            System.arraycopy(job, 0, result, currentPos, job.length);
            currentPos += job.length;
        }

        return result;
    }


	
	public double getResult() {
		int size = resultList.size();
		double score = 0;
		double wins = 0;
		for (Future<GameResult> f : resultList) {
			GameResult g;
			try {
				g = f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return -100;
			} catch (ExecutionException e) {
				e.printStackTrace();
				return -100;
			}
			score += g.getScore();
			wins += g.getWin();
		}
		score /= size;
		wins /= size;
		double value = wins * 100 + score;
		return value;
	}
	
	
	public static ArrayList<AHeuristic> create(Tree tree) {
		ArrayList<AHeuristic> pool = new ArrayList<AHeuristic>();
		pool.add(new EquationStateHeuristic("camelRace/eggomania", new double[] {71.51606955238063,-0.10874248901326666,1.46935755801519,58.91949024357237,-46.09021025115321,-57.43379973569722,57.57362881912201,-73.6456264953129,-31.50978515374345,-52.41586298782184}));
		//pool.add(new EquationStateHeuristic("frogs", new double[] {83.56525340105779,-94.18003693788877,63.8497743799621,52.91407845744166,-89.16201858673986,50.898113590684744,-59.55816967825811,41.28391268668591,-70.88223625353956,-17.469607503662886 }));
		//pool.add(new EquationStateHeuristic("aliens/butterflies/missilecommand", new double[] {-40.62327505720693,-58.69258914953923,-49.606898527438204,-81.32390879388393,71.43675019986114,-61.35483540585036,-61.11143207906202,-87.16329141011143,-62.109108312834024,69.37755076132808 }));
		return pool;
	}


}
