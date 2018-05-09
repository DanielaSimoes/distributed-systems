/*
 * This file contains the betting centre stub.
 */
package shared;

import GeneralRepository.Bet;
import communication.stub.ClientStub;
import communication.message.Message;
import communication.message.MessageType;
import communication.message.MessageWrapper;
import entities.Broker;
import entities.BrokerState;
import entities.Spectators;
import entities.SpectatorsState;

/**
 * Class that implements betting centre stub.
 * @author Daniela Simões, 76771
 */
public class BettingCentreStub extends ClientStub implements IBettingCentre{
    
    /**
    * Constructor to betting centre stub.
    */
    public BettingCentreStub(){
        super("BettingCentre");
    }
    
    /**
     * Method to send a message to accept the bets.
     * @param raceNumber
     */
    @Override
    public void acceptTheBets(int raceNumber) {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.WAITING_FOR_BETS);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    /**
     * Method to send a message to honour the bets.
     * @param raceNumber
     */
    @Override
    public void honourTheBets(int raceNumber) {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SETTLING_ACCOUNTS);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    /**
     * Method to send a message to verify if there are any winners.
     * @param raceNumber
     * @return 
     */
    @Override
    public boolean areThereAnyWinners(int raceNumber) {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getBoolean();
    }

    /**
     * Method to send a message to place a bet.
     * @param raceNumber
     * @param spectatorId
     * @param initialBet
     * @param moneyToBet
     * @return 
     */
    @Override
    public Bet placeABet(int raceNumber, int spectatorId, int initialBet, int moneyToBet) {
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.PLACING_A_BET);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, spectatorId, initialBet, moneyToBet));
        return result.getMessage().getBet();
    }

    /**
     * Method to send a message to collect the gains.
     * @param raceNumber
     * @param spectatorId
     * @return 
     */
    @Override
    public int goCollectTheGains(int raceNumber, int spectatorId) {
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.COLLECTING_THE_GAINS);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, spectatorId));
        return result.getMessage().getInteger1();
    }
    
}
