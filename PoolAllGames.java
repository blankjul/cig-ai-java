import java.util.concurrent.ExecutionException;



public class PoolAllGames {



	public static void main(String[] args) throws InterruptedException,
			ExecutionException {

		// compile the classes
		String out = Compile.start();
		System.out.println(out);
		System.out.println("STARTING COMPETITION...");
		

		for (String strGame : Configuration.allGames) {
			System.out.println("------------------------");
			System.out.println(strGame);
			System.out.println("------------------------");
			PoolOneGame.playOneGame(strGame);
			
		}

		Configuration.SCHEDULER.shutdown();
	}


}
