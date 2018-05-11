package entities.broker;

import structures.enumerates.BrokerState;
import structures.vectorClock.VectorTimestamp;
import structures.constants.Constants;

/**
 * This file contains the code that represents the broker lifecycle.
 * @author Daniela Simões, 76771
 */
public class Broker extends Thread{
    
    /**
    * State of the broker
    */
    private BrokerState state;
    private final int id;
    /**
     *   Shared zones in which broker has actions
     */
    private final interfaces.stable.IBroker stable;
    private final interfaces.controlCentre.IBroker cc;
    private final interfaces.bettingCentre.IBroker bc;
    private final interfaces.racingTrack.IBroker rt;
    private final interfaces.paddock.IBroker paddock;
    private final interfaces.log.IBroker log;
    private final interfaces.races.IBroker races;
    
    //private final VectorTimestamp myClock;
    //private VectorTimestamp receivedClock;
    
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
    public Broker(int id, interfaces.stable.IBroker s, interfaces.controlCentre.IBroker cc, interfaces.bettingCentre.IBroker bc, interfaces.racingTrack.IBroker rt, interfaces.paddock.IBroker paddock, interfaces.log.IBroker log, interfaces.races.IBroker races){
        this.stable = s;
        this.cc = cc;
        this.bc = bc;
        this.rt = rt;
        this.paddock = paddock;
        this.log = log;
        this.races = races;
        this.state = BrokerState.OPENING_THE_EVENT;
        this.id = id;
        //this.myClock = new VectorTimestamp(Constants.N_OF_HORSES+);
        this.setName("Broker");
    }
}
