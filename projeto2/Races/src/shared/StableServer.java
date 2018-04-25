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
public class StableServer extends Stable implements ServerInterface{
    private boolean serverEnded;
    private String name;
    
    public StableServer(RacesProxy races) {
        super(races);
        this.name = "Stable Server";
        this.serverEnded = false;
    }

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

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
    @Override
    public String serviceName() {
        return this.name;
    }
}
