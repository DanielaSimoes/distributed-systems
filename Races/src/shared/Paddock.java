package shared;

import GeneralRepository.Races;
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
    
    private Races races = Races.getInstace();
    
    /**
    *
    * Method to send the horses to paddock.
    */
    @Override
    public synchronized void proceedToPaddock(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        
        while(!this.races.allSpectatorsArrivedAtPaddock()){
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
    */
    @Override
    public synchronized void proceedToStartLine(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
        
        this.races.addNHorseJockeyLeftThePadock();
        
        if(this.races.allHorseJockeyLeftThePadock()){
            notifyAll();
        }
    };
    
    /**
    *
    * Method to get the broker to announce the next race.
    */
    @Override
    public synchronized void summonHorsesToPaddock(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        
        while(!this.races.allSpectatorsArrivedAtPaddock()){
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
    */
    @Override
    public synchronized void goCheckHorses(){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.APPRAISING_THE_HORSES);
        
        this.races.addNSpectatorsArrivedAtPaddock();
        notifyAll();
    
        while(!this.races.allHorseJockeyLeftThePadock()){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
}
