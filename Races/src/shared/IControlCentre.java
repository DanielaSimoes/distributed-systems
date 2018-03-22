package shared;

/**
 *
 * @author Daniela
 */
public interface IControlCentre {
    public void reportResults();
    public void proceedToPaddock();
    public void waitForNextRace();
    public void goWatchTheRace();
    public boolean haveIWon();
    public void relaxABit();
}
