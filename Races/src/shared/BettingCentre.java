package shared;

import entities.Broker;
import entities.BrokerState;
import entities.Spectators;
import entities.SpectatorsState;

/**
 *
 * @author Daniela
 */
public class BettingCentre implements IBettingCentre {
    
    private boolean betsOfSpectators[], paidSpectators[] = new boolean[4];
    private boolean waitingInQueueToBet, waitingInQueueToCollectTheGains = true;
    
    @Override
    public synchronized void acceptTheBets(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.WAITING_FOR_BETS);
        
        for(int i = 0; i < 4; i++){
            if (!betsOfSpectators[i]) {
                try{
                    wait();
                }catch (InterruptedException ex){
                    // do something in the future
                }
            }
        }
    };
    
    @Override
    public synchronized void honourTheBets(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SETTLING_ACCOUNTS);
        
        for(int i = 0; i < 4; i++){
            if (!paidSpectators[i]) {
                try{
                    wait();
                }catch (InterruptedException ex){
                    // do something in the future
                }
            }
        }
    };
    
    @Override
    public synchronized boolean areThereAnyWinners(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        // verify something and return
        return false;
    };
    
    @Override
    public synchronized void placeABet(){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.PLACING_A_BET);
        
        while(waitingInQueueToBet){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    @Override
    public synchronized void goCollectTheGains(){
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.COLLECTING_THE_GAINS);
        
        while(waitingInQueueToCollectTheGains){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
}
