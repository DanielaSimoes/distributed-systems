/*
 * This file is a message wrapper with getters and setters.
 */
package communication.message;

import java.io.Serializable;

/**
 *
 * @author Daniela
 */
public class MessageWrapper implements Serializable{
    private Message m;
    
    public void setMessage(Message m){
        this.m = m;
    }
    
    public Message getMessage(){
        return this.m;
    }

}
