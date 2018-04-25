/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralRepository;

import communication.Proxy.ClientProxy;
import communication.message.Message;
import communication.message.MessageType;
import communication.message.MessageWrapper;
import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorsState;

/**
 *
 * @author Daniela
 */
public class LogProxy extends ClientProxy implements ILog{
    
    /**
    * Constructor to paddock proxy.
    */
    public LogProxy(){
        super("Log");
    }
    
    @Override
    public void setSpectatorState(int id, SpectatorsState state, int raceNumber) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, id, state, raceNumber));
    }

    @Override
    public void setHorseJockeyState(int id, HorseJockeyState state, int raceNumber) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, id, state, raceNumber));
    }

    @Override
    public void setBrokerState(BrokerState state, int raceNumber) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state));
    }

    /**
     *
     * @param raceNumber
     */
    @Override
    public void makeAMove(int raceNumber) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
     *
     * @param spectatorId
     * @param amount
     */
    @Override
    public void setSpectatorAmount(int spectatorId, int amount) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, spectatorId, amount));
    }
    
}
