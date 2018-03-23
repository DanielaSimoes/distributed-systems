package shared;

import entities.Broker;
import entities.BrokerState;
import entities.Spectators;
import entities.SpectatorsState;
import GeneralRepository.Races;

/**
 *
 * @author Daniela
 */
public class BettingCentre implements IBettingCentre {
    
    private Races races = Races.getInstace();
    
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

            all_spectators_betted = true;
            
            for(int i = 0; i < Races.N_OF_SPECTATORS; i++){
                if (this.races.getRace().getBetsOfSpectator(i) && !this.races.getRace().getAcceptedTheBet(i)) {
                    this.races.getRace().setAcceptedTheBet(i, true);
                    notifyAll();
                }
                
                all_spectators_betted &= this.races.getRace().getBetsOfSpectator(i);
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
                if (this.races.getRace().getWaitingToBePaidSpectators(i)) {
                    this.races.getRace().setPaidSpectators(i, true);
                    notifyAll();
                }
                
                all_spectators_paid &= this.races.getRace().getPaidSpectators(i);
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
        
        this.races.getRace().setBetsOfSpectator(spectator.getSpectatorId(), true);
        
        // wake broker because spectator placed a bet, 
        // and broker must accpet the bet
        notifyAll();
        
        while(!this.races.getRace().getAcceptedTheBet(spectator.getSpectatorId())){
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
        
        this.races.getRace().setWaitingToBePaidSpectators(spectator.getSpectatorId(), true);
        
        // wake broker because spectator is waiting to be Paid
        // and broker must pay the honours
        notifyAll();
        
        while(!this.races.getRace().getPaidSpectators(spectator.getSpectatorId())){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        this.races.getRace().setWaitingToBePaidSpectators(spectator.getSpectatorId(), false);
    };
}
