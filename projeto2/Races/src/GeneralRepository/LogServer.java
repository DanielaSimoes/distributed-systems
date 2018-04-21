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
            case newGame:
                if(inMessage.getInteger()== -1){
                    super.newGame();
                }else{
                    super.newGame(inMessage.getInteger());
                }
                break;
            case writeEnd:
                super.writeEnd();
                break;
            case newTrial:
                super.newTrial();
                break;
            case getNumberOfGames:
                return new Message(MessageType.ACK, super.getNumberOfGames());
            case declareMatchWinner:
                super.declareMatchWinner();
                break;
            case getTotalNumberOfGames:
                return new Message(MessageType.ACK, super.getTotalNumberOfGames());
            case updateRope:
                String t = inMessage.getString();
                int i = inMessage.getInteger();
                super.updateRope(t, i);
                break;
            case assertTrialDecision:
                return new Message(MessageType.ACK, super.assertTrialDecision());
            case initContestant:
                super.initContestant(inMessage.getContestantState(), 
                        inMessage.getString(), 
                        inMessage.getInteger());
                break;
            case setContestantState:
                super.setContestantState(inMessage.getContestantState(), 
                        inMessage.getString(),
                        inMessage.getInteger());
                break;
            case initCoachState:
                super.initCoachState(inMessage.getString(), inMessage.getCoachState());
                break;
            case setCoachState:
                super.setCoachState(inMessage.getString(), inMessage.getCoachState());
                break;
            case initRefereeState:
                super.initRefereeState(inMessage.getRefereeState());
                break;
            case setRefereeState:
                super.setRefereeState(inMessage.getRefereeState());
                break;
            case setContestantLastTrial:
                super.setContestantLastTrial(inMessage.getString(), inMessage.getInteger());
                break;
            case refreshStrengths:
                super.refreshStrengths(inMessage.getString());
                break;
            case setPosition:
                super.setPosition(inMessage.getString(), inMessage.getInteger());
                break;
            case removePosition:
                super.removePosition(inMessage.getString(), inMessage.getInteger());
                break;
            case printGameWinner:
                super.printGameWinner();
                break;
            case TERMINATE:
                this.numberOfClientsRunning--;
                
                if(this.numberOfClientsRunning<=0){
                    super.terminateServers();
                    System.out.println("Terminating servers...");
                    serverEnded = true;
                    super.writeEnd();
                }
                break;
        }
        
        return new Message(MessageType.ACK);
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
}
