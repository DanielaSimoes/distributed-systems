/*
 * This file is a message wrapper with getters and setters.
 */
package communication.message;

import java.io.Serializable;

/**
 * This class implements the message wrapper.
 * @author Daniela Simões
 */
public class MessageWrapper implements Serializable{
    private Message m;
    
    /**
    * Setter of the message.
    * @param m
    */
    public void setMessage(Message m){
        this.m = m;
    }
    
    /**
    * Getter of the message.
    * @return 
    */
    public Message getMessage(){
        return this.m;
    }

}
