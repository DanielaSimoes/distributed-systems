/*
 * This file contains the client proxy.
 */
package communication.Proxy;

import communication.ClientChannel;
import communication.message.Message;
import communication.message.MessageType;
import communication.message.MessageWrapper;
import entities.LogProxy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that implements client proxy.
 * @author Daniela Simões, 76771
 */
public class ClientProxy extends Thread {
    private final String clientProxyServerName;
    private final int toServerPort;
    private final Message outMessage;
    private final MessageWrapper result;
    
    /**
     * Construct the client proxy.
     * @param clientProxyServerName
     * @param toServerPort
     * @param result
     * @param outMessage
     */
    public ClientProxy(String clientProxyServerName, int toServerPort, MessageWrapper result, Message outMessage){
        this.clientProxyServerName = clientProxyServerName;
        this.toServerPort = toServerPort;
        this.outMessage = outMessage;
        this.result = result;
    }
    
    /**
     * Client proxy wrapper.
     * @param logServerName
     * @param logServerPort
     * @param m
     * @return 
     */
    public static MessageWrapper connect(String logServerName, int logServerPort, Message m){
        MessageWrapper result = new MessageWrapper();
        
        ClientProxy cp = new ClientProxy(logServerName, logServerPort, result, m);
        
        cp.start();
        
        try {
            // System.out.printf("[%s][%d][%s] Init Join\n", logServerName, logServerPort, m.getType().toString()); 
            cp.join(); 
            // System.out.printf("[%s][%d][%s] Init Join\n", logServerName, logServerPort, m.getType().toString());
        } catch (InterruptedException ex) {
            Logger.getLogger(LogProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
        
        return result;
    }
    
    /**
     * Run the client proxy
     */
    @Override
    public void run(){
        try {
            ClientChannel con = new ClientChannel(this.clientProxyServerName, this.toServerPort);
            
            while (!con.open())
            {
                try {
                    sleep((long) (10));
                } catch (InterruptedException e) {
                }
            }   
            
            con.writeObject(outMessage);
            
            this.result.setMessage((Message) con.readObject());
            
            con.close();
            
        } catch (Exception ex) {
            Logger.getLogger(ClientProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
