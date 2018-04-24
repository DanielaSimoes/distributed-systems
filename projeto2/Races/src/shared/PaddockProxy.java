/*
 * This file contains the paddock proxy.
 */
package shared;

import shared.IPaddock;
import communication.message.Message;
import communication.message.MessageType;
import communication.Proxy.ClientProxy;
import entities.Broker;
import entities.BrokerState;
import entities.HorseJockey;
import entities.HorseJockeyState;
import entities.Spectators;
import entities.SpectatorsState;
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
    public void proceedToPaddock(int raceNumber) {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public void proceedToStartLine(int raceNumber) {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public void goCheckHorses(int raceNumber) {
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.APPRAISING_THE_HORSES);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public void summonHorsesToPaddock(int raceNumber) {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }
    
}
