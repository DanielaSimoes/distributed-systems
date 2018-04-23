package shared;

import GeneralRepository.Races;
import GeneralRepository.RacesProxy;
import entities.HorseJockey;
import entities.HorseJockeyState;
import entities.Broker;
import entities.BrokerState;

/**
 * This file contains the shared memory region Stable.
 * @author Daniela Simões, 76771
 */
public class Stable implements IStable {
    
    private boolean wakeEntertainTheGuests = false;
    
    private final RacesProxy races;
    
    public Stable(RacesProxy races){
        this.races = races;
    }
     
    /**
    *
    * Method to get the broker to announce the next race.
     * @param raceNumber
    */
    @Override
    public synchronized void summonHorsesToPaddock(int raceNumber){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        this.races.setAnnouncedNextRace(true, raceNumber);
        notifyAll();
    };
    
    /**
    *
    * Method to get the horses to proceed to stable.
     * @param raceNumber
     * @param horseID
     * @param horseStepSize
    */
    @Override
    public synchronized void proceedToStable(int raceNumber, int horseID, int horseStepSize){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);

        while(!((races.getAnnouncedNextRace(raceNumber) && this.races.getWakedHorsesToPaddock(raceNumber)!=races.getNRunningHorses(raceNumber) && races.horseHasBeenSelectedToRace(horseID, horseStepSize, raceNumber)) || wakeEntertainTheGuests)){
            try{
                wait();
            }catch (InterruptedException ex){
                    // do something in the future
            } 
            
            if(((HorseJockey)Thread.currentThread()).getCurrentRace()<Races.N_OF_RACES && races.horsesFinished(raceNumber) && races.hasMoreRaces()){
                ((HorseJockey)Thread.currentThread()).nextRace();
            }
        }
        
        if(!wakeEntertainTheGuests){
            this.races.addWakedHorsesToPaddock(raceNumber);
        }
    };
    
    /**
    *
    * Method to get the broker to entertain the guests - death state.
    */
    @Override
    public synchronized void entertainTheGuests(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);
        wakeEntertainTheGuests = true;
        notifyAll();
    }
}
