/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.racingTrack;

import structures.vectorClock.VectorTimestamp;

/**
 *
 * @author Daniela
 */
public interface IBroker {
    public VectorTimestamp startTheRace(VectorTimestamp vt);
    public VectorTimestamp proceedToStartLine(VectorTimestamp vt);
    public boolean hasFinishLineBeenCrossed(int horseId);
    public VectorTimestamp makeAMove(VectorTimestamp vt);
}
