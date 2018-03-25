package shared;

import GeneralRepository.Races;
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
    
    private Races races = Races.getInstace();
    
    /**
    *
    * Method to report the bet results to the spectators.
    */
    @Override
    public synchronized void reportResults(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        
        this.races.setReportResults(true);
        notifyAll();
    };
    
    /**
    *
    * Method to move the horses to paddock.
    */
    @Override
    public synchronized void proceedToPaddock(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        
        this.races.addNHorsesInPaddock();
        
        if (this.races.allNHorsesInPaddock()){
            this.races.setProceedToPaddock(true);
            notifyAll();
        }
    };
  
    
    /**
    *
    * Method to wait for the next race.
    */
    @Override
    public synchronized void waitForNextRace(){
        while(!this.races.getProceedToPaddock() || this.races.horsesFinished()){
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
    */
    @Override
    public synchronized void goWatchTheRace(){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
        
        while(!this.races.getReportResults()){
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
    */
    @Override
    public synchronized boolean haveIWon(){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
        return false;
        /*
        Races r = Races.getInstace();
      
        if(r.getWinner() == ((Spectators)Thread.currentThread()).getSpectatorId()){
            return true;
        }else{
            return false;
        }
        */
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
