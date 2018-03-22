package shared;

import GeneralRepository.Races;
import GeneralRepository.Race;
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
    
    private boolean reportResults=false, proceedToPaddock = false;
    private int nHorsesInPaddock = 0;
    private Races races = Races.getInstace();
    
    @Override
    public synchronized void reportResults(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        
        this.reportResults = true;
        notifyAll();
    };
    
    @Override
    public synchronized void proceedToPaddock(){
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        if (++this.nHorsesInPaddock == races.getRace().getNRunningHorses()){
            this.proceedToPaddock = true;
            notifyAll();
        }
    };
  
    
    @Override
    public synchronized void waitForNextRace(){
        while(!this.proceedToPaddock){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    @Override
    public synchronized void goWatchTheRace(){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
        
        while(!this.reportResults){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    
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
   
    
    @Override
    public synchronized void relaxABit(){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.CELEBRATING);
    };
}
