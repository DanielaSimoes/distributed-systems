/*
 * This file contains the racing track stub.
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

/**
 * Class that implements racing track stub.
 * @author Daniela Simões, 76771
 */
public class RacingTrackStub extends ClientStub implements IRacingTrack {
    
    /**
    * Constructor to racing track stub.
    */
    public RacingTrackStub(){
        super("RacingTrack");
    }
    
    /**
     * Method to send a message to start the race.
     * @param raceNumber
     */
    @Override
    public void startTheRace(int raceNumber) {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    /**
     * Method to send a message to proceed to start line.
     * @param raceNumber
     * @param horseId
     */
    @Override
    public void proceedToStartLine(int raceNumber, int horseId) {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber, horseId));
    }

    /**
     * Method to send a message to verify if the finish line has been crossed.
     * @param horseId
     * @param raceNumber
     * @return 
     */
    @Override
    public boolean hasFinishLineBeenCrossed(int horseId, int raceNumber) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, horseId, raceNumber));
        return result.getMessage().getBoolean();
    }

    /**
     * Method to send a message to make a move.
     * @param raceNumber
     * @param horseId
     */
    @Override
    public void makeAMove(int raceNumber, int horseId) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber, horseId));
        
        if(this.hasFinishLineBeenCrossed(horseId, raceNumber)){
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_FINISH_LINE);
        }else{
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.RUNNNING);
        }
    }
    
}
