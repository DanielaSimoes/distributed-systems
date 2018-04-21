/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

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
public class StableServer extends Stable implements ServerInterface{
    private boolean serverEnded;
    
    public StableServer() {
        super();
        this.serverEnded = false;
    }

    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        switch(inMessage.getType()){
            case TERMINATE:
                this.serverEnded = true;
            case summonHorsesToPaddock:
                super.summonHorsesToPaddock(inMessage.getInteger());
                break;
            case proceedToStable:
                super.proceedToStable(inMessage.getInteger(), inMessage.getInteger(), inMessage.getInteger());
                break;
            case entertainTheGuests:
                super.entertainTheGuests();
                break;
        }
        
        return new Message(MessageType.ACK);
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
}
