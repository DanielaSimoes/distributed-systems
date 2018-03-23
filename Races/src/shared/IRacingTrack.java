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
public interface IRacingTrack {
    public void startTheRace();
    public void proceedToStartLine();
    public boolean hasFinishLineBeenCrossed(int horseId);
    public void makeAMove();
}
