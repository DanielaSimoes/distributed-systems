/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.controlCentre;

/**
 *
 * @author Daniela
 */
public interface IBroker {
    public void reportResults();
    public void proceedToPaddock();
    public void waitForNextRace();
    public void goWatchTheRace();
    public boolean haveIWon();
    public void relaxABit();
}
