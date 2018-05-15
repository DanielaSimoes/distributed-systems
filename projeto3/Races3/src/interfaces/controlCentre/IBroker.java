/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.controlCentre;

import structures.vectorClock.VectorTimestamp;

/**
 *
 * @author Daniela
 */
public interface IBroker {
    public VectorTimestamp reportResults(VectorTimestamp vt);
    public VectorTimestamp proceedToPaddock(VectorTimestamp vt);
    public VectorTimestamp waitForNextRace(VectorTimestamp vt);
    public VectorTimestamp goWatchTheRace(VectorTimestamp vt);
    public boolean haveIWon();
    public VectorTimestamp relaxABit(VectorTimestamp vt);
}
