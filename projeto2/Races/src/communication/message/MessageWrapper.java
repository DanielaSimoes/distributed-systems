/*
 * This file is a message wrapper with getters and setters.
 */
package communication.message;

/**
 *
 * @author Daniela
 */
public class MessageWrapper {
    private Message m;
    
    public void setMessage(Message m){
        this.m = m;
    }
    
    public Message getMessage(){
        return this.m;
    }

}
