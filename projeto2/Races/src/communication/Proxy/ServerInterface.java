/*
 * Server interface.
 */
package communication.Proxy;

import communication.ServerChannel;
import communication.message.Message;
import communication.message.MessageException;
import java.net.SocketException;

/**
 *
 * @author Daniela
 */
public interface ServerInterface {
     
    /**
     *
     * @param inMessage
     * @param scon
     * @return
     * @throws MessageException
     * @throws SocketException
     */
    public Message processAndReply (Message inMessage, ServerChannel scon) throws MessageException, SocketException;
    
    
    /**
     * Service end
     * @return 
     */
    public boolean serviceEnded();
}
