/*
 * This file contains a client proxy.
 */
package communication.Proxy;

import communication.message.Message;
import communication.message.MessageWrapper;
import settings.NodeSettsStub;

/**
 * This class implements a client proxy.
 * @author Daniela Simões, 76771
 */
public class ClientStub {
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    private final String proxyName;
    
    /**
    * Client Proxy constructor.
    * @param proxyName    
    */
    public ClientStub(String proxyName){
        NodeSettsStub proxy = new NodeSettsStub(); 
        SERVER_HOST = proxy.SERVER_HOSTS().get(proxyName);
        SERVER_PORT = proxy.SERVER_PORTS().get(proxyName);
        this.proxyName = proxyName;
    }
    
    /**
    * Method to communicate with a given server:port.
    * @param m
    * @return 
    */
    public MessageWrapper communicate(Message m){
        return Stub.connect(SERVER_HOST,  SERVER_PORT, m);
    }
}
