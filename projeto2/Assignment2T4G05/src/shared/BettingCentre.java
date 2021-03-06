package shared;

import GeneralRepository.Bet;
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
     * @param raceNumber
    */
    @Override
    public synchronized void honourTheBets(int raceNumber){
        Integer spectatorId;
        
        while(true){
            spectatorId = this.races.poolWaitingToBePaidSpectators(raceNumber);
            
            if(this.races.allSpectatorsPaid(raceNumber) && spectatorId == null){
                break;
            }else if(this.races.allSpectatorsPaid(raceNumber) && spectatorId != null){
                this.races.setPaidSpectators(spectatorId, raceNumber);
                notifyAll();
            }else if(!this.races.allSpectatorsPaid(raceNumber) && spectatorId != null){
                this.races.setPaidSpectators(spectatorId, raceNumber);
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
        return this.races.areThereAnyWinners(raceNumber);
    };
    
    /**
    *
    * Method to allow the spectator to place a bet.
     * @param raceNumber
     * @param spectatorId
     * @param initialBet
     * @param moneyToBet
     * @return 
    */
    @Override
    public Bet placeABet(int raceNumber, int spectatorId, int initialBet, int moneyToBet){
        Bet spectator_bet = this.races.chooseBet(raceNumber, spectatorId, initialBet, moneyToBet);
        this.races.addBetOfSpectator(spectator_bet, raceNumber, spectatorId);

        this.races.waitAcceptedTheBet(raceNumber, spectatorId);
        return spectator_bet;
    };
    
    /**
    *
    * Method to the spectator go collect the gains and increase the bank.
     * @param raceNumber
     * @param spectatorId
     * @return 
    */
    @Override
    public synchronized int goCollectTheGains(int raceNumber, int spectatorId){
        this.races.addWaitingToBePaidSpectator(spectatorId, raceNumber);
        
        // wake broker because spectator is waiting to be Paid
        // and broker must pay the honours
        notifyAll();
        
        int gains = this.races.getPaidSpectators(spectatorId, raceNumber);
        
        while(gains == -1){
            try{
                wait();
            }catch (InterruptedException ex){
                // do something in the future
            }
            
            gains = this.races.getPaidSpectators(spectatorId, raceNumber);
        }
        
        return gains;
    };
}
