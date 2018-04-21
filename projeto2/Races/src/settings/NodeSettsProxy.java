/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;

import communication.message.Message;
import communication.message.MessageType;
import communication.message.MessageWrapper;
import communication.Proxy.ClientProxy;
import java.util.HashMap;

/**
 *
 * @author Daniela
 */
public class NodeSettsProxy {
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    
    public NodeSettsProxy(){
        String json_path = "hosts.json";
        
        if(NodeSetts.DEBUG){
            json_path = "debug_hosts.json";
        }
        
        NodeSetts nodeSetts = new NodeSetts(json_path);
        
        this.SERVER_HOST = NodeSetts.SERVER_HOSTS.get("NodeSetts");
        this.SERVER_PORT = NodeSetts.SERVER_PORTS.get("NodeSetts");
    }
    
    /**
    * Communicate method to communicate with the Node Setts server
    */
    private MessageWrapper communicate(Message m){
        return ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    /**
    * SERVER HOSTS
    * @return 
    */
    public HashMap<String, String> SERVER_HOSTS() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper wp = communicate(new Message(mt));
        return wp.getMessage().getStrStrMap();
    }
    
    /**
    * SERVER PORTS
    * @return 
    */
    public HashMap<String, Integer> SERVER_PORTS() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper wp = communicate(new Message(mt));
        return wp.getMessage().getStrIntMap();
    }
    
    /**
    * Number of horses.
    * @return 
    */
    public int N_OF_HORSES() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
    /**
    * Size of max step.
    * @return 
    */
    public int HORSE_MAX_STEP_SIZE() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
    /**
    * Return number of spectators.
    * @return 
    */
    public int N_OF_SPECTATORS() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
    /**
    * Return max amount to bet.
    * @return 
    */
    public int MAX_SPECTATOR_BET() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
}
