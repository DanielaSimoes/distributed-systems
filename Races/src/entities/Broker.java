package entities;

import GeneralRepository.Log;

/**
 *
 * @author Daniela
 */
public class Broker extends Thread{
    
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
    
    public Broker(shared.IStable s, shared.IControlCentre cc, shared.IBettingCentre bc, shared.IRacingTrack rt, shared.IPaddock paddock){
        this.stable = s;
        this.cc = cc;
        this.bc = bc;
        this.rt = rt;
        this.paddock = paddock;
        this.log = Log.getInstance();
        this.state = BrokerState.OPENING_THE_EVENT;
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
                        }else{
                            this.entertainTheGuests = true;
                        }
                        break;

                    case SETTLING_ACCOUNTS:
                        this.entertainTheGuests = true;
                        break;

                    //case PLAYING_HOST_AT_THE_BAR:
                        // do not know what to do yet
                        //break;
                    
                }
            }
            
            this.stable.entertainTheGuests();
            System.out.println("Broker died");
    }
    
    public void setBrokerState(BrokerState state){
        this.state = state;
        this.log.setBrokerState(state);
    } 
    
    public BrokerState getBrokerState(){
        return this.state;
    }
    
}
