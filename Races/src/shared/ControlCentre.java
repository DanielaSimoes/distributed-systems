/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

/**
 *
 * @author Daniela
 */
public class ControlCentre implements IControlCentre {
    public void summonHorsesToPaddock(){};
    public void startTheRace(){};
    public void entertainTheGuests(){};
    public void reportResults(){};
    public void proceedToPaddock(){};
    public void waitingForARaceToStart(){};
    public void goWatchTheRace(){};
    public boolean haveIWon(){
        return false;
    };
    public boolean waitingForNextRace(){
        return false;
    };
    public void relaxABit(){};
}
