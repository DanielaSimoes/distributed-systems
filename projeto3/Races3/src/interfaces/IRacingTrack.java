package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This file contains the Interface implemented by the shared memory region Racing Track.
 * @author Daniela Simes, 76771
 */
public interface IRacingTrack extends Remote{
    public void startTheRace(int raceNumber) throws RemoteException;
    public void proceedToStartLine(int raceNumber, int horseId) throws RemoteException;
    public boolean hasFinishLineBeenCrossed(int horseId, int raceNumber) throws RemoteException;
    public void makeAMove(int raceNumber, int horseId) throws RemoteException;
    public void signalShutdown() throws RemoteException;
}
