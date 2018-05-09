package settings;

import communication.message.Message;
import communication.message.MessageType;
import communication.message.MessageWrapper;
import communication.Proxy.Proxy;
import java.util.HashMap;

/**
 * This file implements the proxy of the node settings.
 * @author Daniela Simões, 76771
 */
public class NodeSettsStub { /* doesn't extends ClientProxy because is the first to init */
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    
    public NodeSettsStub(){
        String json_path = "hosts.json";
        
        if(NodeSetts.DEBUG){
            json_path = "debug_hosts.json";
        }
        
        NodeSetts nodeSetts = new NodeSetts(json_path);
        
        this.SERVER_HOST = nodeSetts.SERVER_HOSTS.get("NodeSetts");
        this.SERVER_PORT = nodeSetts.SERVER_PORTS.get("NodeSetts");
    }
    
    /**
    * Method to communicate with the Node Setts server.
    */
    private MessageWrapper communicate(Message m){
        return Proxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    /**
    * Method to return the SERVER HOSTS.
    * @return 
    */
    public HashMap<String, String> SERVER_HOSTS() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper wp = communicate(new Message(mt));
        return wp.getMessage().getStrStrMap();
    }
    
    /**
    * Method to return the SERVER PORTS.
    * @return 
    */
    public HashMap<String, Integer> SERVER_PORTS() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper wp = communicate(new Message(mt));
        return wp.getMessage().getStrIntMap();
    }
    
    /**
    * Method to return the number of horses.
    * @return 
    */
    public int N_OF_HORSES() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper wp = communicate(new Message(mt));
        return wp.getMessage().getInteger1();
    }
    
    /**
    * Method to return the size of the max step of the horse.
    * @return 
    */
    public int HORSE_MAX_STEP_SIZE() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper wp = communicate(new Message(mt));
        return wp.getMessage().getInteger1();
    }
    
    /**
    * Method to return the number of spectators.
    * @return 
    */
    public int N_OF_SPECTATORS() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper wp = communicate(new Message(mt));
        return wp.getMessage().getInteger1();
    }
    
    /**
    * Method to return max amount to bet.
    * @return 
    */
    public int MAX_SPECTATOR_BET() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        MessageWrapper wp = communicate(new Message(mt));
        return wp.getMessage().getInteger1();
    }
    
}
