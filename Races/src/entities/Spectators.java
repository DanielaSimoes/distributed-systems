package entities;

import GeneralRepository.Log;
import GeneralRepository.Races;

/**
 *
 * @author Daniela
 */
public class Spectators extends Thread{
    
    private SpectatorsState state;
    private double moneyToBet;
    private final Log log;
    private final Races races = Races.getInstace();
    
    /*
        Shared zones in which Spectators has actions
    */
    
    private final shared.IControlCentre cc;
    private final shared.IBettingCentre bc;
    private final shared.IPaddock paddock;
    private final int id;
    private boolean relaxABit;
    
    public Spectators(shared.IControlCentre cc, shared.IBettingCentre bc, shared.IPaddock paddock, double moneyToBet, int id){
        this.id = id;
        this.cc = cc;
        this.bc = bc;
        this.paddock = paddock;
        this.moneyToBet = moneyToBet;
        this.log = Log.getInstance();
        this.state = SpectatorsState.WAITING_FOR_A_RACE_TO_START;
        this.relaxABit = false;
        this.setName("Spectator " + id);
    }
    
    @Override
    public void run(){ 
        while(!this.relaxABit){
            switch(this.state){

                case WAITING_FOR_A_RACE_TO_START:
                    cc.waitForNextRace();
                    paddock.goCheckHorses();
                    break;

                case APPRAISING_THE_HORSES:
                    bc.placeABet();
                    break;

                case PLACING_A_BET:
                    cc.goWatchTheRace();
                    break;

                case WATCHING_A_RACE:
                    if(cc.haveIWon()){
                        bc.goCollectTheGains();
                    }else if(races.hasMoreRaces()){
                        cc.waitForNextRace();
                        paddock.goCheckHorses();
                    }else{
                        this.relaxABit = true;
                    }
                    break;

                case COLLECTING_THE_GAINS:
                    if(races.hasMoreRaces()){
                        cc.waitForNextRace();
                        paddock.goCheckHorses();
                    }else{
                        this.relaxABit = true;
                    }
                    break;
            }   
        }
        
        cc.relaxABit();
    }
    
    public void setSpectatorsState(SpectatorsState state){
        this.state = state;
        this.log.setSpectatorState(id, state);
    } 
    
    public SpectatorsState getSpectatorsState(){
        return this.state;
    }
    
    public double getMoneyToBet(){
        return this.moneyToBet;
    }
    
    public int getSpectatorId(){
        return this.id;
    }
}
