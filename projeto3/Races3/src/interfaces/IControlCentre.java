package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This file contains the Interface implemented by the shared memory region Control Centre.
 * @author Daniela Simes, 76771
 */
public interface IControlCentre extends Remote{
    public void reportResults(int raceNumber) throws RemoteException;
    public void proceedToPaddock(int raceNumber) throws RemoteException;
    public void waitForNextRace(int raceNumber) throws RemoteException;
    public void goWatchTheRace(int raceNumber) throws RemoteException;
    public boolean haveIWon(int raceNumber, int spectatorId) throws RemoteException;
    public void relaxABit() throws RemoteException;
    public void signalShutdown() throws RemoteException;
}
