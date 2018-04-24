/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralRepository;

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
public class LogServer extends Log implements ServerInterface{
    
    private boolean serverEnded;
    
    public LogServer() {
        super("");
        this.serverEnded = false;
    }

    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        switch(inMessage.getType()){
            case setSpectatorState:
                super.setSpectatorState(inMessage.getInteger1(), inMessage.getSpectatorState(), inMessage.getInteger2());
                break;
            case setHorseJockeyState:
                super.setHorseJockeyState(inMessage.getInteger1(), inMessage.getHorseJockeyState(), inMessage.getInteger2());
                break;
            case setBrokerState:
                super.setBrokerState(inMessage.getBrokerState(), inMessage.getInteger1());
                break;
            case setSpectatorAmount:
                super.setSpectatorAmount(inMessage.getInteger1(), inMessage.getInteger2());
                break;
            case makeAMove:
                super.makeAMove(inMessage.getInteger1());
                break;
        }
        
        return new Message(MessageType.ACK);
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
}
