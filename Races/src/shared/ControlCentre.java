package shared;

import entities.Broker;
import entities.BrokerState;
import entities.HorseJockey;
import entities.HorseJockeyState;
import entities.Spectators;
import entities.SpectatorsState;

/**
 *
 * @author Daniela
 */
public class ControlCentre implements IControlCentre {
    
    private boolean wakeHorsesToPaddock = false, startTheRace = false, entertainTheGuests=false, reportResults=false, proceedToPaddock = false, goWatchTheRace=false, goCheckHorses = false;
    
    @Override
    public synchronized void summonHorsesToPaddock(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        
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
    public synchronized void startTheRace(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        
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
    public synchronized void entertainTheGuests(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);
        
        this.entertainTheGuests = true;
        notifyAll();
    };
    
    @Override
    public synchronized void reportResults(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        
        this.reportResults = true;
        notifyAll();
    };
    
    @Override
    public synchronized void proceedToPaddock(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        
        this.proceedToPaddock = true;
        notifyAll();
    };
    
    @Override
    public synchronized void waitForProceedToPaddock(){
        while(!this.proceedToPaddock){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        this.proceedToPaddock = false;
    };
    
    @Override
    public synchronized boolean waitForNextRace(){
       return this.proceedToPaddock;
    };
    
    @Override
    public synchronized void goWatchTheRace(){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
        this.goWatchTheRace = true;
    };
    
    @Override
    public synchronized void waitForGoWatchTheRace(){
         while(!this.goWatchTheRace){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        this.goWatchTheRace = false;
    };
    
    @Override
    public synchronized boolean haveIWon(){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
        return false;
    };
   
    
    @Override
    public synchronized void relaxABit(){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.CELEBRATING);
    };
}
