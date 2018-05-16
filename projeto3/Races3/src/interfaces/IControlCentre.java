package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This file contains the Interface implemented by the shared memory region Control Centre.
 * @author Daniela Simes, 76771
 */
public interface IControlCentre extends Remote{
    public void reportResults(int raceNumber);
    public void proceedToPaddock(int raceNumber);
    public void waitForNextRace(int raceNumber);
    public void goWatchTheRace(int raceNumber);
    public boolean haveIWon(int raceNumber, int spectatorId);
    public void relaxABit();
    public void signalShutdown() throws RemoteException;
}
