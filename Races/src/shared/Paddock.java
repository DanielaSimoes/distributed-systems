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
    
    private int nSpectatorsArrivedAtPaddock = 0, nHorseJockeyLeftThePadock = 0;
    private Races races = Races.getInstace();
    
    @Override
    public synchronized void proceedToPaddock(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        
        while(nSpectatorsArrivedAtPaddock!=Races.N_OF_SPECTATORS){
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
        
        if(++this.nHorseJockeyLeftThePadock==races.getRace().getNRunningHorses()){
            notifyAll();
            System.out.println("notificnado com nHorseJockeyLeftThePadock = " + nHorseJockeyLeftThePadock);
        }
    };
    
    @Override
    public synchronized void summonHorsesToPaddock(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        
        while(this.nSpectatorsArrivedAtPaddock != Races.N_OF_SPECTATORS){
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
        
        if(++this.nSpectatorsArrivedAtPaddock==Races.N_OF_SPECTATORS){
            notifyAll();
        } 
        
        while(this.nHorseJockeyLeftThePadock!=races.getRace().getNRunningHorses()){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        System.out.println("Out of goCheckHorses");
    };
    
}
