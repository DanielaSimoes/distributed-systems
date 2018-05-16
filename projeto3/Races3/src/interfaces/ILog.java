package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import structures.enumerates.BrokerState;
import structures.enumerates.HorseJockeyState;
import structures.enumerates.SpectatorsState;

/**
 * This file contains the log interface.
 * @author Daniela Simes, 76771
 */
public interface ILog extends Remote{
    public void setSpectatorState(int id, SpectatorsState state, int raceNumber)  throws RemoteException;
    public void setHorseJockeyState(int id, HorseJockeyState state, int raceNumber)  throws RemoteException;
    public void setBrokerState(BrokerState state, int raceNumber)  throws RemoteException;
    public void makeAMove(int raceNumber)  throws RemoteException;
    public void setSpectatorAmount(int spectatorId, int amount)  throws RemoteException;
    public void finished() throws RemoteException;
}
