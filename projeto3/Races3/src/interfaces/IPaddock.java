package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This file contains the Interface implemented by the shared memory region Paddock.
 * @author Daniela Simões, 76771
 */
public interface IPaddock extends Remote{
    public void proceedToPaddock(int raceNumber);
    public void proceedToStartLine(int raceNumber);
    public void goCheckHorses(int raceNumber);
    public void summonHorsesToPaddock(int raceNumber);
    public void signalShutdown() throws RemoteException;
}
