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
    private String name;
    
    public RacingTrackServer(RacesProxy races) {
        super(races);
        this.name = "Racing Track Server";
        this.serverEnded = false;
    }

    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        switch(inMessage.getType()){
            case TERMINATE:
                this.serverEnded = true;
                break;
            case startTheRace:
                super.startTheRace(inMessage.getInteger1());
                break;
            case proceedToStartLine:
                super.proceedToStartLine(inMessage.getInteger1(), inMessage.getInteger2());
                break;
            case hasFinishLineBeenCrossed:
                boolean response = super.hasFinishLineBeenCrossed(inMessage.getInteger1(), inMessage.getInteger2());   
                return new Message(MessageType.ACK, response);
            case makeAMove:
                super.makeAMove(inMessage.getInteger1(), inMessage.getInteger2());
                break;
        }
        
        return new Message(MessageType.ACK);
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
    @Override
    public String serviceName() {
        return this.name;
    }
}
