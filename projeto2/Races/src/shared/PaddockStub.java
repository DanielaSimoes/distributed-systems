/*
 * This file contains the paddock proxy.
 */
package shared;

import communication.message.Message;
import communication.message.MessageType;
import communication.Proxy.ClientProxy;
import entities.Broker;
import entities.BrokerState;
import entities.HorseJockey;
import entities.HorseJockeyState;
import entities.Spectators;
import entities.SpectatorsState;


/**
 * This class implements the paddock proxy.
 * @author Daniela Simões, 76771
 */
public class PaddockStub extends ClientProxy implements IPaddock {

    /**
    * Constructor to paddock proxy.
    */
    public PaddockStub(){
        super("Paddock");
    }
    
    /**
     * Method to send a message to proceed to paddock.
     * @param raceNumber
     */
    @Override
    public void proceedToPaddock(int raceNumber) {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    /**
     * Method to send a message to proceed to start line.
     * @param raceNumber
     */
    @Override
    public void proceedToStartLine(int raceNumber) {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    /**
     * Method to send a message to go check the horses.
     * @param raceNumber
     */
    @Override
    public void goCheckHorses(int raceNumber) {
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.APPRAISING_THE_HORSES);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    /**
     * Method to send a message to summon horses to paddock.
     * @param raceNumber
     */
    @Override
    public void summonHorsesToPaddock(int raceNumber) {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }
    
}
