package settings;

import communication.stub.ServerInterface;
import communication.ServerChannel;
import communication.message.Message;
import communication.message.MessageException;
import communication.message.MessageType;
import java.net.SocketException;

/**
 *
 * @author Daniela
 */
public class NodeSettsInterface extends NodeSetts implements ServerInterface{
    private boolean serverEnded;
    private String name;
    
    /**
     * Constructor of node settings server.
     * @param jsonfilepath
     */
    public NodeSettsInterface(String jsonfilepath) {
        super(jsonfilepath);
        this.name = "Node Setts Server";
        this.serverEnded = false;
    }
    
    /**
    * Process and reply all the messages
     * @throws communication.message.MessageException
     * @throws java.net.SocketException
    */
    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        switch(inMessage.getType()){
            case TERMINATE:
                this.serverEnded = true;
                break;
            case SERVER_PORTS:
                return new Message(MessageType.ACK, super.SERVER_PORTS);
            case SERVER_HOSTS:
                return new Message(MessageType.ACK, super.SERVER_HOSTS);
            case N_OF_RACES:
                return new Message(MessageType.ACK, super.N_OF_RACES);
            case N_OF_HORSES:
                return new Message(MessageType.ACK, super.N_OF_HORSES);
            case N_OF_HORSES_TO_RUN:
                return new Message(MessageType.ACK, super.N_OF_HORSES_TO_RUN);
            case N_OF_SPECTATORS:
                return new Message(MessageType.ACK, super.N_OF_SPECTATORS);
            case SIZE_OF_RACING_TRACK:
                return new Message(MessageType.ACK, super.SIZE_OF_RACING_TRACK);
            case HORSE_MAX_STEP_SIZE:
                return new Message(MessageType.ACK, super.HORSE_MAX_STEP_SIZE);
            case MAX_SPECTATOR_BET:
                return new Message(MessageType.ACK, super.MAX_SPECTATOR_BET);
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
