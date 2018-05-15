/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.racingTrack;

import java.rmi.RemoteException;
import structures.vectorClock.VectorTimestamp;

/**
 *
 * @author Daniela
 */
public interface RacingTrackInterface {
    public VectorTimestamp startTheRace(VectorTimestamp vt) throws RemoteException;
    public VectorTimestamp proceedToStartLine(VectorTimestamp vt) throws RemoteException;
    public boolean hasFinishLineBeenCrossed(int horseId) throws RemoteException;
    public VectorTimestamp makeAMove(VectorTimestamp vt) throws RemoteException;
}
