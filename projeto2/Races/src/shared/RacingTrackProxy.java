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
import settings.NodeSettsProxy;
import shared.IRacingTrack;

/**
 * Class that implements racing track proxy.
 * @author Daniela
 */
public class RacingTrackProxy implements IRacingTrack {
    
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    
    /**
    * Constructor to paddock proxy.
    */
    public RacingTrackProxy(){
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_HOST = proxy.SERVER_HOSTS().get("RacingTrack");
        SERVER_PORT = proxy.SERVER_PORTS().get("RacingTrack");
    }
    
    /**
    * Method to communicate with the RacingTrack.
    */
    private MessageWrapper communicate(Message m){
        return ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
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
        MessageWrapper result = communicate(new Message(mt, raceNumber, horseId));
        return result.getMessage().getBoolean();
    }

    @Override
    public void makeAMove(int raceNumber, int horseId) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, raceNumber));
        
        if(this.hasFinishLineBeenCrossed(horseId, raceNumber)){
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_FINISH_LINE);
        }else{
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.RUNNNING);
        }
    }
    
}
