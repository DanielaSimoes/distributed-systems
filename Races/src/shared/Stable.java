package shared;

import entities.HorseJockey;
import entities.HorseJockeyState;
import entities.Broker;
import entities.BrokerState;

/**
 *
 * @author Daniela
 */
public class Stable implements IStable {
    
    private boolean wakeHorsesToPaddock = false, proceedToStable = false, wakeEntertainTheGuests = false;
     
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
    public synchronized void proceedToStable(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
       
        while(!this.proceedToStable || !this.wakeEntertainTheGuests){
            try{
                wait();
            }catch (InterruptedException ex){
                    // do something in the future
            } 
        }
        
        this.proceedToStable = true;
    };
    
    public synchronized void setWaitEntertainTheGuests(boolean set){
        this.wakeEntertainTheGuests = set;
    }
}
