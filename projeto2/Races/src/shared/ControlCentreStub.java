/*
 * This file contains the control centre stub.
 */
package shared;

import communication.stub.ClientStub;
import communication.message.Message;
import communication.message.MessageType;
import communication.message.MessageWrapper;
import entities.Broker;
import entities.BrokerState;
import entities.HorseJockey;
import entities.HorseJockeyState;
import entities.Spectators;
import entities.SpectatorsState;

/**
 * Class that implements control centre stub.
 * @author Daniela Simões, 76771
 */
public class ControlCentreStub extends ClientStub implements IControlCentre{
    
    /**
    * Constructor to control centre stub.
    */
    public ControlCentreStub(){
        super("ControlCentre");
    }
    
    /**
     * Method to send a message to report results.
     * @param raceNumber
     */
    @Override
    public void reportResults(int raceNumber) {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
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
     * Method to send a message to wait for next race.
     * @param raceNumber
     */
    @Override
    public void waitForNextRace(int raceNumber) {
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WAITING_FOR_A_RACE_TO_START);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    /**
     * Method to send a message to go watch the race
     * @param raceNumber
     */
    @Override
    public void goWatchTheRace(int raceNumber) {
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    /**
     * Method to send a message to verify if a spectator has won.
     * @param raceNumber
     * @param spectatorId
     * @return 
     */
    @Override
    public boolean haveIWon(int raceNumber, int spectatorId) {
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.WATCHING_A_RACE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, spectatorId));
        return result.getMessage().getBoolean();
    }

    /**
     * Method to send a message to relax a bit.
     */
    @Override
    public void relaxABit() {
        ((Spectators)Thread.currentThread()).setSpectatorsState(SpectatorsState.CELEBRATING);
    }
    
}
