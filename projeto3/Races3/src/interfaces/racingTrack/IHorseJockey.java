package interfaces.racingTrack;

import structures.vectorClock.VectorTimestamp;

/**
 *
 * @author Daniela
 */
public interface IHorseJockey {
    public VectorTimestamp proceedToStartLine(VectorTimestamp vt);
    public VectorTimestamp makeAMove(VectorTimestamp vt);
    public boolean hasFinishLineBeenCrossed(int id);
}
