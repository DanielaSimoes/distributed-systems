package shared;

import GeneralRepository.Races;
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
    private final Races races = Races.getInstace();
     
    @Override
    public synchronized void summonHorsesToPaddock(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        races.newRace();
        this.wakeHorsesToPaddock = true;
        notifyAll();
    };
    
    
    @Override
    public synchronized void proceedToStable(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);

        while(((this.wakeHorsesToPaddock && races.getRace().horseHasBeenSelectedToRace((HorseJockey)Thread.currentThread()))|| this.wakeEntertainTheGuests) == false){
            try{
                wait();
            }catch (InterruptedException ex){
                    // do something in the future
            } 
        }
        System.out.println("Out of proceedToStable");
    };
    
    public synchronized void entertainTheGuests(){
        this.wakeEntertainTheGuests = true;
    }
}
