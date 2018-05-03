package shared;

import GeneralRepository.RacesProxy;

/**
 * This file contains the shared memory region Racing Track.
 * @author Daniela Simões, 76771
 */
public class RacingTrack implements IRacingTrack {
    
    private final RacesProxy races;
    
    public RacingTrack(RacesProxy races){
        this.races = races;
    }
    
    /**
    *
    * Method to start the race.
     * @param raceNumber
    */
    @Override
    public synchronized void startTheRace(int raceNumber){
        races.setStartTheRace(true, raceNumber);
        notifyAll();
        
        while(!races.horsesFinished(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        races.setStartTheRace(false, raceNumber);
    };
    
    /**
    *
    * Method to get the horses to proceed to the start line.
     * @param raceNumber
     * @param horseId
    */
    @Override
    public synchronized void proceedToStartLine(int raceNumber, int horseId){
        while(!(races.getStartTheRace(raceNumber) || !this.races.nextMovingHorse(horseId, raceNumber))){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        notifyAll();
    };
    
    /**
    *
    * Method to verify if a given horse crossed the finish line.
    * @param horseId The HorseJockey ID.
     * @param raceNumber
     * @return 
    */
    @Override
    public synchronized boolean hasFinishLineBeenCrossed(int horseId, int raceNumber){  
        return races.horseFinished(horseId, raceNumber);
    };
    
    /**
    *
    * Method to make a move - an iteration in the racing track.
     * @param raceNumber
     * @param horseId
    */
    @Override
    public synchronized void makeAMove(int raceNumber, int horseId){
        while(!this.races.nextMovingHorse(horseId, raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        races.makeAMove(horseId, raceNumber);
        
        notifyAll();
    };   
}
