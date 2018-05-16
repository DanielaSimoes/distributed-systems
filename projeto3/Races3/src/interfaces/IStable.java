package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This file contains the Interface implemented by the shared memory region Stable.
 * @author Daniela Simões, 76771
 */
public interface IStable extends Remote{
    public void summonHorsesToPaddock(int raceNumber);
    public int proceedToStable(int raceNumber, int horseID, int horseStepSize);
    public void entertainTheGuests();
    public void signalShutdown() throws RemoteException;
}
