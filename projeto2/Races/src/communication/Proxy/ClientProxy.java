/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.Proxy;

import communication.message.Message;
import communication.message.MessageWrapper;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import settings.NodeSettsProxy;

/**
 *
 * @author gipmon
 */
public class ClientProxy {
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    private final String proxyName;
    
    private Logger logger = Logger.getLogger("AccessLog");  
    private FileHandler fh;  
    
    public ClientProxy(String proxyName){
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_HOST = proxy.SERVER_HOSTS().get(proxyName);
        SERVER_PORT = proxy.SERVER_PORTS().get(proxyName);
        this.proxyName = proxyName;
        /*
        try {
            // This block configure the logger with handler and formatter  
            fh = new FileHandler("access.log");  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
            logger.setUseParentHandlers(false);
        } catch (SecurityException | IOException e) {  
            
        } 
        */
    }
    
    /**
    * Method to communicate with the Betting Centre.
     * @param m
     * @return 
    */
    public MessageWrapper communicate(Message m){
        Thread.currentThread().getName();
        //logger.log(Level.INFO, "{0} - requested {1} to {2}", new Object[]{Thread.currentThread().getName(), m.getType(), proxyName});
        System.out.println(Thread.currentThread().getName() + " requested " + m.getType() + " to " + proxyName);
        return Proxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
}
