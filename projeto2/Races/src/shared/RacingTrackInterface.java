package shared;

import GeneralRepository.RacesStub;
import communication.Proxy.ServerInterface;
import communication.ServerChannel;
import communication.message.Message;
import communication.message.MessageException;
import communication.message.MessageType;
import java.net.SocketException;

/**
 * This file contains the racing track server.
 * @author Daniela Simões, 76771
 */
public class RacingTrackInterface extends RacingTrack implements ServerInterface{

    private boolean serverEnded;
    private String name;
    
    public RacingTrackInterface(RacesStub races) {
        super(races);
        this.name = "Racing Track Server";
        this.serverEnded = false;
    }

    /**
    * Method to process and reply.
    * @throws communication.message.MessageException
    * @throws java.net.SocketException
    */
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

    /**
    * Method for return the service end flag
    * @return 
    */
    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
    /**
    * Method to return the service name.
    * @return
    */
    @Override
    public String serviceName() {
        return this.name;
    }
}
