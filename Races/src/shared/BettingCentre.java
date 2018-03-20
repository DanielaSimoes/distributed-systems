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
    
    private boolean[] betsOfSpectators = new boolean[4];
    private boolean[] acceptedTheBet = new boolean[4];
    
    private boolean[] waitingToBePaidSpectators = new boolean[4];
    private boolean[] paidSpectators = new boolean[4];
    
    public BettingCentre(){
        for(int i = 0; i < 4; i++){
            this.betsOfSpectators[i] = false;
            this.acceptedTheBet[i] = false;
            this.paidSpectators[i] = false;
            this.waitingToBePaidSpectators[i] = false;
        }
     }
    
    @Override
    public synchronized void acceptTheBets(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.WAITING_FOR_BETS);
        
        boolean all_spectators_betted = false;
        
        while(!all_spectators_betted){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }

            for(int i = 0; i < 4; i++){
                if (betsOfSpectators[i]) {
                    this.acceptedTheBet[i] = true;
                    notifyAll();
                }
                
                all_spectators_betted &= betsOfSpectators[i];
            }
        }
    };
    
    @Override
    public synchronized void honourTheBets(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SETTLING_ACCOUNTS);
        
        boolean all_spectators_paid = false;
        
        while(!all_spectators_paid){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }

            for(int i = 0; i < 4; i++){
                if (waitingToBePaidSpectators[i]) {
                    this.paidSpectators[i] = true;
                    notifyAll();
                }
                
                all_spectators_paid &= paidSpectators[i];
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
        Spectators spectator = ((Spectators)Thread.currentThread());
        spectator.setSpectatorsState(SpectatorsState.PLACING_A_BET);
        
        this.betsOfSpectators[spectator.getSpectatorId()] = true;
        
        // wake broker because spectator placed a bet, 
        // and broker must accpet the bet
        notifyAll();
        
        while(!this.acceptedTheBet[spectator.getSpectatorId()]){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    @Override
    public synchronized void goCollectTheGains(){
        Spectators spectator = ((Spectators)Thread.currentThread());
        spectator.setSpectatorsState(SpectatorsState.COLLECTING_THE_GAINS);
        
        this.waitingToBePaidSpectators[spectator.getSpectatorId()] = true;
        
        // wake broker because spectator is waiting to be Paid
        // and broker must pay the honours
        notifyAll();
        
        while(!this.paidSpectators[spectator.getSpectatorId()]){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        this.waitingToBePaidSpectators[spectator.getSpectatorId()] = false;
        
    };
}
