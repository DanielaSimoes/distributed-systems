/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import communication.Proxy.ClientProxy;
import communication.message.Message;
import communication.message.MessageType;
import communication.message.MessageWrapper;
import settings.NodeSettsProxy;

/**
 *
 * @author Daniela
 */
public class LogProxy {
    
    private final String SERVER_HOST;
    private final int SERVER_PORT;

    public LogProxy(){
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_HOST = proxy.SERVER_HOSTS().get("Log");
        SERVER_PORT = proxy.SERVER_PORTS().get("Log");
    }
    
    private MessageWrapper communicate(Message m){
        MessageWrapper test = ClientProxy.connect(SERVER_HOST, SERVER_PORT, m);
        return test;
    }
    
    /**
     *
     * @param spectatorId
     * @param amount
     */
    public void setSpectatorAmount(int spectatorId, int amount){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, spectatorId, amount));
    }

    /**
     *
     * @param state
     */
    public void setBrokerState(BrokerState state){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state));
    }
    

    /**
     *
     */
    public void makeAMove(){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }
    
     /**
    *
    * Method to set the state of a HorseJockey.
    * @param id
    * @param state The state to be assigned.
    */
    public void setHorseJockeyState(int id, HorseJockeyState state){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, id, state));
    }
    
    /**
    *
    * Method to set the state of a Spectator.
     * @param id
    * @param state The state to be assigned.
    */
    public void setSpectatorState(int id, SpectatorsState state){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, id, state));
    }
    
}
