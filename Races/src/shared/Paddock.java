package shared;

import GeneralRepository.Races;
import entities.Broker;
import entities.BrokerState;
import entities.HorseJockey;
import entities.HorseJockeyState;
import entities.Spectators;
import entities.SpectatorsState;

/**
 *
 * @author Daniela
 */
public class Paddock implements IPaddock {
    
    private Races races = Races.getInstace();
    
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
    
    @Override
    public synchronized void proceedToStartLine(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
        
        this.races.addNHorseJockeyLeftThePadock();
        
        if(this.races.allHorseJockeyLeftThePadock()){
            notifyAll();
        }
    };
    
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
    
    @Override
    public synchronized void goCheckHorses(){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.APPRAISING_THE_HORSES);
        
        this.races.addNSpectatorsArrivedAtPaddock();
        
        if(this.races.allSpectatorsArrivedAtPaddock()){
            notifyAll();
        } 
        
        while(!this.races.allHorseJockeyLeftThePadock()){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
}
