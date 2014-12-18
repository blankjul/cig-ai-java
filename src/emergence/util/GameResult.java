package emergence.util;



/**
 * This is the result of a game simulation. This is needed to have a callback
 * object for multithreading. The attributes are the output, game, level, winner
 * and score.
 */
public class GameResult {

	private String output = "NO_OUTPUT";
	private String game = "UNKNOWN";
	private int level = -1;
	private int win = -1;
	private double score = -1;
	private String timesteps = "NO TIMESTEPS";
	private String parameterAgent = "NO PARAMETER AGENT";

	public GameResult() {
	}

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

	public String toCSVString(){
		String csv = "";
		//csv += Configuration.dateFormat.format(new Date()) + ",";
		csv += game + ",";
		csv += level + ",";
		csv += win + ",";
		csv += (int) score + ",";
		csv += timesteps + ",";
		csv += parameterAgent;
		return csv;
	}
	
	@Override
	public String toString() {
		return String.format("Game:%s, Level:%d, Winner:%d, Score:%f",
				this.game, this.level, this.win, this.score);
	}

	/*
	 * Getter and Setter Methods
	 */

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
		parseString(output);
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}
}