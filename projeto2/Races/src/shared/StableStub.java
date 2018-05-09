/*
 * This file contains the stable proxy.
 */
package shared;

import communication.Proxy.ClientStub;
import communication.message.Message;
import communication.message.MessageType;
import communication.message.MessageWrapper;
import entities.Broker;
import entities.BrokerState;
import entities.HorseJockey;
import entities.HorseJockeyState;

/**
 * Class that implements stable proxy.
 * @author Daniela Simões, 76771
 */
public class StableStub extends ClientStub implements IStable {
    
    /**
    * Constructor to stable proxy.
    */
    public StableStub(){
        super("Stable");
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
    
    /**
     * Method to send a message to proceed to stable.
     * @param raceNumber
     * @param horseID
     * @param horseStepSize
     * @return 
     */
    @Override
    public int proceedToStable(int raceNumber, int horseID, int horseStepSize) {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, raceNumber, horseID, horseStepSize));
        return result.getMessage().getInteger1();
    }

    /**
     * Method to send a message to entertain the guests.
     */
    @Override
    public void entertainTheGuests() {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }
    
}
