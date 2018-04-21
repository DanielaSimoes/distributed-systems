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
 * @author Daniela Simões, 76771
 */
public class PaddockServer extends Paddock implements ServerInterface{
    
    private boolean serverEnded;
    
    public PaddockServer() {
        super();
        this.serverEnded = false;
    }

    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        switch(inMessage.getType()){
            case TERMINATE:
                this.serverEnded = true;
            case proceedToPaddock:
                super.proceedToPaddock();
                break;
            case proceedToStartLine:
                super.proceedToStartLine();
                break;
            case summonHorsesToPaddock:
                super.summonHorsesToPaddock();
                break;
            case goCheckHorses:
                super.goCheckHorses();
                break;
        }
        
        return new Message(MessageType.ACK);
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
}
