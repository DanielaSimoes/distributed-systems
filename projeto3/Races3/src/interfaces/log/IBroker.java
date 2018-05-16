/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.log;

import java.rmi.RemoteException;
import structures.enumerates.BrokerState;

/**
 *
 * @author Daniela
 */
public interface IBroker {
    void setBrokerState(BrokerState state);
    public void finished() throws RemoteException;
}
