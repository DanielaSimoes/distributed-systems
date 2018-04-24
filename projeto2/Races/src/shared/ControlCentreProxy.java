/*
 * This file contains the control centre proxy.
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
import entities.Spectators;
import entities.SpectatorsState;
import settings.NodeSettsProxy;
import shared.IControlCentre;

/**
 * Class that implements control centre proxy.
 * @author Daniela Simões, 76771
 */
public class ControlCentreProxy implements IControlCentre{
    
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    
    /**
    * Constructor to paddock proxy.
    */
    public ControlCentreProxy(){
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_HOST = proxy.SERVER_HOSTS().get("ControlCentre");
        SERVER_PORT = proxy.SERVER_PORTS().get("ControlCentre");
    }
    
    /**
    * Method to communicate with the Control Centre.
    */
    private MessageWrapper communicate(Message m){
        return ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    @Override
    public void reportResults(int raceNumber) {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public void proceedToPaddock(int raceNumber) {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public void waitForNextRace(int raceNumber) {
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WAITING_FOR_A_RACE_TO_START);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public void goWatchTheRace(int raceNumber) {
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public boolean haveIWon(int raceNumber, int spectatorId) {
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, spectatorId));
        return result.getMessage().getBoolean();
    }

    @Override
    public void relaxABit() {
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.CELEBRATING);
    }
    
}
