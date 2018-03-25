package shared;

import entities.Broker;
import entities.BrokerState;
import entities.Spectators;
import entities.SpectatorsState;
import GeneralRepository.Races;

/**
 * This file contains the shared memory region Betting Centre.
 * @author Daniela Simões, 76771
 */
public class BettingCentre implements IBettingCentre {
    
    private Races races = Races.getInstace();
    
    /**
    *
    * Method to the broker accept the bets of the spectators.
    */
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
                if (this.races.getBetsOfSpectator(i) && !this.races.getAcceptedTheBet(i)) {
                    this.races.setAcceptedTheBet(i, true);
                    notifyAll();
                }
                
                all_spectators_betted &= this.races.getBetsOfSpectator(i);
            }
        }
    };
    
    /**
    *
    * Method to get the broker to honour the winners of the bets.
    */
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
                if (this.races.getWaitingToBePaidSpectators(i)) {
                    this.races.setPaidSpectators(i, true);
                    notifyAll();
                }
                
                all_spectators_paid &= this.races.getPaidSpectators(i);
            }
        }
    };
    
    /**
    *
    * Method to verify if are spectators who have won the bets.
    */
    @Override
    public synchronized boolean areThereAnyWinners(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        // verify something and return
        return false;
    };
    
    /**
    *
    * Method to allow the spectator to place a bet.
    */
    @Override
    public synchronized void placeABet(){
        Spectators spectator = ((Spectators)Thread.currentThread());
        spectator.setSpectatorsState(SpectatorsState.PLACING_A_BET);
        
        this.races.setBetsOfSpectator(spectator.getSpectatorId(), true);
        
        // wake broker because spectator placed a bet, 
        // and broker must accpet the bet
        notifyAll();
        
        while(!this.races.getAcceptedTheBet(spectator.getSpectatorId())){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
    
    /**
    *
    * Method to the spectator go collect the gains and increase the bank.
    */
    @Override
    public synchronized void goCollectTheGains(){
        Spectators spectator = ((Spectators)Thread.currentThread());
        spectator.setSpectatorsState(SpectatorsState.COLLECTING_THE_GAINS);
        
        this.races.setWaitingToBePaidSpectators(spectator.getSpectatorId(), true);
        
        // wake broker because spectator is waiting to be Paid
        // and broker must pay the honours
        notifyAll();
        
        while(!this.races.getPaidSpectators(spectator.getSpectatorId())){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
        
        this.races.setWaitingToBePaidSpectators(spectator.getSpectatorId(), false);
    };
}
