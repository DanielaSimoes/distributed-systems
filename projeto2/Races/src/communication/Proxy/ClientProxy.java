/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.Proxy;

import communication.message.Message;
import communication.message.MessageWrapper;
import settings.NodeSettsProxy;

/**
 *
 * @author Daniela Simões, 76771
 */
public class ClientProxy {
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    private final String proxyName;
    
    public ClientProxy(String proxyName){
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_HOST = proxy.SERVER_HOSTS().get(proxyName);
        SERVER_PORT = proxy.SERVER_PORTS().get(proxyName);
        this.proxyName = proxyName;
    }
    
    /**
    * Method to communicate with the Betting Centre.
     * @param m
     * @return 
    */
    public MessageWrapper communicate(Message m){
        Thread.currentThread().getName();
        System.out.println(Thread.currentThread().getName() + " requested " + m.getType() + " to " + proxyName);
        return Proxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
}
