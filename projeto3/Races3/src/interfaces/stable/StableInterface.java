package interfaces.stable;

import java.rmi.RemoteException;
import structures.vectorClock.VectorTimestamp;

/**
 *
 * @author Daniela
 */
public interface StableInterface {
    public VectorTimestamp summonHorsesToPaddock(VectorTimestamp vt) throws RemoteException;
    public VectorTimestamp proceedToStable(VectorTimestamp vt) throws RemoteException;
    public VectorTimestamp entertainTheGuests(VectorTimestamp vt) throws RemoteException;
}
