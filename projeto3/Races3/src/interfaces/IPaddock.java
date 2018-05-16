package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This file contains the Interface implemented by the shared memory region Paddock.
 * @author Daniela Simes, 76771
 */
public interface IPaddock extends Remote{
    public void proceedToPaddock(int raceNumber)  throws RemoteException;
    public void proceedToStartLine(int raceNumber)  throws RemoteException;
    public void goCheckHorses(int raceNumber)  throws RemoteException;
    public void summonHorsesToPaddock(int raceNumber) throws RemoteException;
    public void signalShutdown() throws RemoteException;
}
