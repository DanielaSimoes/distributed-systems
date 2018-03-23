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
    
    private final Races races = Races.getInstace();
     
    @Override
    public synchronized void summonHorsesToPaddock(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        this.races.setAnnuncedNextRace(true);
        // verify when the race ends
        //races.setAnnuncedNextRace(false);
        notifyAll();
    };
    
    
    @Override
    public synchronized void proceedToStable(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);

        while(!((races.getAnnuncedNextRace() && this.races.getWakedHorsesToPaddock()!=races.getNRunningHorses() && races.horseHasBeenSelectedToRace((HorseJockey)Thread.currentThread())) || races.getWakeEntertainTheGuests())){
            try{
                wait();
            }catch (InterruptedException ex){
                    // do something in the future
            } 
        }
        
        this.races.addWakedHorsesToPaddock();
    };
    
    @Override
    public synchronized void entertainTheGuests(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);
        races.setWakeEntertainTheGuests(true);
        notifyAll();
    }
}
