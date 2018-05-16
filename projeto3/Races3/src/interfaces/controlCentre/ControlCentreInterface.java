package interfaces.controlCentre;

import java.rmi.Remote;
import java.rmi.RemoteException;
import structures.vectorClock.VectorTimestamp;

/**
 *
 * @author Daniela
 */
public interface ControlCentreInterface extends Remote, IBroker, ISpectators{
}
