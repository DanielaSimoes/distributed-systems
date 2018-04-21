package shared;

/**
 * This file contains the Interface implemented by the shared memory region Betting Centre.
 * @author Daniela Simões, 76771
 */
public interface IBettingCentre {
    public void acceptTheBets(int raceNumber);
    public void honourTheBets(int raceNumber);
    public boolean areThereAnyWinners(int raceNumber);
    public void placeABet(int raceNumber);
    public void goCollectTheGains(int raceNumber);
}
