package shared.racingTrack;

import generalRepository.Races;
import entities.broker.Broker;
import structures.enumerates.BrokerState;
import entities.horseJockey.HorseJockey;
import interfaces.racingTrack.RacingTrackInterface;
import java.rmi.RemoteException;
import structures.enumerates.HorseJockeyState;
import structures.vectorClock.VectorTimestamp;

/**
 * This file contains the shared memory region Racing Track.
 * @author Daniela Simões, 76771
 */
public class RacingTrack implements RacingTrackInterface {
    
    private Races races = Races.getInstace();
    private final VectorTimestamp clocks;
    
    public RacingTrack(){
        this.clocks = new VectorTimestamp(4,4);
    }
    
    /**
    *
    * Method to start the race.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized VectorTimestamp startTheRace(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
       
        races.setStartTheRace(true);
        notifyAll();
        
        while(!races.horsesFinished()){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        races.setStartTheRace(false);
        return this.clocks.clone();
    };
    
    /**
    *
    * Method to get the horses to proceed to the start line.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized VectorTimestamp proceedToStartLine(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
        
        while(!(races.getStartTheRace() || !this.races.nextMovingHorse(((HorseJockey)Thread.currentThread()).getHorseId()))){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        notifyAll();
        return this.clocks.clone();
    };
    
    /**
    *
    * Method to verify if a given horse crossed the finish line.
    * @param horseId The HorseJockey ID.
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized boolean hasFinishLineBeenCrossed(int horseId) throws RemoteException{  
        return races.horseFinished(horseId);
    };
    
    /**
    *
    * Method to make a move - an iteration in the racing track.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized VectorTimestamp makeAMove(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        int horseId = ((HorseJockey)Thread.currentThread()).getHorseId();
        
        while(!this.races.nextMovingHorse(horseId)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        races.makeAMove(horseId);
        
        if(this.hasFinishLineBeenCrossed(horseId)){
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_FINISH_LINE);
        }else{
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.RUNNNING);
        }
        
        notifyAll();
        return this.clocks.clone();
    };   
}
