package shared;

/**
 * This file contains the Interface implemented by the shared memory region Paddock.
 * @author Daniela Simões, 76771
 */
public interface IPaddock {
    public void proceedToPaddock(int raceNumber);
    public void proceedToStartLine(int raceNumber);
    public void goCheckHorses(int raceNumber);
    public void summonHorsesToPaddock(int raceNumber);
}
