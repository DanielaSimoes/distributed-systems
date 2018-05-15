package shared.paddock;

import entities.broker.Broker;
import entities.horseJockey.HorseJockey;
import entities.spectators.Spectators;
import generalRepository.Races;
import interfaces.paddock.PaddockInterface;
import java.rmi.RemoteException;
import structures.enumerates.BrokerState;
import structures.enumerates.HorseJockeyState;
import structures.enumerates.SpectatorsState;
import structures.vectorClock.VectorTimestamp;

/**
 *
 * @author Daniela
 */
public class Paddock implements PaddockInterface{
    private Races races = Races.getInstace();
    private final VectorTimestamp clocks;
    
    public Paddock(){
        this.clocks = new VectorTimestamp(4,4); //?????????????????????????????????
    }
    
    /**
    *
    * Method to send the horses to paddock.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException 
    */
    @Override
    public synchronized VectorTimestamp proceedToPaddock(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        
        while(!this.races.allSpectatorsArrivedAtPaddock()){
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
    * Method to send the horses to the start line.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized VectorTimestamp proceedToStartLine(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
        
        this.races.addNHorseJockeyLeftThePadock();
        
        if(this.races.allHorseJockeyLeftThePadock()){
            notifyAll();
        }
        return this.clocks.clone();
    };
    
    /**
    *
    * Method to get the broker to announce the next race.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized VectorTimestamp summonHorsesToPaddock(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        
        while(!this.races.allSpectatorsArrivedAtPaddock()){
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
    * Method to get spectators to go check the horses.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized VectorTimestamp goCheckHorses(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
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
        return this.clocks.clone();
    };
}
