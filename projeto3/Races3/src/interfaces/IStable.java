package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This file contains the Interface implemented by the shared memory region Stable.
 * @author Daniela Simes, 76771
 */
public interface IStable extends Remote{
    public void summonHorsesToPaddock(int raceNumber)  throws RemoteException;
    public int proceedToStable(int raceNumber, int horseID, int horseStepSize)  throws RemoteException;
    public void entertainTheGuests()  throws RemoteException;
    public void signalShutdown() throws RemoteException;
}
