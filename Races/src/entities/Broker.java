package entities;

import GeneralRepository.Log;
import GeneralRepository.Races;

/**
 * This file contains the code that represents the broker lifecycle.
 * @author Daniela Simões, 76771
 */
public class Broker extends Thread implements IEntity{
    
    /**
    *
    * State of the broker
    */
    private BrokerState state;
    
    private final Log log;
    /**
     *   Shared zones in which broker has actions
     */
    private final shared.IStable stable;
    private final shared.IControlCentre cc;
    private final shared.IBettingCentre bc;
    private final shared.IRacingTrack rt;
    private final shared.IPaddock paddock;
    private boolean entertainTheGuests = false;
    private final Races races = Races.getInstace();
    private int raceId = 0;
    
    /**
    *
    * Broker Constructor
    * @param s The Stable is a shared memory region where the broker will perform actions.
    * @param cc The Control Centre is a shared memory region where the broker will perform actions.
    * @param bc The Betting Centre is a shared memory region where the broker will perform actions.
    * @param rt The Racing Track is a shared memory region where the broker will perform actions.
    * @param paddock The Paddock is a shared memory region where the broker will perform actions.
    */
    public Broker(shared.IStable s, shared.IControlCentre cc, shared.IBettingCentre bc, shared.IRacingTrack rt, shared.IPaddock paddock){
        this.stable = s;
        this.cc = cc;
        this.bc = bc;
        this.rt = rt;
        this.paddock = paddock;
        this.log = Log.getInstance();
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
        this.setBrokerState(BrokerState.OPENING_THE_EVENT);
        
        while(!this.entertainTheGuests){

            switch(this.state){

                case OPENING_THE_EVENT:
                    stable.summonHorsesToPaddock();
                    paddock.summonHorsesToPaddock();
                    break;

                case ANNOUNCING_NEXT_RACE:
                    bc.acceptTheBets();
                    break;

                case WAITING_FOR_BETS:
                    rt.startTheRace();
                    break;

                case SUPERVISING_THE_RACE:
                    cc.reportResults();
                    
                    if(bc.areThereAnyWinners()){ 
                        bc.honourTheBets();
                    }
                    
                    if(races.hasMoreRaces()){
                        this.nextRace();
                        stable.summonHorsesToPaddock();
                        paddock.summonHorsesToPaddock();
                    }else{
                        this.entertainTheGuests = true;
                    }

                    break;

                case SETTLING_ACCOUNTS:
                    if(races.hasMoreRaces()){
                        this.nextRace();
                        stable.summonHorsesToPaddock();
                        paddock.summonHorsesToPaddock();
                    }else{
                        this.entertainTheGuests = true;
                    }
                    break;

            }
        }

        this.stable.entertainTheGuests();
    }
    
    /**
    *
    * Method to increment the race ID.
    */
    @Override
    public void nextRace(){
        this.raceId++;
    }
    
    /**
    *
    * Method to get the current race ID.
    */
    @Override
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
    */
    public BrokerState getBrokerState(){
        return this.state;
    }
    
}
