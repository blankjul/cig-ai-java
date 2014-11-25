package emergence_RL;

import core.player.AbstractPlayer;

/**
 * This allows to evaluate the results in threads. The Agent must override these
 * methods that allows to be started external at the commandline.
 */
abstract public class AThreadablePlayer extends AbstractPlayer {

	abstract public void createFromString(String parameter);

	abstract public String printToString();

}
