/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.paddock;

import structures.vectorClock.VectorTimestamp;

/**
 *
 * @author Daniela
 */
public interface IHorseJockey {
    public VectorTimestamp proceedToPaddock(VectorTimestamp vt);
    public VectorTimestamp proceedToStartLine(VectorTimestamp vt);
}
