package shared;

import entities.Broker;
import entities.HorseJockey;
import entities.HorseJockeyState;
import entities.Spectators;
import entities.SpectatorsState;

/**
 *
 * @author Daniela
 */
public class Paddock implements IPaddock {
    
    private boolean wakeHorsesToPaddock = false, proceedToPaddock = false, proceedToStartLine = false, goCheckHorses = false;
    
    @Override
    public synchronized void summonHorsesToPaddock(){
        this.wakeHorsesToPaddock = true;
        notifyAll();
    };
    
    @Override
    public synchronized void waitForSummonHorsesToPaddock(){
        while(!this.wakeHorsesToPaddock){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        this.wakeHorsesToPaddock = false;  
    };
    
    
    @Override
    public synchronized void proceedToPaddock(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        
        this.proceedToPaddock = true;
        notifyAll();
    };
    
    @Override
    public synchronized void waitForProceedToPaddock(){
        while(!this.wakeHorsesToPaddock){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        this.wakeHorsesToPaddock = false; 
    };
    
    @Override
    public synchronized void proceedToStartLine(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
        
        this.proceedToStartLine = true;
        notifyAll();
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
    public synchronized void goCheckHorses(){
        this.goCheckHorses = true;
        
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.APPRAISING_THE_HORSES);
        
        notifyAll();
    };
    
    @Override
    public synchronized void waitForGoCheckHorses(){
        while(!this.goCheckHorses){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        this.goCheckHorses = false;  
    };
}
