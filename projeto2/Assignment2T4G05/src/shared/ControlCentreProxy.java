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

/**
 * Class that implements control centre proxy.
 * @author Daniela Simões, 76771
 */
public class ControlCentreProxy extends ClientProxy implements IControlCentre{
    
    /**
    * Constructor to control centre proxy.
    */
    public ControlCentreProxy(){
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
