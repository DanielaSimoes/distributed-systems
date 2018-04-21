package shared;

/**
 * This file contains the Interface implemented by the shared memory region Stable.
 * @author Daniela Simões, 76771
 */
public interface IStable {
    public void summonHorsesToPaddock(int raceNumber);
    public void proceedToStable(int raceNumber, int horseID, int horseStepSize);
    public void entertainTheGuests();
}
