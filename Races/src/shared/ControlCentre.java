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
public class ControlCentre implements IControlCentre {
    
    private boolean wakeHorsesToPaddock = false, startTheRace = false, entertainTheGuests=false, reportResults=false, proceedToPaddock = false, goWatchTheRace=false, goCheckHorses = false;
    private int nSpectators = 0;
    private int nHorsesCrossedFinishLine = 0;
    private int nHorsesInPaddock = 0;
    
    @Override
    public synchronized void summonHorsesToPaddock(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        
        while(this.nSpectators != Races.N_OF_SPECTATORS){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    @Override
    public synchronized void startTheRace(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        
        while(nHorsesCrossedFinishLine != Races.N_OF_HORSES){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    @Override
    public synchronized void entertainTheGuests(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);
    };
    
    @Override
    public synchronized void reportResults(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        
        this.reportResults = true;
        notifyAll();
    };
    
    @Override
    public synchronized void proceedToPaddock(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        
        if (++this.nHorsesInPaddock == Races.N_OF_HORSES){
            this.proceedToPaddock = true;
            notifyAll();
        }
    };
  
    
    @Override
    public synchronized boolean waitForNextRace(){
        
        if (GeneralRepository.Races.actual_race < GeneralRepository.Races.N_OF_RACES){

            while(!this.proceedToPaddock){
                try{
                    wait();
                }catch (InterruptedException ex){
                    // do something in the future
                }
            }

           return true;
           
        }else{
            
            return false;
            
        }
    };
    
    @Override
    public synchronized void goWatchTheRace(){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
        this.goWatchTheRace = true;
    };
    
    
    @Override
    public synchronized boolean haveIWon(){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
        return false;
    };
   
    
    @Override
    public synchronized void relaxABit(){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.CELEBRATING);
    };
}
