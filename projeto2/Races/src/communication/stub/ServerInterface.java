/*
 * Server interface.
 */
package communication.stub;

import communication.ServerChannel;
import communication.message.Message;
import communication.message.MessageException;
import java.net.SocketException;

/**
 * Server Interface.
 * @author Daniela Simões, 76771
 */
public interface ServerInterface {
     
    /**
    * Method to process a message and reply.
    * @param inMessage
    * @param scon
    * @return
    * @throws MessageException
    * @throws SocketException
    */
    public Message processAndReply (Message inMessage, ServerChannel scon) throws MessageException, SocketException;
    
    
    /**
    * Method to get the service name.
    * Service name
    * @return 
    */
    public String serviceName();
    
    /**
    * Method to retrieve the flag of the end of the service.
    * Service end
    * @return 
    */
    public boolean serviceEnded();
}
