package shared.stable;

import generalRepository.Races;
import entities.horseJockey.HorseJockey;
import structures.enumerates.HorseJockeyState;
import entities.broker.Broker;
import interfaces.stable.StableInterface;
import java.rmi.RemoteException;
import structures.enumerates.BrokerState;
import structures.vectorClock.VectorTimestamp;

/**
 * This file contains the shared memory region Stable.
 * @author Daniela Simões, 76771
 */
public class Stable implements StableInterface {
    
    private boolean wakeEntertainTheGuests = false;
    private final Races races = Races.getInstace();
    private final VectorTimestamp clocks;
    
    public Stable(){
        this.clocks = new VectorTimestamp(4,4);//??????????????????????????????????
    }
     
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
        this.races.setAnnouncedNextRace(true);
        notifyAll();
        return this.clocks.clone();
    };
    
    /**
    *
    * Method to get the horses to proceed to stable.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized VectorTimestamp proceedToStable(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);

        while(!((races.getAnnouncedNextRace() && this.races.getWakedHorsesToPaddock()!=races.getNRunningHorses() && races.horseHasBeenSelectedToRace((HorseJockey)Thread.currentThread())) || wakeEntertainTheGuests)){
            try{
                wait();
            }catch (InterruptedException ex){
                    // do something in the future
            } 
            
            if(((HorseJockey)Thread.currentThread()).getCurrentRace()<Races.N_OF_RACES && races.horsesFinished() && races.hasMoreRaces()){
                ((HorseJockey)Thread.currentThread()).nextRace();
            }
        }
        
        if(!wakeEntertainTheGuests){
            this.races.addWakedHorsesToPaddock();
        }
        return this.clocks.clone();
    };
    
    /**
    *
    * Method to get the broker to entertain the guests - death state.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized VectorTimestamp entertainTheGuests(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);
        wakeEntertainTheGuests = true;
        notifyAll();
        return this.clocks.clone();
    }
}
