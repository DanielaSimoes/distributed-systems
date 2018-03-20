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
    
    private boolean wakeHorsesToPaddock = false, wakeEntertainTheGuests = false;
     
    @Override
    public synchronized void summonHorsesToPaddock(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        
        this.wakeHorsesToPaddock = true;
        notifyAll();
    };
    
    
    @Override
    public synchronized void proceedToStable(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
       
        while(!this.wakeHorsesToPaddock || !this.wakeEntertainTheGuests){
            try{
                wait();
            }catch (InterruptedException ex){
                    // do something in the future
            } 
        }
    };
    
    public synchronized void setEntertainTheGuests(boolean set){
        this.wakeEntertainTheGuests = set;
    }
}
