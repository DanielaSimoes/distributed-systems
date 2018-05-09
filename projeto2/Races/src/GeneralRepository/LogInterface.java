package GeneralRepository;

import communication.stub.ServerInterface;
import communication.ServerChannel;
import communication.message.Message;
import communication.message.MessageException;
import communication.message.MessageType;
import java.net.SocketException;

/**
 * This file implememts the log server.
 * @author Daniela Simões, 76771
 */
public class LogInterface extends Log implements ServerInterface{
    
    private boolean serverEnded;
    private String name;
    
    public LogInterface() {
        super("");
        this.name = "Log Server";
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
                this.serverEnded = super.terminateServers();
                break;
            case setSpectatorState:
                super.setSpectatorState(inMessage.getInteger1(), inMessage.getSpectatorState(), inMessage.getInteger2());
                break;
            case setHorseJockeyState:
                super.setHorseJockeyState(inMessage.getInteger1(), inMessage.getHorseJockeyState(), inMessage.getInteger2());
                break;
            case setBrokerState:
                super.setBrokerState(inMessage.getBrokerState(), inMessage.getInteger1());
                break;
            case setSpectatorAmount:
                super.setSpectatorAmount(inMessage.getInteger1(), inMessage.getInteger2());
                break;
            case makeAMove:
                super.makeAMove(inMessage.getInteger1());
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
