package shared.controlCentre;

import generalRepository.Races;
import entities.broker.Broker;
import structures.enumerates.BrokerState;
import entities.horseJockey.HorseJockey;
import structures.enumerates.HorseJockeyState;
import entities.spectators.Spectators;
import interfaces.controlCentre.ControlCentreInterface;
import java.rmi.RemoteException;
import structures.enumerates.SpectatorsState;
import structures.vectorClock.VectorTimestamp;

/**
 * This file contains the shared memory region Control Centre.
 * @author Daniela Simões, 76771
 */
public class ControlCentre implements ControlCentreInterface {
    
    private Races races = Races.getInstace();
    private final VectorTimestamp clocks;
    
    public ControlCentre(){
        this.clocks = new VectorTimestamp(4,4);
    }
    
    /**
    *
    * Method to report the bet results to the spectators.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized VectorTimestamp reportResults(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        
        this.races.setReportResults(true);
        notifyAll();
        return this.clocks.clone();
    };
    
    /**
    *
    * Method to move the horses to paddock.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized VectorTimestamp proceedToPaddock(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        
        this.races.addNHorsesInPaddock();
        
        if (this.races.allNHorsesInPaddock()){
            this.races.setProceedToPaddock(true);
            notifyAll();
        }
        return this.clocks.clone();
    };
  
    
    /**
    *
    * Method to wait for the next race.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized VectorTimestamp waitForNextRace(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WAITING_FOR_A_RACE_TO_START);
        while(!this.races.getProceedToPaddock() || this.races.horsesFinished()){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        return this.clocks.clone();
    };
    
    /**
    *
    * Method to send the spectators watch the race.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized VectorTimestamp goWatchTheRace(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
        
        while(!this.races.getReportResults()){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        return this.clocks.clone();
    };
    
    /**
    *
    * Method to verify if a spectator has won the bet.
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized boolean haveIWon() throws RemoteException{
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
        return this.races.haveIWon();
    };
   
    /**
    *
    * Method to get the spectator to relax a bit - death state.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized VectorTimestamp relaxABit(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.CELEBRATING);
        return this.clocks.clone();
    };
}
