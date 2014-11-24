
public class PoolEvolution {

	/*
	public String CONTROLLER = "emergence_RL.Agent";
	public int NUM_LEVELS = 5;
	public int POOL_SIZE = 12;
	public int NUM_FITTEST = 4;
	public int NUM_GENERATION = 10;

	public ArrayList<Pair<UCTSearch, Integer>> pool = new ArrayList<Pair<UCTSearch, Integer>>();

	public ArrayList<String> games = new ArrayList<String>(Arrays.asList(Configuration.training));

	public Random r = new Random();

	
	private Integer getWins(ArrayList<String> games, UCTSearch settings) {
		Integer wins = playAllGames(games, settings);
		//Integer wins = r.nextInt();
		return wins;
	}
	
	
	public void start() {
		// print the time
		System.out.println(Configuration.dateFormat.format(new Date()));

		// initialize the pool
		for (int i = 0; i < POOL_SIZE; i++) {
			UCTSettings settings = UCTFactory.random(r);
			
			
			settings.weights = UCTFactory.randomWeights(r);
			settings.maxDepth = UCTFactory.randomMaxDepth(r);
			settings.gamma = UCTFactory.randomGamma(r);
			
			; j++) {

			
			// survival of the fittest
			Collections.sort(pool);
			
			// print the whole pool
			System.out.println("-----------------------------");
			System.out.println("GEN " + j);
			System.out.println("-----------------------------");
			for (Pair<UCTSettings, Integer> entry : pool) {
				String s= String.format("[wins:%s] %s", entry.getSecond(), entry.getFirst());
				System.out.println(s);
			}
			
			ArrayList<Pair<UCTSettings, Integer>> nextPool = new ArrayList<Pair<UCTSettings, Integer>>();
			for (int i = 0; i < NUM_FITTEST; i++) {
				UCTSettings s = pool.get(i).getFirst();
				Integer wins = getWins(games, s);
				nextPool.add(new Pair<UCTSettings, Integer>(s, wins));
			}

			while (nextPool.size() < POOL_SIZE) {

				UCTSettings selected = Helper.getRandomEntry(nextPool, r)
						.getFirst();
				
				UCTSettings entry = null;
				
				// mutate
				if (r.nextDouble() < 0.4) {
					
					entry = Evolution.mutate(r, selected);
					
					// crossover
				} else {

					// select a second one that is not the first!
					ArrayList<Pair<UCTSettings, Integer>> tmp = new ArrayList<Pair<UCTSettings, Integer>>();
					for (Pair<UCTSettings, Integer> pair : nextPool) {
						if (pair.getFirst() != selected) tmp.add(pair);
					}

					UCTSettings second = Helper.getRandomEntry(tmp, r)
							.getFirst();
					
					
					
					entry = Evolution.crossover(r, selected, second);

				}
				Integer wins = getWins(games, entry);
				nextPool.add(new Pair<UCTSettings, Integer>(entry, wins));
			}

			pool = nextPool;
		}

		System.out.println(Configuration.dateFormat.format(new Date()));
	}

	public Integer playAllGames(ArrayList<String> games, UCTSettings settings) {

		ArrayList<Future<GameResult>> results = new ArrayList<Future<GameResult>>();
		for (String game : games) {
			for (int j = 0; j < NUM_LEVELS; j++) {
				ExecCallable e = new ExecCallable(CONTROLLER, game, j,
						settings.toString());
				Future<GameResult> future = Configuration.SCHEDULER.submit(e);
				results.add(future);
			}
		}

		Integer wins = 0;
		for (Future<GameResult> f : results) {
			GameResult g;
			try {
				g = f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return -1;
			} catch (ExecutionException e) {
				e.printStackTrace();
				return -1;
			}
			wins += g.getWin();
		}

		return wins;

	}

	public static void main(String[] args) {

		PoolEvolution evo = new PoolEvolution();
		evo.start();

	}
	*/
	

}
