/*
 * This file contains the racing track proxy.
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

/**
 * Class that implements racing track proxy.
 * @author Daniela
 */
public class RacingTrackProxy extends ClientProxy implements IRacingTrack {
    
    /**
    * Constructor to paddock proxy.
    */
    public RacingTrackProxy(){
        super("RacingTrack");
    }
    
    @Override
    public void startTheRace(int raceNumber) {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
    }

    @Override
    public void proceedToStartLine(int raceNumber, int horseId) {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber, horseId));
    }

    @Override
    public boolean hasFinishLineBeenCrossed(int horseId, int raceNumber) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, horseId, raceNumber));
        return result.getMessage().getBoolean();
    }

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
