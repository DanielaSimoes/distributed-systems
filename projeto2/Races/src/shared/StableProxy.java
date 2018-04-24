/*
 * This file contains the stable proxy.
 */
package shared;

import communication.Proxy.ClientProxy;
import communication.message.Message;
import communication.message.MessageType;
import communication.message.MessageWrapper;
import entities.Broker;
import entities.BrokerState;
import entities.HorseJockey;
import entities.HorseJockeyState;
import settings.NodeSettsProxy;
import shared.IStable;

/**
 * Class that implements stable proxy.
 * @author Daniela Simões, 76771
 */
public class StableProxy implements IStable {
    
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    
    /**
    * Constructor to paddock proxy.
    */
    public StableProxy(){
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_HOST = proxy.SERVER_HOSTS().get("Stable");
        SERVER_PORT = proxy.SERVER_PORTS().get("Stable");
    }
    
    /**
    * Method to communicate with the RacingTrack.
    */
    private MessageWrapper communicate(Message m){
        return ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    @Override
    public void summonHorsesToPaddock(int raceNumber) {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public int proceedToStable(int raceNumber, int horseID, int horseStepSize) {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, horseID, horseStepSize));
        return result.getMessage().getInteger1();
    }

    @Override
    public void entertainTheGuests() {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }
    
}
