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
public interface ISpectators {
    public VectorTimestamp waitForNextRace() throws RemoteException;
    public VectorTimestamp goWatchTheRace() throws RemoteException;
    public VectorTimestamp relaxABit() throws RemoteException;
}
