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
public class BettingCentreServer extends BettingCentre implements ServerInterface {
    
    private boolean serverEnded;
    
    public BettingCentreServer(RacesProxy races) {
        super(races);
        this.serverEnded = false;
    }

    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        switch(inMessage.getType()){
            case TERMINATE:
                this.serverEnded = true;
            case acceptTheBets:
                super.acceptTheBets(inMessage.getInteger());
                break;
            case honourTheBets:
                super.honourTheBets(inMessage.getInteger());
                break;
            case areThereAnyWinners:
                super.areThereAnyWinners(inMessage.getInteger());
                break;
            case placeABet:
                super.placeABet(inMessage.getInteger());
                break;
            case goCollectTheGains:
                super.goCollectTheGains(inMessage.getInteger());
                break;
        }
        
        return new Message(MessageType.ACK);
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
}
