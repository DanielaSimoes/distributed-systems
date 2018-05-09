package shared;

import GeneralRepository.RacesStub;
import communication.Proxy.ServerInterface;
import communication.ServerChannel;
import communication.message.Message;
import communication.message.MessageException;
import communication.message.MessageType;
import java.net.SocketException;

/**
 * This file implements the control centre server.
 * @author Daniela Simões, 76771
 */
public class ControlCentreInterface extends ControlCentre implements ServerInterface {
    
    private boolean serverEnded;
    private String name;
    
    public ControlCentreInterface(RacesStub races) {
        super(races);
        this.name = "Control Centre Server";
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
            case reportResults:
                super.reportResults(inMessage.getInteger1());
                break;
            case proceedToPaddock:
                super.proceedToPaddock(inMessage.getInteger1());
                break;
            case waitForNextRace:
                super.waitForNextRace(inMessage.getInteger1());
                break;
            case goWatchTheRace:
                super.goWatchTheRace(inMessage.getInteger1());
                break;
            case haveIWon:
                boolean response = super.haveIWon(inMessage.getInteger1(), inMessage.getInteger2());
                return new Message(MessageType.ACK, response);
            case relaxABit:
                super.relaxABit();
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
