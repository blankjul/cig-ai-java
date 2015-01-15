package emergence.util;

/**
 * This is the result of a game simulation. This is needed to have a callback
 * object for multithreading. The attributes are the output, game, level, winner
 * and score.
 */
public class GameResult {

	/** the console output of the level */
	private String output = "NO_OUTPUT";

	/** the played game */
	private String game = "UNKNOWN";

	/** the played level */
	private int level = -1;

	/** win or lose */
	private int win = -1;

	/** score of the game */
	private double score = -1;

	/** timesteps of the game */
	private String timesteps = "NO TIMESTEPS";

	/** parameters submitted from the executed agent */
	private String parameterAgent = "NO PARAMETER AGENT";

	/**
	 * Empty constructor.
	 */
	public GameResult() {
	}

	/**
	 * Creates a game result.
	 * 
	 * @param output
	 * @param game
	 * @param level
	 * @param win
	 * @param score
	 * @param timesteps
	 * @param parameterAgent
	 */
	public GameResult(String output, String game, int level, int win,
			double score, String timesteps, String parameterAgent) {
		super();
		this.output = output;
		this.game = game;
		this.level = level;
		this.win = win;
		this.score = score;
		this.timesteps = timesteps;
		this.parameterAgent = parameterAgent;
	}

	/**
	 * Parses a String (in most cases the console output) to get the score and
	 * information about win or loss.
	 * 
	 * @param s
	 */
	public void parseString(String s) {
		if (!s.contains("Result"))
			return;
		s = s.substring(s.indexOf("Result"));
		String[] l = s.split(",");
		String strWinner = l[0].substring(l[0].length() - 1);
		String strScore = l[1].trim().split(":")[1];
		this.win = (strWinner.equals("1")) ? 1 : 0;
		this.score = Double.parseDouble(strScore);
	}

	/**
	 * Generates a String according to the attributes of this game result. It is
	 * used in csv output.
	 * 
	 * @return
	 */
	public String toCSVString() {
		String csv = "";
		// csv += Configuration.dateFormat.format(new Date()) + ",";
		csv += game + ",";
		csv += level + ",";
		csv += win + ",";
		csv += (int) score + ",";
		csv += timesteps + ",";
		csv += parameterAgent;
		return csv;
	}

	/**
	 * Generates a String according to the attributes of this game result
	 */
	@Override
	public String toString() {
		return String.format("Game:%s, Level:%d, Winner:%d, Score:%f",
				this.game, this.level, this.win, this.score);
	}

	/*
	 * Getter and Setter Methods
	 */

	/**
	 * Returns the output.
	 * 
	 * @return
	 */
	public String getOutput() {
		return output;
	}

	/**
	 * Sets the output.
	 * 
	 * @param output
	 */
	public void setOutput(String output) {
		this.output = output;
		parseString(output);
	}

	/**
	 * Returns the game.
	 * 
	 * @return
	 */
	public String getGame() {
		return game;
	}

	/**
	 * Sets the game.
	 * 
	 * @param game
	 */
	public void setGame(String game) {
		this.game = game;
	}

	/**
	 * Returns the level.
	 * 
	 * @return
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Sets the level.
	 * 
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Returns the score.
	 * 
	 * @return
	 */
	public double getScore() {
		return score;
	}

	/**
	 * Set the score.
	 * 
	 * @param score
	 */
	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * Get the win/loss.
	 * 
	 * @return
	 */
	public int getWin() {
		return win;
	}

	/**
	 * Set the win/loss.
	 * 
	 * @param win
	 */
	public void setWin(int win) {
		this.win = win;
	}
}