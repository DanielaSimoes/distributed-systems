package entities;

import GeneralRepository.Log;
import GeneralRepository.Races;

/**
 *
 * @author Daniela
 */
public class Broker extends Thread implements IEntity{
    
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
    
    public Broker(shared.IStable s, shared.IControlCentre cc, shared.IBettingCentre bc, shared.IRacingTrack rt, shared.IPaddock paddock){
        this.stable = s;
        this.cc = cc;
        this.bc = bc;
        this.rt = rt;
        this.paddock = paddock;
        this.log = Log.getInstance();
        this.state = BrokerState.OPENING_THE_EVENT;
        this.setName("Broker");
    }
    
    @Override
    public void run(){
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
                        }else if(races.hasMoreRaces()){
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
    
    @Override
    public void nextRace(){
        this.raceId++;
    }
    
    @Override
    public int getCurrentRace(){
        return this.raceId;
    }
    
    public void setBrokerState(BrokerState state){
        this.state = state;
        this.log.setBrokerState(state);
    } 
    
    public BrokerState getBrokerState(){
        return this.state;
    }
    
}
