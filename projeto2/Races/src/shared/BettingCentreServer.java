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
public class BettingCentreServer extends BettingCentre implements ServerInterface {
    
    private boolean serverEnded;
    
    public BettingCentreServer() {
        super();
        this.serverEnded = false;
    }

    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        switch(inMessage.getType()){
            case TERMINATE:
                this.serverEnded = true;
            case acceptTheBets:
                super.acceptTheBets();
                break;
            case honourTheBets:
                super.honourTheBets();
                break;
            case areThereAnyWinners:
                super.areThereAnyWinners();
                break;
            case placeABet:
                super.placeABet();
                break;
            case goCollectTheGains:
                super.goCollectTheGains();
                break;
        }
        
        return new Message(MessageType.ACK);
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
}
