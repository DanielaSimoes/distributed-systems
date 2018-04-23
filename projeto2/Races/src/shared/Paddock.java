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
 * This file contains the shared memory region Paddock.
 * @author Daniela Simões, 76771
 */
public class Paddock implements IPaddock {
    
    private RacesProxy races;
    
    public Paddock(RacesProxy races){
        this.races = races;
    }
    
    /**
    *
    * Method to send the horses to paddock.
     * @param raceNumber
    */
    @Override
    public synchronized void proceedToPaddock(int raceNumber){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        
        while(!this.races.allSpectatorsArrivedAtPaddock(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    /**
    *
    * Method to send the horses to the start line.
     * @param raceNumber
    */
    @Override
    public synchronized void proceedToStartLine(int raceNumber){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
        
        this.races.addNHorseJockeyLeftThePadock(raceNumber);
        
        if(this.races.allHorseJockeyLeftThePadock(raceNumber)){
            notifyAll();
        }
    };
    
    /**
    *
    * Method to get the broker to announce the next race.
     * @param raceNumber
    */
    @Override
    public synchronized void summonHorsesToPaddock(int raceNumber){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        
        while(!this.races.allSpectatorsArrivedAtPaddock(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    /**
    *
    * Method to get spectators to go check the horses.
     * @param raceNumber
    */
    @Override
    public synchronized void goCheckHorses(int raceNumber){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.APPRAISING_THE_HORSES);
        
        this.races.addNSpectatorsArrivedAtPaddock(raceNumber);
        notifyAll();
    
        while(!this.races.allHorseJockeyLeftThePadock(raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
}
