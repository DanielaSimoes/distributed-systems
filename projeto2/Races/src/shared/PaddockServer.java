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
 * @author Daniela Simões, 76771
 */
public class PaddockServer extends Paddock implements ServerInterface{
    
    private boolean serverEnded;
    
    public PaddockServer(RacesProxy races) {
        super(races);
        this.serverEnded = false;
    }

    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        switch(inMessage.getType()){
            case TERMINATE:
                this.serverEnded = true;
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

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
}
