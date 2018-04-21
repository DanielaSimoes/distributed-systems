/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import GeneralRepository.RacesProxy;
import communication.Proxy.ServerInterface;
import communication.ServerChannel;
import communication.message.Message;
import communication.message.MessageException;
import communication.message.MessageType;
import java.net.SocketException;

/**
 *
 * @author Daniela
 */
public class RacingTrackServer extends RacingTrack implements ServerInterface{

    private boolean serverEnded;
    
    public RacingTrackServer(RacesProxy races) {
        super(races);
        this.serverEnded = false;
    }

    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        switch(inMessage.getType()){
            case TERMINATE:
                this.serverEnded = true;
            case startTheRace:
                super.startTheRace(inMessage.getInteger());
                break;
            case proceedToStartLine:
                super.proceedToStartLine(inMessage.getInteger());
                break;
            case hasFinishLineBeenCrossed:
                super.hasFinishLineBeenCrossed(inMessage.getInteger(), inMessage.getInteger());
                break;
            case makeAMove:
                super.makeAMove(inMessage.getInteger());
                break;
        }
        
        return new Message(MessageType.ACK);
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
}
