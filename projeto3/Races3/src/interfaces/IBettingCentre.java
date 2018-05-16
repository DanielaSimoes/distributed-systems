package interfaces;

import generalRepository.races.Bet;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This file contains the Interface implemented by the shared memory region Betting Centre.
 * @author Daniela Simes, 76771
 */
public interface IBettingCentre extends Remote{
    public void acceptTheBets(int raceNumber) throws RemoteException;
    public void honourTheBets(int raceNumber) throws RemoteException;
    public boolean areThereAnyWinners(int raceNumber) throws RemoteException;
    public Bet placeABet(int raceNumber, int spectatorId, int initialBet, int moneyToBet) throws RemoteException;
    public int goCollectTheGains(int raceNumber, int spectatorId) throws RemoteException;
    public void signalShutdown() throws RemoteException;
}
