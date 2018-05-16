/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.bettingCentre;

import java.rmi.RemoteException;
import structures.vectorClock.VectorTimestamp;

/**
 *
 * @author Daniela
 */
public interface IBroker {
    public VectorTimestamp acceptTheBets(VectorTimestamp vt) throws RemoteException;
    public VectorTimestamp honourTheBets(VectorTimestamp vt) throws RemoteException;
    public boolean areThereAnyWinners() throws RemoteException;
    public VectorTimestamp placeABet(VectorTimestamp vt) throws RemoteException;
    public VectorTimestamp goCollectTheGains(VectorTimestamp vt) throws RemoteException;
}
