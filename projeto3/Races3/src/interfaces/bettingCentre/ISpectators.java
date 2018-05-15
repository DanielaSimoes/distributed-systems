/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.bettingCentre;

/**
 *
 * @author Daniela
 */
public interface ISpectators {
    public void acceptTheBets();
    public void honourTheBets();
    public boolean areThereAnyWinners();
    public void placeABet();
    public void goCollectTheGains();
}
