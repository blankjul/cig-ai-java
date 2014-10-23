package controllers.cig;

import tools.ElapsedCpuTimer;

/**
 * This class provides the timing of the actions. Every possible controller is
 * going to have a loop based algorithm. To make sure that we are not longer
 * calculating as 40 milliseconds this timer helps by stopping the average time
 * of the iteration.
 * 
 */
public class ActionTimer {

	// variable for defining a safety time limit
	public int timeRemainingLimit = 3;

	// multiplicator of the average time
	public double timeAvgMultiplicator = 1.4;

	// accumulated time
	protected double acumTime;

	// number of iterations
	protected int numIters = 0;

	// timer for the start and stop method.
	protected ElapsedCpuTimer tmpTimer;

	// timer for the act method. is need for the remaining time
	protected ElapsedCpuTimer actTimer;

	/**
	 * This is the constructor for the Timer object. The elapsedTimer that is
	 * given to the act method of the controller is needed to get the remaining
	 * time. First all variable are set by using the reset() method.
	 * 
	 * @param elapsedTimer
	 */
	ActionTimer(ElapsedCpuTimer elapsedTimer) {
		tmpTimer = null;
		actTimer = elapsedTimer;
		acumTime = 0;
		numIters = 0;
	}

	void start() {
		tmpTimer = new ElapsedCpuTimer();
	}

	void stop() {
		++this.numIters;
		this.acumTime += (this.tmpTimer.elapsedMillis());
	}

	/**
	 * Get the average time of the last iteration.
	 * 
	 * @return average time or zero if there were no iterations.
	 */
	double getAVG() {
		return (numIters != 0) ? acumTime / (double) numIters : 0;
	}

	/**
	 * @return remaining time for all further iteration.
	 */
	double getRemaining() {
		return actTimer.remainingTimeMillis();
	}

	/**
	 * Checks if there is enough time left for the next iteration by looking at
	 * the average of all last iteration and by using a safety time limit.
	 * 
	 * @return if there is enough time for a further iteration.
	 */
	boolean isTimeLeft() {
		return getRemaining() >= timeAvgMultiplicator * getAVG()
				&& getRemaining() > timeRemainingLimit;
	}

	/**
	 * Create a String with information that are relevant for the action timing.
	 * 
	 * @return string with the status.
	 */
	String status() {
		return "Remaining Time: " + getRemaining() + " | Iterations: "
				+ this.numIters + " | Average Time: " + getAVG();
	}

}
