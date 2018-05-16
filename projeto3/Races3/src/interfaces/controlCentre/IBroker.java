/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.controlCentre;

import java.rmi.RemoteException;
import structures.vectorClock.VectorTimestamp;

/**
 *
 * @author Daniela
 */
public interface IBroker {
    public VectorTimestamp reportResults(VectorTimestamp vt) throws RemoteException;
    public VectorTimestamp proceedToPaddock(VectorTimestamp vt) throws RemoteException;
    public VectorTimestamp waitForNextRace(VectorTimestamp vt) throws RemoteException;
    public VectorTimestamp goWatchTheRace(VectorTimestamp vt) throws RemoteException;
    public boolean haveIWon() throws RemoteException;
    public VectorTimestamp relaxABit(VectorTimestamp vt) throws RemoteException;
}
