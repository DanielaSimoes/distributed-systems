package shared;

import GeneralRepository.Races;
import GeneralRepository.RacesProxy;
import entities.Broker;
import entities.BrokerState;
import entities.HorseJockey;
import entities.HorseJockeyState;
import entities.Spectators;
import entities.SpectatorsState;

/**
 * This file contains the shared memory region Control Centre.
 * @author Daniela Simões, 76771
 */
public class ControlCentre implements IControlCentre {
    
    private final RacesProxy races;
    
    public ControlCentre(RacesProxy races){
        this.races = races;
    }
    
    /**
    *
    * Method to report the bet results to the spectators.
     * @param raceNumber
    */
    @Override
    public synchronized void reportResults(int raceNumber){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        
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
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        
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
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WAITING_FOR_A_RACE_TO_START);
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
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
        
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
     * @return 
    */
    @Override
    public synchronized boolean haveIWon(int raceNumber){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
        return this.races.haveIWon(raceNumber);
    };
   
    /**
    *
    * Method to get the spectator to relax a bit - death state.
    */
    @Override
    public synchronized void relaxABit(){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.CELEBRATING);
    };
}
