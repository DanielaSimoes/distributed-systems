package entities.broker;

import structures.enumerates.BrokerState;
import interfaces.IEntity;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.constants.Constants;

/**
 * This file contains the code that represents the broker lifecycle.
 * @author Daniela Simes, 76771
 */
public class Broker extends Thread implements IEntity{
    
    /**
    * State of the broker
    */
    private BrokerState state;
    
    /**
     *   Shared zones in which broker has actions
     */
    private final interfaces.IStable stable;
    private final interfaces.IControlCentre cc;
    private final interfaces.IBettingCentre bc;
    private final interfaces.IRacingTrack rt;
    private final interfaces.IPaddock paddock;
    private final interfaces.ILog log;
    private boolean entertainTheGuests = false;
    private final interfaces.IRaces races;
    private int raceId = 0;
    
    /**
    * Broker Constructor
    * @param s The Stable is a shared memory region where the broker will perform actions.
    * @param cc The Control Centre is a shared memory region where the broker will perform actions.
    * @param bc The Betting Centre is a shared memory region where the broker will perform actions.
    * @param rt The Racing Track is a shared memory region where the broker will perform actions.
    * @param paddock The Paddock is a shared memory region where the broker will perform actions.
     * @param log Log
    */
    public Broker(interfaces.IStable s, interfaces.IControlCentre cc, interfaces.IBettingCentre bc, interfaces.IRacingTrack rt, interfaces.IPaddock paddock, interfaces.ILog log, interfaces.IRaces races){
        this.stable = s;
        this.cc = cc;
        this.bc = bc;
        this.rt = rt;
        this.paddock = paddock;
        this.log = log;
        this.races = races;
        this.setName("Broker");
    }
    
    /**
    * The run method of the Broker executes its entire life cycle, making the transitions between
    * the states: Opening the event - Announcin next race -  Waiting for bets - Supervising the race - Settling accounts - Playing host at the bar.
    * This method runs until it's time to entertain the guests - death state.
    */
    @Override
    public void run(){
        try{
            this.setBrokerState(BrokerState.OPENING_THE_EVENT);

            while(!this.entertainTheGuests){

                switch(this.state){

                    case OPENING_THE_EVENT:
                        this.setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
                        stable.summonHorsesToPaddock(raceId);

                        this.setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
                        paddock.summonHorsesToPaddock(raceId);
                        break;

                    case ANNOUNCING_NEXT_RACE:
                        this.setBrokerState(BrokerState.WAITING_FOR_BETS);
                        bc.acceptTheBets(raceId);
                        break;

                    case WAITING_FOR_BETS:
                        this.setBrokerState(BrokerState.SUPERVISING_THE_RACE);
                        rt.startTheRace(raceId);
                        break;

                    case SUPERVISING_THE_RACE:
                        this.setBrokerState(BrokerState.SUPERVISING_THE_RACE);
                        cc.reportResults(raceId);

                        this.setBrokerState(BrokerState.SUPERVISING_THE_RACE);

                        if(bc.areThereAnyWinners(raceId)){ 
                            this.setBrokerState(BrokerState.SETTLING_ACCOUNTS);
                            bc.honourTheBets(raceId);
                        }

                        if(races.hasMoreRaces()){
                            this.nextRace();
                            this.setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
                            stable.summonHorsesToPaddock(raceId);

                            this.setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
                            paddock.summonHorsesToPaddock(raceId);
                        }else{
                            this.entertainTheGuests = true;
                        }

                        break;

                    case SETTLING_ACCOUNTS:
                        if(races.hasMoreRaces()){
                            this.nextRace();
                            this.setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
                            stable.summonHorsesToPaddock(raceId);

                            this.setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
                            paddock.summonHorsesToPaddock(raceId);
                        }else{
                            this.entertainTheGuests = true;
                        }
                        break;

                }
            }

            this.setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);
            this.stable.entertainTheGuests();
        } catch (RemoteException ex) {
            Logger.getLogger(Broker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
    *
    * Method to increment the race ID.
    */
    @Override
    public void nextRace(){
        if(this.raceId==Constants.N_OF_RACES-1){
            return;
        }
        this.raceId++;
    }
    
    /**
    *
    * Method to set the state of the broker.
    * @param state The state to be assigned to the broker.
    */
    public void setBrokerState(BrokerState state) throws RemoteException{
        if(state==this.state){
            return;
        }
        this.state = state;
        this.setName("Broker - " + this.state.toString());
        this.log.setBrokerState(state, this.raceId);
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
