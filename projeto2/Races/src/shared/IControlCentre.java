package shared;

/**
 * This file contains the Interface implemented by the shared memory region Control Centre.
 * @author Daniela Simões, 76771
 */
public interface IControlCentre {
    public void reportResults(int raceNumber);
    public void proceedToPaddock(int raceNumber);
    public void waitForNextRace(int raceNumber);
    public void goWatchTheRace(int raceNumber);
    public boolean haveIWon(int raceNumber);
    public void relaxABit();
}
