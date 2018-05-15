package interfaces.stable;

import structures.vectorClock.VectorTimestamp;

/**
 *
 * @author Daniela
 */
public interface IBroker {
    public VectorTimestamp summonHorsesToPaddock(VectorTimestamp vt);
    public VectorTimestamp proceedToStable(VectorTimestamp vt);
    public VectorTimestamp entertainTheGuests(VectorTimestamp vt);
}
