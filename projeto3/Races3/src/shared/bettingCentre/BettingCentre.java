package shared.bettingCentre;

import generalRepository.Bet;
import entities.broker.Broker;
import structures.enumerates.BrokerState;
import entities.spectators.Spectators;
import structures.enumerates.SpectatorsState;
import generalRepository.Races;
import interfaces.bettingCentre.BettingCentreInterface;
import java.rmi.RemoteException;
import structures.vectorClock.VectorTimestamp;
/**
 * This file contains the shared memory region Betting Centre.
 * @author Daniela Simões, 76771
 */
public class BettingCentre implements BettingCentreInterface {
    
    private Races races = Races.getInstace();
    private final VectorTimestamp clocks;
    
    public BettingCentre(){
        this.clocks = new VectorTimestamp(4,4);
    }
    
    /**
    *
    * Method to the broker accept the bets of the spectators.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public VectorTimestamp acceptTheBets(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
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
        return this.clocks.clone();
    };
    
    /**
    *
    * Method to get the broker to honour the winners of the bets.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized VectorTimestamp honourTheBets(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
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
        return this.clocks.clone();
    };
    
    /**
    *
    * Method to verify if are spectators who have won the bets.
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized boolean areThereAnyWinners() throws RemoteException{
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        return this.races.areThereAnyWinners();
    };
    
    /**
    *
    * Method to allow the spectator to place a bet.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public VectorTimestamp placeABet(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        Spectators spectator = ((Spectators)Thread.currentThread());
        spectator.setSpectatorsState(SpectatorsState.PLACING_A_BET);
        
        Bet spectator_bet = this.races.chooseBet();
        this.races.addBetOfSpectator(spectator_bet);

        this.races.waitAcceptedTheBet();
        return this.clocks.clone();
    };
    
    /**
    *
    * Method to the spectator go collect the gains and increase the bank.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
    */
    @Override
    public synchronized VectorTimestamp goCollectTheGains(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
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
        return this.clocks.clone();
    };
}
