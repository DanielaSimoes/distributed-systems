package shared;

import GeneralRepository.Races;
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
    private int nSpectatorsArrivedAtPaddock = 0, nHorseJockeyLeftThePadock = 0;
    
    @Override
    public synchronized void proceedToPaddock(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        
        while(nSpectatorsArrivedAtPaddock!=Races.N_OF_SPECTATORS){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
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
        
        if(++nHorseJockeyLeftThePadock==Races.N_OF_HORSES){
            notifyAll();
        }
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
        //this.goCheckHorses = true;
        
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.APPRAISING_THE_HORSES);
        
        if(++this.nSpectatorsArrivedAtPaddock==Races.N_OF_SPECTATORS){
            notifyAll();
        }
        
        while(nHorseJockeyLeftThePadock!=Races.N_OF_HORSES){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }        
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
