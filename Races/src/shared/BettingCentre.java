package shared;

import GeneralRepository.Bet;
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
    public void acceptTheBets(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.WAITING_FOR_BETS);
        
        while(true){
            Integer spectatorId = this.races.waitAddedBet();

            if(spectatorId!=null){
                this.races.acceptBet(spectatorId);
            }
        
            if(this.races.allSpectatorsBetted() && this.races.allSpectatorsBettsAceppted()){
                break;
            }
        }
        
        System.out.println("Broker All bets accepted");
    };
    
    /**
    *
    * Method to get the broker to honour the winners of the bets.
    */
    @Override
    public synchronized void honourTheBets(){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SETTLING_ACCOUNTS);
        
        Integer spectatorId;
        
        while(true){
            spectatorId = this.races.poolWaitingToBePaidSpectators();
            
            if(this.races.allSpectatorsPaid() && spectatorId == null){
                break;
            }else if(this.races.allSpectatorsPaid() && spectatorId != null){
                this.races.setPaidSpectators(spectatorId, true);
                notifyAll();
            }else if(!this.races.allSpectatorsPaid() && spectatorId != null){
                this.races.setPaidSpectators(spectatorId, true);
                notifyAll();
            }else if(!this.races.allSpectatorsPaid() && spectatorId == null){
                try{
                    wait();
                }catch (InterruptedException ex){
                    // do something in the future
                }
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
        return this.races.areThereAnyWinners();
    };
    
    /**
    *
    * Method to allow the spectator to place a bet.
    */
    @Override
    public void placeABet(){
        Spectators spectator = ((Spectators)Thread.currentThread());
        spectator.setSpectatorsState(SpectatorsState.PLACING_A_BET);
        
        Bet spectator_bet = this.races.chooseBet();
        this.races.addBetOfSpectator(spectator_bet);

        this.races.waitAcceptedTheBet();
        System.out.println("Spectator OUT Accepted Bet S" + spectator.getSpectatorId());
    };
    
    /**
    *
    * Method to the spectator go collect the gains and increase the bank.
    */
    @Override
    public synchronized void goCollectTheGains(){
        Spectators spectator = ((Spectators)Thread.currentThread());
        spectator.setSpectatorsState(SpectatorsState.COLLECTING_THE_GAINS);
        
        this.races.addWaitingToBePaidSpectator(spectator.getSpectatorId());
        
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
    };
}
