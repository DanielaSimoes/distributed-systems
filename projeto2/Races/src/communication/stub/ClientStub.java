/*
 * This file contains a client stub.
 */
package communication.stub;

import communication.message.Message;
import communication.message.MessageWrapper;
import settings.NodeSettsStub;

/**
 * This class implements a client st7ub.
 * @author Daniela Simões, 76771
 */
public class ClientStub {
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    private final String stubName;
    
    /**
    * Client Stub constructor.
    * @param stubName    
    */
    public ClientStub(String stubName){
        NodeSettsStub nodeSettsStub = new NodeSettsStub(); 
        SERVER_HOST = nodeSettsStub.SERVER_HOSTS().get(stubName);
        SERVER_PORT = nodeSettsStub.SERVER_PORTS().get(stubName);
        this.stubName = stubName;
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
