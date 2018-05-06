package shared;

import GeneralRepository.RacesProxy;
import communication.Proxy.ServerInterface;
import communication.ServerChannel;
import communication.message.Message;
import communication.message.MessageException;
import communication.message.MessageType;
import java.net.SocketException;

/**
 * This file implements the stable server.
 * @author Daniela Simões, 76771
 */
public class StableServer extends Stable implements ServerInterface{
    private boolean serverEnded;
    private String name;
    
    public StableServer(RacesProxy races) {
        super(races);
        this.name = "Stable Server";
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
            case summonHorsesToPaddock:
                super.summonHorsesToPaddock(inMessage.getInteger1());
                break;
            case proceedToStable:
                int response = super.proceedToStable(inMessage.getInteger1(), inMessage.getInteger2(), inMessage.getInteger3());
                return new Message(MessageType.ACK, response);
            case entertainTheGuests:
                super.entertainTheGuests();
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
