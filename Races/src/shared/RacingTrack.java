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
public class RacingTrack implements IRacingTrack {
    
    private boolean startTheRace = false, proceedToStartLine = false, makeAMove = false;
    
    @Override
    public synchronized void startTheRace(){
        this.startTheRace = true;
        notifyAll();
    };
    
    @Override
    public synchronized void waitForStartTheRace(){
        while(!this.startTheRace){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        this.startTheRace = false;
    };
    
    @Override
    public synchronized void proceedToStartLine(){
        this.proceedToStartLine = true;
    };
    
    @Override
    public synchronized void waitForProceedToStartLine(){
    while(!this.proceedToStartLine){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        this.proceedToStartLine = false;
    };
    
    @Override
    public synchronized boolean hasFinishLineBeenCrossed(){
        // will do something
        return false;
    
    };
    
    @Override
    public synchronized void makeAMove(){
        this.makeAMove = true;
        notifyAll();
    };
    
}
