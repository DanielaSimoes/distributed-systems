package shared;

import GeneralRepository.RacesStub;
import communication.stub.ServerInterface;
import communication.ServerChannel;
import communication.message.Message;
import communication.message.MessageException;
import communication.message.MessageType;
import java.net.SocketException;

/**
 * This file implements the paddock server.
 * @author Daniela Simões, 76771
 */
public class PaddockInterface extends Paddock implements ServerInterface{
    
    private boolean serverEnded;
    private String name;
    
    public PaddockInterface(RacesStub races) {
        super(races);
        this.name = "Paddock Server";
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
            case proceedToPaddock:
                super.proceedToPaddock(inMessage.getInteger1());
                break;
            case proceedToStartLine:
                super.proceedToStartLine(inMessage.getInteger1());
                break;
            case summonHorsesToPaddock:
                super.summonHorsesToPaddock(inMessage.getInteger1());
                break;
            case goCheckHorses:
                super.goCheckHorses(inMessage.getInteger1());
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
