package emergence_HR.strategy;

import java.util.ArrayList;

import emergence_HR.heuristics.AHeuristic;
import emergence_HR.heuristics.EquationStateHeuristic;
import emergence_HR.heuristics.SimpleStateHeuristic;
import emergence_HR.heuristics.WinScoreHeuristic;
import emergence_HR.tree.Tree;

/**
 * This class is for the administration issue for the heuristics. It's a
 * singleton class!
 * 
 */
public class EnsembleStrategy {

	// all the different strategies
	public ArrayList<AStrategy> pool = new ArrayList<AStrategy>();

	// index that should be expanded on this calculation
	private int index = 0;

	// the tree that is used for iteration
	public Tree tree;

	// private constructor for singleton pattern
	public EnsembleStrategy(Tree tree) {
		this.tree = tree;
		init();
	}

	public void init() {
		pool.clear();
		
		pool.add(new AStarStrategy(tree, new EquationStateHeuristic("camelRace/eggomania", new double[] {71.51606955238063,-0.10874248901326666,1.46935755801519,58.91949024357237,-46.09021025115321,-57.43379973569722,57.57362881912201,-73.6456264953129,-31.50978515374345,-52.41586298782184})));
		pool.add(new AStarStrategy(tree, new EquationStateHeuristic("frogs", new double[] {83.56525340105779,-94.18003693788877,63.8497743799621,52.91407845744166,-89.16201858673986,50.898113590684744,-59.55816967825811,41.28391268668591,-70.88223625353956,-17.469607503662886 })));;
		pool.add(new AStarStrategy(tree, new EquationStateHeuristic("aliens/butterflies/missilecommand", new double[] {-40.62327505720693,-58.69258914953923,-49.606898527438204,-81.32390879388393,71.43675019986114,-61.35483540585036,-61.11143207906202,-87.16329141011143,-62.109108312834024,69.37755076132808 })));;
		
		//pool.add(new GreedyStrategy(tree, new SimpleStateHeuristic()));
		//pool.add(new AStarStrategy(tree, new WinScoreHeuristic()));
		
		
		//pool.add(new GreedyStrategy(tree, new EquationStateHeuristic("frogs", new double[] {13.447649010117232,-86.26394049458989,24.872998953340144,20.684078657065854,-71.11835447411003,-39.422633928274806,39.33397998875313,4.175496181472639,-16.27181412540257,95.82123741853573})));
		//pool.add(new AStarStrategy(tree, new EquationStateHeuristic("aliens/butterflies/missilecommand", new double[] {64.84119238866205,-6.706116268770671,15.972320970002045,-86.00116924839656,1.5311167186565768,83.48853622908294,0.5650190254932141,-83.55457187644191,96.1475129439522,51.51009302853271 })));
		//pool.add(new AStarStrategy(tree, new EquationStateHeuristic("chase", new double[] {16.658090001664434,-20.267976603933732,-60.723581317867925,91.06066121999515,-15.278404502667257,-81.30387414647893,7.623760435087675,0.49671497912558493,-33.89029589538693,-72.94003091211758})));
		//pool.add(new AStarStrategy(tree, new EquationStateHeuristic("portals/camelRace/whackamole", new double[] {51.519453618756984,72.83520154311398,4.752517662280752,47.2395355703799,-66.80726754813959,-60.537732810548775,31.15981485494811,-60.11933350409342,-58.834420262681974,-13.678153196844917})));
		//pool.add(new GreedyStrategy(tree, new SimpleStateHeuristic()));
		
		//pool.add(new AStarStrategy(tree, new EquationStateHeuristic("infection", new double[] {74.23993150820812,-59.73089673490131,-81.78863187230746,-41.30505287771682,35.821764523860054,19.847928767495574,92.0980077240992,83.79253267828386,88.43900660948825,28.18378234029555})));
		//pool.add(new AStarStrategy(tree, new EquationStateHeuristic("camelRace/eggomania", new double[] {71.51606955238063,-0.10874248901326666,1.46935755801519,58.91949024357237,-46.09021025115321,-57.43379973569722,57.57362881912201,-73.6456264953129,-31.50978515374345,-52.41586298782184})));
		//pool.add(new AStarStrategy(tree, new SimpleStateHeuristic()));
		
		//pool.add(new GreedyStrategy(tree, new SimpleStateHeuristic()));
		//pool.add(new GreedyStrategy(tree, new EquationStateHeuristic("aliens/butterflies/missilecommand", new double[] {64.84119238866205,-6.706116268770671,15.972320970002045,-86.00116924839656,1.5311167186565768,83.48853622908294,0.5650190254932141,-83.55457187644191,96.1475129439522,51.51009302853271 })));
		//pool.add(new GreedyStrategy(tree, new EquationStateHeuristic("chase", new double[] {16.658090001664434,-20.267976603933732,-60.723581317867925,91.06066121999515,-15.278404502667257,-81.30387414647893,7.623760435087675,0.49671497912558493,-33.89029589538693,-72.94003091211758})));
		//pool.add(new GreedyStrategy(tree, new EquationStateHeuristic("portals/camelRace/whackamole", new double[] {51.519453618756984,72.83520154311398,4.752517662280752,47.2395355703799,-66.80726754813959,-60.537732810548775,31.15981485494811,-60.11933350409342,-58.834420262681974,-13.678153196844917})));
		//pool.add(new GreedyStrategy(tree, new EquationStateHeuristic("survivezombies", new double[] {22.411238839548915,-80.48724107173246,32.36064972660259,83.99446994734384,42.64481692521031,55.32231465005154,-55.487332283934144,10.71756675650282,47.99218288302822,-79.38576087271994})));
		//pool.add(new GreedyStrategy(tree, new EquationStateHeuristic("aliens", new double[] {-81.90769324283849,-39.44793318768141,-65.46251835204176,67.66230768594019,-41.746347038418286,11.276588777149186,63.79286972090824,-23.396258609779736,-80.43084681995711,-51.50159000365024})));
		//pool.add(new GreedyStrategy(tree, new EquationStateHeuristic("missilecommand", new double[] {-1.0037976253290992,-40.44595656848906,-66.09870008997743,-6.225927822559598,3.5959545316576396,70.75877306974164,-94.28605283580043,24.76636130575696,44.52040132954883,1.9185082486306868})));
		//pool.add(new GreedyStrategy(tree, new EquationStateHeuristic("infection", new double[] {38.331830027884905,-87.12981580936474,-92.62383672988983,-72.93181264227924,78.81581171217329,46.21459330915661,44.91890618108073,19.54609164731309,-92.89916982961444,40.05768389815569})));
		//pool.add(new GreedyStrategy(tree, new EquationStateHeuristic("camelRace", new double[] {84.26731297796707,16.29887855037188,-64.38427388236738,-31.05795690099862,-91.96391963452398,19.89712681104298,52.79859143087262,3.2347913321811177,23.237834219305682,80.49300115368334})));
		//pool.add(new GreedyStrategy(tree, new EquationStateHeuristic("aliens", new double[] {-81.90769324283849,-39.44793318768141,-65.46251835204176,67.66230768594019,-41.746347038418286,11.276588777149186,63.79286972090824,-23.396258609779736,-80.43084681995711,-51.50159000365024})));
		//pool.add(new GreedyStrategy(tree, new EquationStateHeuristic("whackamole", new double[] {45.083248773049604,78.68199130149321,15.085456834853389,34.82740684729259,15.526270735791087,-97.61559038900786,-67.22532746231556,-92.62850229726247,38.04763603380502,-56.80556820688971})));

	}

	public boolean expand() {
		AStrategy strategy = pool.get(index % pool.size());
		strategy.expand();
		++index;
		return true;
	}

	public AHeuristic top() {
		double maxScore = Double.NEGATIVE_INFINITY;
		AHeuristic heur = null;

		for (AStrategy strategy : pool) {
			if (strategy.heuristic.getScore() > maxScore) {
				maxScore = strategy.heuristic.getScore();
				heur = strategy.heuristic;
			}
		}
		return heur;
	}

	@Override
	public String toString() {
		String s = "---------------------------\n";
		s += "heuristic pool - size: " + pool.size() + "\n";
		s += "---------------------------\n";
		for (AStrategy strategy : pool) {
			AHeuristic heuristic = strategy.heuristic;
			s += String.format("heuristic:%s -> %s \n", heuristic,
					heuristic.getScore());
		}
		s += "iteration: " + index + "\n";
		s += "---------------------------\n";
		return s;
	}

}
