/*
 * This file contains the betting centre proxy.
 */
package shared;

import GeneralRepository.Bet;
import communication.Proxy.ClientProxy;
import communication.message.Message;
import communication.message.MessageType;
import communication.message.MessageWrapper;
import entities.Broker;
import entities.BrokerState;
import entities.Spectators;
import entities.SpectatorsState;
import settings.NodeSettsProxy;
import shared.IBettingCentre;

/**
 * Class that implements betting centre proxy.
 * @author Daniela Simões, 76771
 */
public class BettingCentreProxy implements IBettingCentre{
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    
    /**
    * Constructor to paddock proxy.
    */
    public BettingCentreProxy(){
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_HOST = proxy.SERVER_HOSTS().get("BettingCentre");
        SERVER_PORT = proxy.SERVER_PORTS().get("BettingCentre");
    }
    
    /**
    * Method to communicate with the Betting Centre.
    */
    private MessageWrapper communicate(Message m){
        return ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    @Override
    public void acceptTheBets(int raceNumber) {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.WAITING_FOR_BETS);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public void honourTheBets(int raceNumber) {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SETTLING_ACCOUNTS);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public boolean areThereAnyWinners(int raceNumber) {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getBoolean();
    }

    @Override
    public Bet placeABet(int raceNumber, int spectatorId, int initialBet, int moneyToBet) {
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.PLACING_A_BET);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, spectatorId));
        return result.getMessage().getBet();
    }

    @Override
    public int goCollectTheGains(int raceNumber, int spectatorId) {
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.COLLECTING_THE_GAINS);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, spectatorId));
        return result.getMessage().getInteger1();
    }
    
}
