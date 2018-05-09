/*
 * This file contains the Stub.
 */
package communication.stub;

import communication.ClientChannel;
import communication.message.Message;
import communication.message.MessageType;
import communication.message.MessageWrapper;
import GeneralRepository.LogStub;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* Class that implements a stub.
* @author Daniela Simões, 76771
*/
public class Stub extends Thread {
    private final String clientStubServerName;
    private final int toServerPort;
    private final Message outMessage;
    private final MessageWrapper inMessage;
    
    /**
    * Constructor of Stub.
    * @param clientStubServerName
    * @param toServerPort
    * @param inMessage
    * @param outMessage
    */
    public Stub(String clientStubServerName, int toServerPort, MessageWrapper inMessage, Message outMessage){
        this.clientStubServerName = clientStubServerName;
        this.toServerPort = toServerPort;
        this.outMessage = outMessage;
        this.inMessage = inMessage;
    }
    
    /**
    * Message wrapper.
    * @param logServerName
    * @param logServerPort
    * @param m
    * @return 
    */
    public static MessageWrapper connect(String logServerName, int logServerPort, Message m){
        MessageWrapper inMessage = new MessageWrapper();
        
        Stub cp = new Stub(logServerName, logServerPort, inMessage, m);
        
        cp.start();
        
        try {
            cp.join(); 
        } catch (InterruptedException ex) {
            Logger.getLogger(LogStub.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (inMessage.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + inMessage.getMessage().toString());
            System.exit(1);
        }
        
        return inMessage;
    }
    
    /**
    * Method to run the stub.
    */
    @Override
    public void run(){
        try {
            ClientChannel con = new ClientChannel(this.clientStubServerName, this.toServerPort);
            
            while (!con.open())
            {
                try {
                    sleep((long) (10));
                } catch (InterruptedException e) {
                }
            }   
            
            con.writeObject(outMessage);
            
            this.inMessage.setMessage((Message) con.readObject());
            
            con.close();
            
            
        } catch (Exception ex) {
            Logger.getLogger(Stub.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
