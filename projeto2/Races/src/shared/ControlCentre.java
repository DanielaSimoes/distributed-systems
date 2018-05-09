package shared;

import GeneralRepository.RacesStub;

/**
 * This file contains the shared memory region Control Centre.
 * @author Daniela Simões, 76771
 */
public class ControlCentre implements IControlCentre {
    
    private final RacesStub races;
    
    public ControlCentre(RacesStub races){
        this.races = races;
    }
    
    /**
    *
    * Method to report the bet results to the spectators.
     * @param raceNumber
    */
    @Override
    public synchronized void reportResults(int raceNumber){
        this.races.setReportResults(true, raceNumber);
        notifyAll();
    };
    
    /**
    *
    * Method to move the horses to paddock.
     * @param raceNumber
    */
    @Override
    public synchronized void proceedToPaddock(int raceNumber){
        this.races.addNHorsesInPaddock(raceNumber);
        
        if (this.races.allNHorsesInPaddock(raceNumber)){
            this.races.setProceedToPaddock(true, raceNumber);
            notifyAll();
        }
    };
  
    
    /**
    *
    * Method to wait for the next race.
     * @param raceNumber
    */
    @Override
    public synchronized void waitForNextRace(int raceNumber){
        while(!this.races.getProceedToPaddock(raceNumber) || this.races.horsesFinished(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    /**
    *
    * Method to send the spectators watch the race.
     * @param raceNumber
    */
    @Override
    public synchronized void goWatchTheRace(int raceNumber){
        while(!this.races.getReportResults(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    /**
    *
    * Method to verify if a spectator has won the bet.
     * @param raceNumber
     * @param spectatorId
     * @return 
    */
    @Override
    public synchronized boolean haveIWon(int raceNumber, int spectatorId){
        return this.races.haveIWon(raceNumber, spectatorId);
    };
   
    /**
    *
    * Method to get the spectator to relax a bit - death state.
    */
    @Override
    public synchronized void relaxABit(){};
}
