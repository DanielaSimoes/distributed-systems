/*
 * This file contains the betting centre proxy.
 */
package entities;

import communication.Proxy.ClientProxy;
import communication.message.Message;
import communication.message.MessageType;
import communication.message.MessageWrapper;
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
    public void acceptTheBets() {
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public void honourTheBets() {
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public boolean areThereAnyWinners() {
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber));
        return result.getMessage().getBoolean();
    }

    @Override
    public void placeABet() {
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public void goCollectTheGains() {
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }
    
}
