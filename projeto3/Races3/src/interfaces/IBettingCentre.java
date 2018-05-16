package interfaces;

import generalRepository.races.Bet;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This file contains the Interface implemented by the shared memory region Betting Centre.
 * @author Daniela Simões, 76771
 */
public interface IBettingCentre extends Remote{
    public void acceptTheBets(int raceNumber);
    public void honourTheBets(int raceNumber);
    public boolean areThereAnyWinners(int raceNumber);
    public Bet placeABet(int raceNumber, int spectatorId, int initialBet, int moneyToBet);
    public int goCollectTheGains(int raceNumber, int spectatorId);
    public void signalShutdown() throws RemoteException;
}
