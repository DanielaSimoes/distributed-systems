package shared;

/**
 * This file contains the Interface implemented by the shared memory region Control Centre.
 * @author Daniela Simões, 76771
 */
public interface IControlCentre {
    public void reportResults();
    public void proceedToPaddock();
    public void waitForNextRace();
    public void goWatchTheRace();
    public boolean haveIWon();
    public void relaxABit();
}
