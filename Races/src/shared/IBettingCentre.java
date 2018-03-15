package shared;

/**
 *
 * @author Daniela
 */
public interface IBettingCentre {
    public void acceptTheBets();
    public void honourTheBets();
    public boolean areThereAnyWinners();
    public void placeABet();
    public void goCollectTheGains();
}
