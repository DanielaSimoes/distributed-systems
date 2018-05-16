package entities.broker;

import generalRepository.Races;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.enumerates.BrokerState;
import structures.vectorClock.VectorTimestamp;

/**
 * This file contains the code that represents the broker lifecycle.
 * @author Daniela Simões, 76771
 */
public class Broker extends Thread{
    
    /**
    * State of the broker
    */
    private BrokerState state;
    /**
     *   Shared zones in which broker has actions
     */
    private final interfaces.stable.IBroker stable;
    private final interfaces.controlCentre.IBroker cc;
    private final interfaces.bettingCentre.IBroker bc;
    private final interfaces.racingTrack.IBroker rt;
    private final interfaces.paddock.IBroker paddock;
    private final interfaces.log.IBroker log;
    private int raceId = 0;
    private boolean entertainTheGuests = false;
    private static Races races; 
    
    private final VectorTimestamp myClock;
    private VectorTimestamp receivedClock;
    
    /**
    * Broker Constructor
    * @param s The Stable is a shared memory region where the broker will perform actions.
    * @param cc The Control Centre is a shared memory region where the broker will perform actions.
    * @param bc The Betting Centre is a shared memory region where the broker will perform actions.
    * @param rt The Racing Track is a shared memory region where the broker will perform actions.
    * @param paddock The Paddock is a shared memory region where the broker will perform actions.
    * @param log Log
    * @param races
    */
    public Broker(interfaces.stable.IBroker s, interfaces.controlCentre.IBroker cc, interfaces.bettingCentre.IBroker bc, interfaces.racingTrack.IBroker rt, interfaces.paddock.IBroker paddock, interfaces.log.IBroker log){
        this.stable = s;
        this.cc = cc;
        this.bc = bc;
        this.rt = rt;
        this.paddock = paddock;
        this.log = log;
        this.state = BrokerState.OPENING_THE_EVENT;
        this.myClock = new VectorTimestamp(4,4);//////??????????????????????????????????
        this.setName("Broker");
    }

/**
    *
    * The run method of the Broker executes its entire life cycle, making the transitions between
    * the states: Opening the event - Announcin next race -  Waiting for bets - Supervising the race - Settling accounts - Playing host at the bar.
    * This method runs until it's time to entertain the guests - death state.
    */
    @Override
    public void run(){
        try {
            this.setBrokerState(BrokerState.OPENING_THE_EVENT);

            while(!this.entertainTheGuests){

                switch(this.state){

                    case OPENING_THE_EVENT:
                        this.myClock.increment();
                        this.receivedClock = stable.summonHorsesToPaddock(this.myClock.clone());
                        this.myClock.update(this.receivedClock);

                        this.myClock.increment();
                        this.receivedClock = paddock.summonHorsesToPaddock(this.myClock.clone());
                        this.myClock.update(this.receivedClock);

                        break;

                    case ANNOUNCING_NEXT_RACE:
                        this.myClock.increment();
                        this.receivedClock = bc.acceptTheBets(this.myClock.clone());
                        this.myClock.update(this.receivedClock);

                        break;

                    case WAITING_FOR_BETS:
                        this.myClock.increment();
                        this.receivedClock = rt.startTheRace(this.myClock.clone());
                        this.myClock.update(this.receivedClock);

                        break;

                    case SUPERVISING_THE_RACE:
                        this.myClock.increment();
                        this.receivedClock = cc.reportResults(this.myClock.clone());
                        this.myClock.update(this.receivedClock);

                        if(bc.areThereAnyWinners()){
                            this.myClock.increment();
                            this.receivedClock = bc.honourTheBets(this.myClock.clone());
                            this.myClock.update(this.receivedClock);
                        }

                        if(races.hasMoreRaces()){
                            this.nextRace();

                            this.myClock.increment();
                            this.receivedClock = stable.summonHorsesToPaddock(this.myClock.clone());
                            this.myClock.update(this.receivedClock);

                            this.myClock.increment();
                            this.receivedClock = paddock.summonHorsesToPaddock(this.myClock.clone());
                            this.myClock.update(this.receivedClock);
                        }else{
                            this.entertainTheGuests = true;
                        }

                        break;

                    case SETTLING_ACCOUNTS:
                        if(races.hasMoreRaces()){
                            this.nextRace();

                            this.myClock.increment();
                            this.receivedClock = stable.summonHorsesToPaddock(this.myClock.clone());
                            this.myClock.update(this.receivedClock);

                            this.myClock.increment();
                            this.receivedClock = paddock.summonHorsesToPaddock(this.myClock.clone());
                            this.myClock.update(this.receivedClock);
                        }else{
                            this.entertainTheGuests = true;
                        }
                        break;

                }
            }

            this.myClock.increment();
            this.receivedClock = this.stable.entertainTheGuests(this.myClock.clone());
            this.myClock.update(this.receivedClock);
        } catch (RemoteException ex) {
            Logger.getLogger(Broker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
    *
    * Method to increment the race ID.
    */
    public void nextRace(){
        if(this.raceId==Races.N_OF_RACES-1){
            return;
        }
        this.raceId++;
    }
    
    /**
    *
    * Method to get the current race ID.
    */
    public int getCurrentRace(){
        return this.raceId;
    }
    
    /**
    *
    * Method to set the state of the broker.
    * @param state The state to be assigned to the broker.
    */
    public void setBrokerState(BrokerState state){
        if(state==this.state){
            return;
        }
        this.state = state;
        this.log.setBrokerState(state);
    } 
    
    /**
    *
    * Method to get the state of the broker.
    * @return 
    */
    public BrokerState getBrokerState(){
        return this.state;
    }
    
}

