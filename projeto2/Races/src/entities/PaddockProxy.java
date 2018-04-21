/*
 * This file contains the paddock proxy.
 */
package entities;

import shared.IPaddock;
import communication.message.Message;
import communication.message.MessageType;
import communication.Proxy.ClientProxy;
import settings.NodeSettsProxy;


/**
 * This class implements the paddock proxy.
 * @author Daniela Simões, 76771
 */
public class PaddockProxy implements IPaddock {

    private final String SERVER_HOST;
    private final int SERVER_PORT;
    
    /**
    * Constructor to paddock proxy.
    */
    public PaddockProxy(){
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_HOST = proxy.SERVER_HOSTS().get("Paddock");
        SERVER_PORT = proxy.SERVER_PORTS().get("Paddock");
    }
    
    /**
    * Method to communicate with the Paddock.
    */
    private void communicate(Message m){
        ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    @Override
    public void proceedToPaddock() {
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public void proceedToStartLine() {
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public void goCheckHorses() {
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public void summonHorsesToPaddock() {
        int raceNumber = ((IEntity)Thread.currentThread()).getCurrentRace();
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }
    
}
