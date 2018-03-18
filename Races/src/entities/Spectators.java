package entities;

/**
 *
 * @author Daniela
 */
public class Spectators extends Thread{
    
    private SpectatorsState state;
    private double moneyToBet;
    
    /*
        Shared zones in which Spectators has actions
    */
    
    private final shared.IControlCentre cc;
    private final shared.IBettingCentre bc;
    private final shared.IPaddock paddock;
    private final int id;
    
    public Spectators(shared.IControlCentre cc, shared.IBettingCentre bc, shared.IPaddock paddock, double moneyToBet, int id){
        this.id = id;
        this.cc = cc;
        this.bc = bc;
        this.paddock = paddock;
        this.moneyToBet = moneyToBet;
    }
    
    @Override
    public void run(){ 
        while(cc.waitForNextRace()){
            switch(this.state){

                case WAITING_FOR_A_RACE_TO_START:
                    paddock.goCheckHorses();
                    break;

                case APPRAISING_THE_HORSES:
                    bc.placeABet();
                    break;

                case PLACING_A_BET:
                    cc.goWatchTheRace();
                    break;

                case WATCHING_A_RACE:
                    if(cc.haveIWon()) bc.goCollectTheGains();
                    break;

                case COLLECTING_THE_GAINS:
                    // don't know what to do
                    break;

                case CELEBRATING:
                    // don't know what to do
                    break;
            }
            
            cc.relaxABit();
        }
    }
    
    public void setSpectatorsState(SpectatorsState state){
        this.state = state;
    } 
    
    public SpectatorsState getSpectatorsState(){
        return this.state;
    }
    
    public double getMoneyToBet(){
        return this.moneyToBet;
    }
}
