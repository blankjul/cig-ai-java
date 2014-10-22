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

	// accumulated time
	protected double acumTime;

	// number of iterations
	protected int numIters = 0;

	// timer for the start and stop method.
	protected ElapsedCpuTimer tmpTimer;

	// timer for the act method. is need for the remaining time
	protected ElapsedCpuTimer actTimer;

	// variable for defining a safety time limit
	protected int TIME_REMAINING_LIMIT = 3;

	// multiplicator of the average time
	protected double TIME_AVG_MULTIPLICATOR = 1.4;

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
		reset();
	}

	void start() {
		tmpTimer = new ElapsedCpuTimer();
	}

	void stop() {
		++this.numIters;
		this.acumTime += (this.tmpTimer.elapsedMillis());
	}

	void reset() {
		acumTime = 0;
		numIters = 0;
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
		return getRemaining() >= TIME_AVG_MULTIPLICATOR * getAVG()
				&& getRemaining() > TIME_REMAINING_LIMIT;
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
