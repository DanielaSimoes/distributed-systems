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
public class LogProxy extends ClientProxy{
    
    /**
    * Constructor to paddock proxy.
    */
    public LogProxy(){
        super("Log");
    }
    
    public void setSpectatorState(int id, SpectatorsState state) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, id, state));
    }

    public void setHorseJockeyState(int id, HorseJockeyState state) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, id, state));
    }

    public void setBrokerState(BrokerState state) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state));
    }

    public void makeAMove() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    public boolean setSpectatorAmount(int spectatorId, int amount) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper result = communicate(new Message(mt, spectatorId, amount));
        return result.getMessage().getBoolean();
    }
    
}
