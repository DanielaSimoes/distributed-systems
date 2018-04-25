/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import GeneralRepository.Bet;
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
    private String name;
    
    public BettingCentreServer(RacesProxy races) {
        super(races);
        this.name = "Betting Centre Server";
        this.serverEnded = false;
    }

    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        switch(inMessage.getType()){
            case TERMINATE:
                this.serverEnded = true;
                break;
            case acceptTheBets:
                super.acceptTheBets(inMessage.getInteger1());
                break;
            case honourTheBets:
                super.honourTheBets(inMessage.getInteger1());
                break;
            case areThereAnyWinners:
                boolean response = super.areThereAnyWinners(inMessage.getInteger1());
                return new Message(MessageType.ACK, response);
            case placeABet:
                Bet bet = super.placeABet(inMessage.getInteger1(), inMessage.getInteger2(), inMessage.getInteger3(), inMessage.getInteger4());
                return new Message(MessageType.ACK, bet);
            case goCollectTheGains:
                int response_integer = super.goCollectTheGains(inMessage.getInteger1(), inMessage.getInteger2());
                return new Message(MessageType.ACK, response_integer);
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
