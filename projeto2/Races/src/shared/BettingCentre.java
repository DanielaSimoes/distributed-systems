package shared;

import GeneralRepository.Bet;
import entities.Broker;
import entities.BrokerState;
import entities.Spectators;
import entities.SpectatorsState;
import GeneralRepository.RacesProxy;

/**
 * This file contains the shared memory region Betting Centre.
 * @author Daniela Simões, 76771
 */
public class BettingCentre implements IBettingCentre {
    
    private final RacesProxy races;
    
    public BettingCentre(RacesProxy races){
        this.races = races;
    }
    
    /**
    *
    * Method to the broker accept the bets of the spectators.
     * @param raceNumber
    */
    @Override
    public void acceptTheBets(int raceNumber){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.WAITING_FOR_BETS);
        
        while(true){
            Integer spectatorId = this.races.waitAddedBet(raceNumber);

            if(spectatorId!=null){
                this.races.acceptBet(spectatorId, raceNumber);
            }
        
            if(this.races.allSpectatorsBetted(raceNumber) && this.races.allSpectatorsBettsAceppted(raceNumber)){
                break;
            }
        }
    };
    
    /**
    *
    * Method to get the broker to honour the winners of the bets.
    */
    @Override
    public synchronized void honourTheBets(int raceNumber){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SETTLING_ACCOUNTS);
        
        Integer spectatorId;
        
        while(true){
            spectatorId = this.races.poolWaitingToBePaidSpectators(raceNumber);
            
            if(this.races.allSpectatorsPaid(raceNumber) && spectatorId == null){
                break;
            }else if(this.races.allSpectatorsPaid(raceNumber) && spectatorId != null){
                this.races.setPaidSpectators(spectatorId, true, raceNumber);
                notifyAll();
            }else if(!this.races.allSpectatorsPaid(raceNumber) && spectatorId != null){
                this.races.setPaidSpectators(spectatorId, true, raceNumber);
                notifyAll();
            }else if(!this.races.allSpectatorsPaid(raceNumber) && spectatorId == null){
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
     * @param raceNumber
     * @return 
    */
    @Override
    public synchronized boolean areThereAnyWinners(int raceNumber){
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        return this.races.areThereAnyWinners(raceNumber);
    };
    
    /**
    *
    * Method to allow the spectator to place a bet.
     * @param raceNumber
    */
    @Override
    public void placeABet(int raceNumber){
        Spectators spectator = ((Spectators)Thread.currentThread());
        spectator.setSpectatorsState(SpectatorsState.PLACING_A_BET);
        
        Bet spectator_bet = this.races.chooseBet(raceNumber);
        this.races.addBetOfSpectator(spectator_bet, raceNumber);

        this.races.waitAcceptedTheBet(raceNumber);
    };
    
    /**
    *
    * Method to the spectator go collect the gains and increase the bank.
     * @param raceNumber
    */
    @Override
    public synchronized void goCollectTheGains(int raceNumber){
        Spectators spectator = ((Spectators)Thread.currentThread());
        spectator.setSpectatorsState(SpectatorsState.COLLECTING_THE_GAINS);
        
        this.races.addWaitingToBePaidSpectator(spectator.getSpectatorId(), raceNumber);
        
        // wake broker because spectator is waiting to be Paid
        // and broker must pay the honours
        notifyAll();
        
        while(!this.races.getPaidSpectators(spectator.getSpectatorId(), raceNumber)){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
        }
    };
}
