package interfaces.paddock;

import structures.vectorClock.VectorTimestamp;

/**
 *
 * @author Daniela
 */
public interface IBroker {
    public VectorTimestamp proceedToPaddock(VectorTimestamp vt);
    public VectorTimestamp proceedToStartLine(VectorTimestamp vt);
    public VectorTimestamp goCheckHorses(VectorTimestamp vt);
    public VectorTimestamp summonHorsesToPaddock(VectorTimestamp vt);
}
