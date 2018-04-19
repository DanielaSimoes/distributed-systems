package shared;

/**
 * This file contains the Interface implemented by the shared memory region Betting Centre.
 * @author Daniela Simões, 76771
 */
public interface IBettingCentre {
    public void acceptTheBets();
    public void honourTheBets();
    public boolean areThereAnyWinners();
    public void placeABet();
    public void goCollectTheGains();
}
