/*
 * This file contains the stable proxy.
 */
package entities;

import communication.Proxy.ClientProxy;
import communication.message.Message;
import communication.message.MessageType;
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
    private void communicate(Message m){
        ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    @Override
    public void summonHorsesToPaddock(int raceNumber) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public void proceedToStable(int raceNumber, int horseID, int horseStepSize) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber, horseID, horseStepSize));
    }

    @Override
    public void entertainTheGuests() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }
    
}
