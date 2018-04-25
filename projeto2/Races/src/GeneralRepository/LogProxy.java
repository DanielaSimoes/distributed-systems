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
import entities.IEntity;
import entities.SpectatorsState;
import settings.NodeSettsProxy;

/**
 *
 * @author Daniela
 */
public class LogProxy {
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    
    /**
    * Constructor to paddock proxy.
    */
    public LogProxy(){
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_HOST = proxy.SERVER_HOSTS().get("Log");
        SERVER_PORT = proxy.SERVER_PORTS().get("Log");
    }
    
    /**
    * Method to communicate with the Control Centre.
    */
    private MessageWrapper communicate(Message m){
        return ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
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
