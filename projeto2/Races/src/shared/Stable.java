package shared;

import GeneralRepository.RacesStub;
import settings.NodeSetts;

/**
 * This file contains the shared memory region Stable.
 * @author Daniela Simões, 76771
 */
public class Stable implements IStable {
    
    private boolean wakeEntertainTheGuests = false;
    
    private final RacesStub races;
    
    public Stable(RacesStub races){
        this.races = races;
    }
     
    /**
    *
    * Method to get the broker to announce the next race.
     * @param raceNumber
    */
    @Override
    public synchronized void summonHorsesToPaddock(int raceNumber){
        this.races.setAnnouncedNextRace(true, raceNumber);
        notifyAll();
    };
    
    /**
    *
    * Method to get the horses to proceed to stable.
     * @param raceNumber
     * @param horseID
     * @param horseStepSize
     * @return 
    */
    @Override
    public synchronized int proceedToStable(int raceNumber, int horseID, int horseStepSize){
        while(!((races.getAnnouncedNextRace(raceNumber) && this.races.getWakedHorsesToPaddock(raceNumber)!=races.getNRunningHorses(raceNumber) && races.horseHasBeenSelectedToRace(horseID, horseStepSize, raceNumber)) || wakeEntertainTheGuests)){
            try{
                wait();
            }catch (InterruptedException ex){
                    // do something in the future
            } 
            
            if(raceNumber<NodeSetts.N_OF_RACES && races.horsesFinished(raceNumber) && races.hasMoreRaces()){
                raceNumber++;
            }
        }
        
        if(!wakeEntertainTheGuests){
            this.races.addWakedHorsesToPaddock(raceNumber);
        }
        
        return raceNumber;
    };
    
    /**
    *
    * Method to get the broker to entertain the guests - death state.
    */
    @Override
    public synchronized void entertainTheGuests(){
        wakeEntertainTheGuests = true;
        notifyAll();
    }
}
