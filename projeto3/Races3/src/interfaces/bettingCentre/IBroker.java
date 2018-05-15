/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.bettingCentre;

import structures.vectorClock.VectorTimestamp;

/**
 *
 * @author Daniela
 */
public interface IBroker {
    public VectorTimestamp acceptTheBets(VectorTimestamp vt);
    public VectorTimestamp honourTheBets(VectorTimestamp vt);
    public boolean areThereAnyWinners();
    public VectorTimestamp placeABet(VectorTimestamp vt);
    public VectorTimestamp goCollectTheGains(VectorTimestamp vt);
}
